-- ===========================================
-- Pharmaceutical Cold Chain Tracking System
-- Seed Data (Schema-Compliant & Audit-Enabled)
-- ===========================================
USE coldchain;

-- -------------------------------------------------
-- 1. SETTINGS
-- -------------------------------------------------
-- Default configuration and alert thresholds
INSERT IGNORE INTO Settings(`key`, `value`, `description`) VALUES
('ALERT_TEMPERATURE_THRESHOLD_CELSIUS','2.00','Buffer threshold beyond product range to trigger alerts'),
('DEFAULT_TIMEZONE','UTC','App default timezone for timestamps');

-- -------------------------------------------------
-- 2. ROLES
-- -------------------------------------------------
INSERT IGNORE INTO Roles (role_id, role_name, description) VALUES
(1, 'ADMIN', 'System administrator with full privileges'),
(2, 'WAREHOUSE_STAFF', 'Responsible for managing warehouse operations'),
(3, 'AUDITOR', 'Read-only access to verify system compliance'),
(4, 'QUALITY_ASSURANCE', 'Monitors data accuracy and alerts');

-- -------------------------------------------------
-- 3. USERS
-- -------------------------------------------------
-- Password hashes are placeholders for demo purposes only.
INSERT IGNORE INTO Users (user_id, username, full_name, email, password_hash, role_id) VALUES
(1, 'admin_demo', 'Admin Demo', 'admin@example.com', '$2y$12$PLACEHOLDERADMINHASH0000000000000000000000000', 1),
(2, 'wstaff_jane', 'Jane Smith', 'jane.smith@example.com', '$2y$12$PLACEHOLDERWAREHOUSEHASH000000000000000000000', 2),
(3, 'wstaff_john', 'John Miller', 'john.miller@example.com', '$2y$12$PLACEHOLDERWAREHOUSEHASH000000000000000000000', 2),
(4, 'auditor_sam', 'Sam Roberts', 'sam.roberts@example.com', '$2y$12$PLACEHOLDERAUDITORHASH000000000000000000000', 3),
(5, 'qa_emma', 'Emma Davis', 'emma.davis@example.com', '$2y$12$PLACEHOLDERQAHASH00000000000000000000000000000', 4);

-- -------------------------------------------------
-- 4. WAREHOUSES
-- -------------------------------------------------
INSERT IGNORE INTO Warehouses (wh_id, wh_code, wh_name, address, phone) VALUES
(1, 'WH-NY-001', 'PharmaHub North', '120 Health Ave, New York, USA', '+1-212-555-1010'),
(2, 'WH-LA-002', 'West Coast Distribution', '550 Supply Rd, Los Angeles, USA', '+1-310-555-2020'),
(3, 'WH-FR-003', 'EU Central Logistics', '22 IndustrieStrasse, Frankfurt, Germany', '+49-69-555-3030'),
(4, 'WH-BLR-004', 'Bangalore Cold Storage', '88 Pharma Tech Park, Bangalore, India', '+91-80-555-4040');

-- -------------------------------------------------
-- 5. PRODUCTS
-- -------------------------------------------------
INSERT IGNORE INTO Products (id, sku, manufacturer, name, description, required_temp_min, required_temp_max) VALUES
(1, 'VAX-001', 'PharmaCorp', 'Vaccine A', 'COVID-19 vaccine - maintain 2–8°C', 2.00, 8.00),
(2, 'INS-010', 'BioGen', 'Insulin B', 'Temperature-sensitive insulin variant', 2.00, 8.00),
(3, 'BIO-003', 'LifeScience Inc.', 'Bio Reagent C', 'Enzyme reagent - store cold', 2.00, 6.00),
(4, 'HEM-004', 'LifeScience Inc.', 'Hemofluid D', 'Blood plasma product', 2.00, 8.00),
(5, 'VAX-005', 'PharmaCorp', 'Vaccine X', 'Experimental vaccine - requires ultra-cold storage', -20.00, -10.00);

