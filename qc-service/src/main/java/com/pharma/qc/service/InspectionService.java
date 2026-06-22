package com.pharma.qc.service;

import com.pharma.qc.model.Inspection;
import com.pharma.qc.repository.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InspectionService {

    private final InspectionRepository repository;

    public List<Inspection> findAll() {
        return repository.findAll();
    }

    public Inspection findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + id));
    }

    public List<Inspection> findByBatchNumber(String batchNumber) {
        return repository.findByBatchNumber(batchNumber);
    }

    public List<Inspection> findByResult(String result) {
        return repository.findByResult(Inspection.Result.valueOf(result.toUpperCase()));
    }

    public Inspection create(Inspection inspection) {
        return repository.save(inspection);
    }

    public Inspection update(Long id, Inspection updated) {
        Inspection existing = findById(id);
        existing.setProductName(updated.getProductName());
        existing.setInspectionType(updated.getInspectionType());
        existing.setResult(updated.getResult());
        existing.setInspectorName(updated.getInspectorName());
        existing.setNotes(updated.getNotes());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
