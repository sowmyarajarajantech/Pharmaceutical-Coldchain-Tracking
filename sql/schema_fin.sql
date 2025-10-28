-- schema.sql
-- Cold Chain Tracking System schema (MySQL 8.x)
CREATE DATABASE IF NOT EXISTS coldchain CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE coldchain;

-- Settings table for global thresholds and config
CREATE TABLE IF NOT EXISTS Settings (
  `key` VARCHAR(100) NOT NULL PRIMARY KEY,
  `value` VARCHAR(500) NOT NULL,
  `description` VARCHAR(1000),
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Roles and users
CREATE TABLE IF NOT EXISTS Roles (
  role_id INT AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(50) NOT NULL UNIQUE COMMENT 'eg. Admin, Housekeeping',
  description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  full_name VARCHAR(200),
  email VARCHAR(200),
  password_hash VARCHAR(255) NOT NULL COMMENT 'Store a strong password, not a plain text',
  role_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE RESTRICT
);

-- Warehouses and storage locations
CREATE TABLE IF NOT EXISTS Warehouses (
  wh_id INT AUTO_INCREMENT PRIMARY KEY,
  wh_code VARCHAR(50) NOT NULL UNIQUE,
  wh_name VARCHAR(200),
  address VARCHAR(255) NOT NULL,
  phone VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_wh_name ON Warehouses(wh_name);

-- Products and batches
CREATE TABLE IF NOT EXISTS Products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  sku VARCHAR(100) NOT NULL UNIQUE,
  manufacturer VARCHAR(255),
  name VARCHAR(255) NOT NULL,
  description TEXT,
  required_temp_min DECIMAL(5,2) DEFAULT 2.00,
  required_temp_max DECIMAL(5,2) DEFAULT 8.00,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Batches (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT NOT NULL,
  batch_no VARCHAR(100) NOT NULL,
  manufacture_date DATE,
  expiry_date DATE,
  quantity INT DEFAULT 0,
  is_expired BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);

DELIMITER $$
CREATE EVENT IF NOT EXISTS MarkExpiredBatchesDaily
ON SCHEDULE EVERY 1 DAY
DO
	UPDATE Batches SET is_expired=TRUE
    WHERE expiry_date < CURDATE();
$$
DELIMITER ;

-- Shipments that move batches between warehouses
CREATE TABLE IF NOT EXISTS Shipments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  shipment_code VARCHAR(100) NOT NULL UNIQUE,
  batch_id INT NOT NULL,
  from_warehouse INT,
  to_warehouse INT,
  shipped_at DATETIME,
  expected_delivery DATETIME,
  actual_delivery DATETIME,
  status ENUM('PENDING','IN_TRANSIT','DELIVERED','CANCELLED') DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (batch_id) REFERENCES Batches(id) ON DELETE RESTRICT,
  FOREIGN KEY (from_warehouse) REFERENCES Warehouses(wh_id) ON DELETE RESTRICT,
  FOREIGN KEY (to_warehouse) REFERENCES Warehouses(wh_id) ON DELETE RESTRICT
);

-- Temperature logs
CREATE TABLE IF NOT EXISTS TemperatureLogs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  shipment_id INT NOT NULL,
  recorded_at DATETIME NOT NULL,
  temperature_celsius DECIMAL(5,2) NOT NULL,
  recorded_by INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (shipment_id) REFERENCES Shipments(id) ON DELETE CASCADE,
  FOREIGN KEY (recorded_by) REFERENCES Users(user_id) ON DELETE SET NULL
);
CREATE INDEX idx_temp_shipment ON TemperatureLogs(shipment_id, recorded_at);

-- Alerts generated when temperature excursions occur
CREATE TABLE IF NOT EXISTS Alerts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  temperature_log_id BIGINT,
  shipment_id INT,
  alert_type VARCHAR(100),
  alert_message VARCHAR(1000),
  temp_at_alert DECIMAL(5,2),
  required_min DECIMAL(5,2),
  required_max DECIMAL(5,2),
  severity ENUM('LOW','MEDIUM','HIGH') DEFAULT 'MEDIUM',
  acknowledged_by INT,
  acknowledged_at DATETIME,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (temperature_log_id) REFERENCES TemperatureLogs(id) ON DELETE CASCADE,
  FOREIGN KEY (shipment_id) REFERENCES Shipments(id) ON DELETE CASCADE,
  FOREIGN KEY (acknowledged_by) REFERENCES Users(user_id) ON DELETE SET NULL
);
CREATE INDEX idx_alert_shipment ON Alerts(shipment_id, created_at);

