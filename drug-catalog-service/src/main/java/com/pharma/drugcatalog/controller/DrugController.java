package com.pharma.drugcatalog.controller;

import com.pharma.drugcatalog.model.Drug;
import com.pharma.drugcatalog.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drug/catalog")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService service;

    @GetMapping
    public ResponseEntity<List<Drug>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{drugId}")
    public ResponseEntity<Drug> getById(@PathVariable Long drugId) {
        return ResponseEntity.ok(service.findById(drugId));
    }

    @PostMapping
    public ResponseEntity<Drug> create(@RequestBody Drug entity) {
        return ResponseEntity.ok(service.create(entity));
    }

    @PutMapping("/{drugId}")
    public ResponseEntity<Drug> update(
            @PathVariable Long drugId,
            @RequestBody Drug entity) {
        return ResponseEntity.ok(service.update(drugId, entity));
    }

    @DeleteMapping("/{drugId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long drugId) {
        service.delete(drugId);
        return ResponseEntity.ok(Map.of("message", "Drug deleted successfully"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "drug-catalog-service"));
    }
}
