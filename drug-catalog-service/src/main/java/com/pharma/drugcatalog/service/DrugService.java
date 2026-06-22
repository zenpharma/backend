package com.pharma.drugcatalog.service;

import com.pharma.drugcatalog.model.Drug;
import com.pharma.drugcatalog.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugService {

    private final DrugRepository repository;

    public List<Drug> findAll() {
        return repository.findAll();
    }

    public Drug findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found with id: " + id));
    }

    public Drug create(Drug entity) {
        return repository.save(entity);
    }

    public Drug update(Long id, Drug updated) {
        Drug existing = findById(id);
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Drug> findActive() {
        return repository.findAllActive();
    }
}
