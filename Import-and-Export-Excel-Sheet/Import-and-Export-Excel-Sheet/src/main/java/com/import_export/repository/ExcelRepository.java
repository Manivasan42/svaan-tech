package com.import_export.repository;

import com.import_export.entity.ExcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelRepository extends JpaRepository<ExcelEntity,Long> {
}
