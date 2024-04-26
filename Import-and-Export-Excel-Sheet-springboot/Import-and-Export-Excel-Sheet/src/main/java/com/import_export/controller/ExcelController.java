package com.import_export.controller;

import com.import_export.exception.DataValidationException;
import com.import_export.service.ExcelImportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelImportExportService excelService;

    @PostMapping("/import")
    public String importUsersFromExcel(@RequestParam String filePath) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", "YourTableName");
        data.put("columnValues", "YourColumnValues");
        try {
            excelService.importUsersFromExcel(filePath);
            return "Data imported successfully!";
        } catch (DataValidationException ex) {
            throw ex;
        }
    }

    @PostMapping("/export")
    public String exportUsersToExcel(@RequestParam String filePath) throws IOException {
        // Assuming you validate data before exporting to Excel
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", "YourTableName");
        data.put("columnValues", "YourColumnValues");
        try {
            excelService.exportUsersToExcel(filePath);
            return "Data exported successfully!";
        } catch (DataValidationException ex) {
            throw ex;
        }
    }
}
