package com.cavity.usermanager.database;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class DatabaseService {

    public void replaceDatabaseFile(MultipartFile newDatabaseFile) {
        try {
            // Ensure the uploaded file is not empty
            if (newDatabaseFile.isEmpty()) {
                throw new IllegalArgumentException("New database file is empty.");
            }

            // Get the database file path from the DATABASE_FILE environment variable
            String databaseFilePath = System.getenv("DATABASE_FILE");

            // If the DATABASE_FILE environment variable is not set, use a default path
            if (databaseFilePath == null || databaseFilePath.isEmpty()) {
                databaseFilePath = "accounts.db";
            }

            File databaseFile = new File(databaseFilePath);

            try {
                if (!databaseFile.exists()) {
                    databaseFile.createNewFile();
                }

                // Print the absolute path of the file
                System.out.println("File absolute path: " + databaseFile.getAbsolutePath());

                FileOutputStream fos = new FileOutputStream(databaseFile);
                byte[] bytes = newDatabaseFile.getBytes();
                fos.write(bytes);
                fos.close();

                System.out.println("File saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to replace the database file: " + e.getMessage(), e);
        }
    }
}