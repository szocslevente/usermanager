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
            File oldDatabaseFile = new File("accounts.db");
            String filePath = oldDatabaseFile.getAbsolutePath();

            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
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