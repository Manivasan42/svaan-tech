package com.import_export.exception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class DataValidationException extends RuntimeException {

    @Autowired
    private EntityManager entityManager;

    public DataValidationException(String s) {
    }

    public void validateData(Map<String, Object> data) throws DataValidationException {

        String tableName = (String) data.get("tableName");
        Map<String, Object> columnValues = (Map<String, Object>) data.get("columnValues");

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE 1=0");
        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        try {
            query.getResultList(); // If no exception is thrown, table exists
        } catch (Exception e) {
            throw new DataValidationException("Table " + tableName + " does not exist in the database");
        }

        for (Map.Entry<String, Object> entry : columnValues.entrySet()) {
            String columnName = entry.getKey();
            Object columnValue = entry.getValue();

            StringBuilder columnQueryBuilder = new StringBuilder("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '")
                    .append(tableName).append("' AND COLUMN_NAME = '").append(columnName).append("'");
            Query columnQuery = entityManager.createNativeQuery(columnQueryBuilder.toString());

            if (columnQuery.getResultList().isEmpty()) {
                throw new DataValidationException("Column " + columnName + " does not exist in table " + tableName);
            }
        }

    }
}