-- Audit log for critical operations
CREATE TABLE IF NOT EXISTS AuditLog (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  action VARCHAR(255),
  details TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE SET NULL
);
CREATE INDEX idx_audit_user_time ON AuditLog(user_id, created_at);

CREATE USER 'coldchain_app_user'@'localhost' IDENTIFIED BY 'ColdChain@123';
GRANT ALL PRIVILEGES ON coldchain.* TO 'coldchain_app_user'@'localhost';
FLUSH PRIVILEGES;


DELIMITER $$
CREATE PROCEDURE AddTemperatureLogWithAudit(IN p_shipment_id INT, IN p_temp_recorded DECIMAL(5,2), IN p_recorded_at TIMESTAMP, IN p_user_id INT)
BEGIN
  DECLARE user_name_val VARCHAR(100);
-- Start a transaction so both log and audit happen together
  START TRANSACTION;
-- Get the username for audit details
  SELECT username INTO user_name_val FROM Users WHERE user_id = p_user_id LIMIT 1;
-- Insert the temperature log
  INSERT INTO TemperatureLogs (shipment_id, recorded_at, temperature_celsius, recorded_by)
  VALUES (p_shipment_id, p_recorded_at, p_temp_recorded, p_user_id);
-- Insert into audit log for traceability
  INSERT INTO AuditLog (user_id, action, details)
  VALUES (p_user_id, 'TEMPERATURE_LOG_ADDED',
	CONCAT('User ', user_name_val, ' recorded temperature ', p_temp_recorded, '°C for shipment ID ', p_shipment_id));
-- Commit both inserts atomically
  COMMIT;
END$$
DELIMITER ;

-- Stored procedure to insert temperature and generate alert if needed
DELIMITER $$
CREATE TRIGGER trg_temperature_alert AFTER INSERT ON TemperatureLogs
FOR EACH ROW
BEGIN
	DECLARE v_product_id INT;
    DECLARE v_min DECIMAL(5,2);
    DECLARE v_max DECIMAL(5,2);
    DECLARE v_batch_id INT;
    DECLARE v_threshold DECIMAL(5,2) DEFAULT 0.00;
    
    
    SELECT CAST('value' AS DECIMAL(5,2)) INTO v_threshold FROM Settings WHERE 'key'='ALERT_TEMPERATURE_THRESHOLD_CELSIUS' LIMIT 1;
    SELECT batch_id INTO v_batch_id FROM Shipments WHERE id=NEW.shipment_id LIMIT 1;
    SELECT product_id INTO v_product_id FROM Batches WHERE id=v_batch_id LIMIT 1;
    SELECT required_temp_min, required_temp_max INTO v_min, v_max FROM Products WHERE id=v_product_id LIMIT 1;
    
    IF NEW.temperature_celsius > (v_max + v_threshold) THEN
		INSERT INTO Alerts(temperature_log_id,shipment_id,alert_type,alert_message,temp_at_alert,required_min,required_max,severity)
        VALUES( NEW.id,NEW.shipment_id,'TEMP_HIGH',
        CONCAT('Temperature ', NEW.temperature_celsius, '°C exceeds max of ', v_max, '°C (Threshold +', v_threshold, ') for Product ID ', v_product_id),
		NEW.temperature_celsius, v_min, v_max, 'HIGH');
	END IF;
    
	IF NEW.temperature_celsius < (v_min - v_threshold) THEN
		INSERT INTO Alerts (temperature_log_id,shipment_id,alert_type,alert_message,temp_at_alert,required_min,required_max,severity)
		VALUES (NEW.id,NEW.shipment_id,'TEMP_LOW',
		CONCAT('Temperature ', NEW.temperature_celsius, '°C below min of ', v_min, '°C (Threshold -', v_threshold, ') for Product ID ', v_product_id),
		NEW.temperature_celsius, v_min, v_max, 'HIGH');
	END IF; 
END $$
DELIMITER ;    

-- Example default settings
INSERT IGNORE INTO Settings(`key`, `value`, `description`) VALUES
('ALERT_TEMPERATURE_THRESHOLD_CELSIUS','0.50','Buffer threshold beyond product range to trigger alerts'),
('DEFAULT_TIMEZONE','UTC','App default timezone for timestamps');