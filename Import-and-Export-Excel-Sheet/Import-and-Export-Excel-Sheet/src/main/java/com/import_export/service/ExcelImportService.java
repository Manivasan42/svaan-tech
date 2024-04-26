package com.import_export.service;

import com.import_export.entity.ExcelEntity;
import com.import_export.repository.ExcelRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelImportExportService {

    @Autowired
    private ExcelRepository excelRepository;

    public void importUsersFromExcel(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            ExcelEntity excelEntity = new ExcelEntity();

            while (cellIterator.hasNext()) {
                Cell currentCell = cellIterator.next();
                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    if (currentCell.getColumnIndex() == 0) {
                        excelEntity.setName(currentCell.getStringCellValue());
                    } else if (currentCell.getColumnIndex() == 1) {
                        excelEntity.setEmail(currentCell.getStringCellValue());
                    }
                }
            }
            excelRepository.save(excelEntity);
        }
        workbook.close();
    }

    public void exportUsersToExcel(String filePath) throws IOException {
        List<ExcelEntity> excelEntity = excelRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        int rowNum = 0;
        for (ExcelEntity user : excelEntity) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getEmail());
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        workbook.close();
        fileOut.close();
    }
}