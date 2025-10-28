// File: core/src/main/java/com/coldchain/dao/TemperatureLogDao.java
package com.coldchain.dao;

import com.coldchain.model.TemperatureLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TemperatureLogDao {
    public void saveInTransaction(TemperatureLog log, Connection conn) throws SQLException {
        String sql = "INSERT INTO TemperatureLogs (shipment_id, temperature, recorded_at, recorded_by_user_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, log.getShipmentId());
            ps.setBigDecimal(2, log.getTemperature());
            ps.setTimestamp(3, Timestamp.valueOf(log.getRecordedAt()));
            ps.setInt(4, log.getRecordedByUserId());
            ps.executeUpdate();
        }
    }
}