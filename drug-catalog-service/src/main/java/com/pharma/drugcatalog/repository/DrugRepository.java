package com.pharma.drugcatalog.repository;

import com.pharma.drugcatalog.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    List<Drug> findByStatus(Drug.Status status);

    @Query("SELECT e FROM Drug e WHERE e.status = 'ACTIVE' ORDER BY e.createdAt DESC")
    List<Drug> findAllActive();
}
