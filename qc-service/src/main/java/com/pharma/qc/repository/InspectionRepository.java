package com.pharma.qc.repository;

import com.pharma.qc.model.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    List<Inspection> findByResult(Inspection.Result result);
    List<Inspection> findByBatchNumber(String batchNumber);
}
