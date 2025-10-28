// File: core/src/main/java/com/coldchain/dao/AlertDao.java
package com.coldchain.dao;

import com.coldchain.model.Alert;
import com.coldchain.util.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDao {
    public List<Alert> findAlertsForShipmentAfter(int shipmentId, Timestamp afterTimestamp) {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM Alerts WHERE shipment_id =? AND created_at >=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, shipmentId);
            ps.setTimestamp(2, afterTimestamp);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapRowToAlert(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return alerts;
    }

    private Alert mapRowToAlert(ResultSet rs) throws SQLException {
        Alert alert = new Alert();
        alert.setAlertId(rs.getInt("alert_id"));
        alert.setShipmentId(rs.getInt("shipment_id"));
        alert.setTempLogId(rs.getInt("temp_log_id"));
        alert.setMessage(rs.getString("message"));
        alert.setAlertLevel(rs.getString("alert_level"));
        alert.setAcknowledged(rs.getBoolean("acknowledged"));
        alert.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return alert;
    }
}