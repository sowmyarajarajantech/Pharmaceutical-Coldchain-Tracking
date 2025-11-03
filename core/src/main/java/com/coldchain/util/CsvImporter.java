package com.coldchain.util;

// 1. DELETE THE OLD SERVICE IMPORT
// import com.coldchain.service.TemperatureService; 

// 2. ADD IMPORTS FOR THE MODELS AND REPOSITORIES
import com.coldchain.model.AuditLog;
import com.coldchain.model.TemperatureLog;
import com.coldchain.repository.AuditLogRepository;
import com.coldchain.repository.TemperatureLogRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CsvImporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 3. CHANGE THE METHOD TO ACCEPT THE REPOSITORIES
    public static int importTemperatureLogs(File csvFile, int userId, String username, 
                                            TemperatureLogRepository tempRepo, 
                                            AuditLogRepository auditRepo) {
        
        int importedCount = 0;
        // 4. DELETE THE OLD SERVICE LINE
        // TemperatureService tempService = new TemperatureService(); 

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header line

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }

                if (values.length >= 3) {
                    try {
                        int shipmentId = Integer.parseInt(values[0]);
                        LocalDateTime recordedAt = LocalDateTime.parse(values[1], FORMATTER);
                        BigDecimal temp = new BigDecimal(values[2]);

                        // 5. REPLACE THE OLD SERVICE CALL WITH THIS NEW LOGIC
                        
                        // Create and save the TemperatureLog
                        TemperatureLog log = new TemperatureLog();
                        log.setShipment_id(shipmentId);
                        log.setRecorded_at(recordedAt);
                        log.setTemperature_celsius(temp);
                        log.setRecorded_by(userId);
                        tempRepo.save(log); // Save using the repository

                        // Create and save the AuditLog
                        AuditLog audit = new AuditLog();
                        audit.setUser_id(userId);
                        audit.setAction("CSV_IMPORT_TEMP_LOG");
                        audit.setDetails("User '" + username + "' imported temp " + temp + "C for shipment " + shipmentId);
                        auditRepo.save(audit); // Save using the repository
                        
                        importedCount++;
                        
                    } catch (Exception e) {
                        System.err.println("WARN: Skipping invalid CSV row: " + line + " | Reason: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("ERROR: Failed to read CSV file.");
            e.printStackTrace(System.err);
        }
        return importedCount;
    }
}