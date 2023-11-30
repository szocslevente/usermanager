package com.cavity.usermanager.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;


@RestController
@RequestMapping("/api/v1/database")
public class DatabaseController {

    private final DatabaseService databaseService;

    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/replace")
    public ResponseEntity<String> replaceDatabase(@RequestParam("file") MultipartFile newDatabaseFile) {
        try {
            if (newDatabaseFile.isEmpty()) {
                return ResponseEntity.badRequest().body("New database file is empty.");
            }

            // Logging: Log the received file name and size
            System.out.println("Received file: " + newDatabaseFile.getOriginalFilename());
            System.out.println("File size: " + newDatabaseFile.getSize());

            // Replace the database file
            databaseService.replaceDatabaseFile(newDatabaseFile);

            return ResponseEntity.ok("Database file replaced successfully.");
        } catch (Exception e) {
            // Logging: Log any exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to replace the database file: " + e.getMessage());
        }
    }

    @GetMapping(path = "/download")
    public ResponseEntity<InputStreamResource> downloadFile() throws Exception {
        // Get the database file path from the DATABASE_FILE environment variable
        String databaseFilePath = System.getenv("DATABASE_FILE");

        // If the DATABASE_FILE environment variable is not set, use a default path
        if (databaseFilePath == null || databaseFilePath.isEmpty()) {
            databaseFilePath = "accounts.db";
        }

        File downloadFile = new File(databaseFilePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}