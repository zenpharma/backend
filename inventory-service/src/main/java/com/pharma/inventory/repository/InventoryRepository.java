package com.pharma.inventory.repository;

import com.pharma.inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByStatus(InventoryItem.Status status);

    List<InventoryItem> findByItemType(InventoryItem.ItemType itemType);

    @Query("SELECT i FROM InventoryItem i WHERE i.status = 'ACTIVE' ORDER BY i.createdAt DESC")
    List<InventoryItem> findAllActive();
}
