package com.pharma.supplier.repository;

import com.pharma.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByStatus(Supplier.Status status);

    @Query("SELECT s FROM Supplier s WHERE s.status = 'ACTIVE' ORDER BY s.companyName ASC")
    List<Supplier> findAllActive();
}
