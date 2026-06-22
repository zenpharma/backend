package com.pharma.supplier.service;

import com.pharma.supplier.model.Supplier;
import com.pharma.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {

    private final SupplierRepository repository;

    public List<Supplier> findAll() {
        return repository.findAll();
    }

    public Supplier findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
    }

    public Supplier create(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier update(Long id, Supplier updated) {
        Supplier existing = findById(id);
        existing.setCompanyName(updated.getCompanyName());
        existing.setContactPerson(updated.getContactPerson());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Supplier> findActive() {
        return repository.findAllActive();
    }
}