-- -------------------------------------------------
-- 6. BATCHES
-- -------------------------------------------------
INSERT IGNORE INTO Batches (id, product_id, batch_no, manufacture_date, expiry_date, quantity) VALUES
(1, 1, 'BATCH-VAX-0001', '2025-01-10', '2026-01-10', 1000),
(2, 2, 'BATCH-INS-0001', '2025-02-05', '2026-02-05', 800),
(3, 3, 'BATCH-BIO-0001', '2024-11-01', '2025-11-01', 500),
(4, 4, 'BATCH-HEM-0002', '2025-03-15', '2025-12-15', 400),
(5, 5, 'BATCH-VAXX-0003', '2025-07-01', '2026-07-01', 300);

-- -------------------------------------------------
-- 7. SHIPMENTS
-- -------------------------------------------------
INSERT IGNORE INTO Shipments (id, shipment_code, batch_id, from_warehouse, to_warehouse, shipped_at, expected_delivery, actual_delivery, status) VALUES
(1, 'SHIP-0001', 1, 1, 2, '2025-10-18 08:00:00', '2025-10-18 16:00:00', '2025-10-18 16:05:00', 'DELIVERED'),
(2, 'SHIP-0002', 2, 1, 3, '2025-10-19 09:00:00', '2025-10-19 20:00:00', NULL, 'IN_TRANSIT'),
(3, 'SHIP-0003', 3, 3, 4, '2025-10-20 07:30:00', '2025-10-20 15:30:00', '2025-10-20 15:10:00', 'DELIVERED'),
(4, 'SHIP-0004', 4, 4, 1, '2025-10-21 10:00:00', '2025-10-21 18:00:00', NULL, 'PENDING'),
(5, 'SHIP-0005', 5, 1, 4, '2025-10-22 09:00:00', '2025-10-22 18:00:00', NULL, 'IN_TRANSIT');

-- -------------------------------------------------
-- 8. TEMPERATURE LOGS (via Procedure)
-- -------------------------------------------------
-- These procedure calls automatically populate both TemperatureLogs and AuditLog tables.
-- Normal shipment (within range)
CALL AddTemperatureLogWithAudit(1, 5.20, '2025-10-18 09:30:00', 2);
CALL AddTemperatureLogWithAudit(1, 5.40, '2025-10-18 11:30:00', 2);
CALL AddTemperatureLogWithAudit(1, 4.90, '2025-10-18 13:00:00', 2);

-- Shipment with alert (breach above 8 + 2 threshold)
CALL AddTemperatureLogWithAudit(2, 8.50, '2025-10-19 10:00:00', 3);
CALL AddTemperatureLogWithAudit(2, 10.50, '2025-10-19 13:00:00', 3);

-- Shipment near low range but valid
CALL AddTemperatureLogWithAudit(3, 2.20, '2025-10-20 08:00:00', 2);
CALL AddTemperatureLogWithAudit(3, 1.80, '2025-10-20 10:00:00', 2); -- May trigger low alert depending on threshold

-- Ultra-cold product shipment (for Vaccine X)
CALL AddTemperatureLogWithAudit(5, -15.50, '2025-10-22 10:00:00', 3);
CALL AddTemperatureLogWithAudit(5, -8.00, '2025-10-22 11:00:00', 3); -- Breach (too warm)

-- -------------------------------------------------
-- 9. OPTIONAL: MANUAL ALERT INSERT (for reference)
-- -------------------------------------------------
-- Normally alerts are auto-generated via trigger.
-- These are included only as demonstration of manual insertion if needed.
-- INSERT IGNORE INTO Alerts (id, temperature_log_id, shipment_id, alert_type, alert_message, temp_at_alert, required_min, required_max, severity, created_at)
-- VALUES (1, 5, 2, 'TEMP_HIGH', 'Temperature 10.5°C exceeded max limit 8°C (+2.0 threshold).', 10.5, 2.0, 8.0, 'HIGH', '2025-10-19 13:05:00');

-- -------------------------------------------------
-- 10. VERIFICATION SCRIPTS (optional for testers)
-- -------------------------------------------------
-- SELECT * FROM TemperatureLogs;
-- SELECT * FROM AuditLog ORDER BY created_at DESC;
-- SELECT * FROM Alerts ORDER BY created_at DESC;
-- SELECT * FROM Settings;
