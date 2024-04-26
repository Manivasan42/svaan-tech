package com.import_export.controller;

import com.import_export.exception.DataValidationException;
import com.import_export.service.ExcelImportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelImportExportService excelService;

    @PostMapping("/import")
    public ResponseEntity<String> importUsersFromExcel(@RequestParam String filePath) {
        try {
            excelService.importUsersFromExcel(filePath);
            return ResponseEntity.ok("Data imported successfully!");
        } catch (DataValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing data: " + e.getMessage());
        }
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportUsersToExcel(@RequestParam String filePath) {
        try {
            excelService.exportUsersToExcel(filePath);
            return ResponseEntity.ok("Data exported successfully!");
        } catch (DataValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error exporting data: " + e.getMessage());
        }
    }
}
