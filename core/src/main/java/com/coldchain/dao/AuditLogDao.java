// File: core/src/main/java/com/coldchain/dao/AuditLogDao.java
package com.coldchain.dao;

import com.coldchain.model.AuditLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AuditLogDao {
    public void saveInTransaction(AuditLog audit, Connection conn) throws SQLException {
        String sql = "INSERT INTO AuditLogs (user_id, action_type, details) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (audit.getUserId()!= null) {
                ps.setInt(1, audit.getUserId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, audit.getActionType());
            ps.setString(3, audit.getDetails());
            ps.executeUpdate();
        }
    }
}