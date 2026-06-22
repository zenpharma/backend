package com.pharma.manufacturing.repository;

import com.pharma.manufacturing.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findByStatus(Batch.Status status);

    List<Batch> findByAssignedLine(String assignedLine);

    @Query("SELECT b FROM Batch b WHERE b.status != 'CANCELLED' ORDER BY b.scheduledDate ASC")
    List<Batch> findAllActive();
}
