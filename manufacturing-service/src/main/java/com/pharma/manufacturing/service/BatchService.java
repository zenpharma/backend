package com.pharma.manufacturing.service;

import com.pharma.manufacturing.model.Batch;
import com.pharma.manufacturing.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchService {

    private final BatchRepository repository;

    public List<Batch> findAll() {
        return repository.findAll();
    }

    public Batch findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
    }

    public Batch create(Batch batch) {
        return repository.save(batch);
    }

    public Batch update(Long id, Batch updated) {
        Batch existing = findById(id);
        existing.setProductName(updated.getProductName());
        existing.setQuantity(updated.getQuantity());
        existing.setScheduledDate(updated.getScheduledDate());
        existing.setAssignedLine(updated.getAssignedLine());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Batch> findActive() {
        return repository.findAllActive();
    }
}
