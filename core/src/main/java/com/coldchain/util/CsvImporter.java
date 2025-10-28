// File: core/src/main/java/com/coldchain/util/CsvImporter.java
package com.coldchain.util;

import com.coldchain.service.TemperatureService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CsvImporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static int importTemperatureLogs(File csvFile, int userId, String username) {
        int importedCount = 0;
        TemperatureService tempService = new TemperatureService();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header line

            String line;
            while ((line = br.readLine()) != null) {
            // Split the line by commas into an array
                String[] values = line.split(",");

                // ✅ Trim each value to remove extra spaces
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }

                if (values.length >= 3) {
                    try {
                        int shipmentId = Integer.parseInt(values[0]);
                        LocalDateTime recordedAt = LocalDateTime.parse(values[1], FORMATTER);
                        BigDecimal temp = new BigDecimal(values[2]);

                        // Use your service call (unchanged)
                        tempService.recordTemperature(shipmentId, recordedAt, temp, userId, username);
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