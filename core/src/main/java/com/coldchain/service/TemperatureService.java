// File: core/src/main/java/com/coldchain/service/TemperatureService.java
package com.coldchain.service;

import com.coldchain.dao.AlertDao;
import com.coldchain.dao.AuditLogDao;
import com.coldchain.dao.TemperatureLogDao;
import com.coldchain.model.Alert;
import com.coldchain.model.AuditLog;
import com.coldchain.model.TemperatureLog;
import com.coldchain.util.DbUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TemperatureService {

    private final TemperatureLogDao temperatureLogDao = new TemperatureLogDao();
    private final AuditLogDao auditLogDao = new AuditLogDao();
    private final AlertDao alertDao = new AlertDao();

    public List<Alert> recordTemperature(int shipmentId, LocalDateTime recordedAt, BigDecimal temperature, int userId, String username) {
        Timestamp checkTime = new Timestamp(System.currentTimeMillis() - 1000);

        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);

            try {
                TemperatureLog log = new TemperatureLog(shipmentId, temperature, recordedAt, userId);
                temperatureLogDao.saveInTransaction(log, conn);

                String details = String.format("User '%s' recorded temperature %.2f°C for shipment ID %d.",
                        username, temperature, shipmentId);
                AuditLog audit = new AuditLog(userId, "ADD_TEMPERATURE_LOG", details);
                auditLogDao.saveInTransaction(audit, conn);

                conn.commit();

                return alertDao.findAlertsForShipmentAfter(shipmentId, checkTime);

            } catch (SQLException e) {
                System.err.println("ERROR: Transaction failed. Rolling back changes.");
                conn.rollback();
                throw new RuntimeException("Failed to record temperature due to a database error.", e);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Failed to get or manage database connection.", e);
        }
    }
}