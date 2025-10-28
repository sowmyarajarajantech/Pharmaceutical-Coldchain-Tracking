// File: core/src/main/java/com/coldchain/util/DbUtil.java
package com.coldchain.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static final HikariDataSource dataSource;

    static {
        // Using System.err sends output to the standard error stream.
        // This is invisible to a user running the packaged application.
        System.err.println("INFO: Initializing database connection pool...");
        try {
            Properties props = new Properties();
            try (InputStream input = DbUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    System.err.println("FATAL: db.properties file not found in classpath.");
                    throw new RuntimeException("db.properties not found. Make sure it's in desktop-ui/src/main/resources.");
                }
                props.load(input);
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.addDataSourceProperty("allowPublicKeyRetrieval", "true");
            config.addDataSourceProperty("useSSL", "false");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("connectTimeout", "5000"); // 5s connection timeout
            config.addDataSourceProperty("socketTimeout", "10000"); // 10s read timeout
            config.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(config);
            System.err.println("INFO: Database connection pool initialized successfully.");

        } catch (IOException | RuntimeException e) {
            System.err.println("FATAL: Failed to initialize database connection pool.");
            e.printStackTrace(System.err); // Also print errors to the developer console
            throw new RuntimeException(e);
        }
    }

    private DbUtil() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource!= null) {
            System.err.println("INFO: Closing database connection pool...");
            dataSource.close();
            System.err.println("INFO: Database connection pool closed.");
        }
    }
}