package com.pharma.inventory.service;

import com.pharma.inventory.model.InventoryItem;
import com.pharma.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository repository;

    public List<InventoryItem> findAll() {
        return repository.findAll();
    }

    public InventoryItem findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
    }

    public InventoryItem create(InventoryItem item) {
        return repository.save(item);
    }

    public InventoryItem update(Long id, InventoryItem updated) {
        InventoryItem existing = findById(id);
        existing.setItemName(updated.getItemName());
        existing.setItemType(updated.getItemType());
        existing.setQuantity(updated.getQuantity());
        existing.setUnitOfMeasure(updated.getUnitOfMeasure());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<InventoryItem> findActive() {
        return repository.findAllActive();
    }
}
