package com.pharma.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MockDataController {

    @GetMapping(value = "/distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Map<String, Object>>> getDistribution() {
        return Mono.just(List.of(
            Map.of("id", 1, "shipmentNumber", "SHIP-2024-001", "destination", "City Hospital, Mumbai", "carrier", "BlueDart Logistics", "dispatchDate", "2024-04-05", "estimatedArrival", "2024-04-07", "status", "ACTIVE"),
            Map.of("id", 2, "shipmentNumber", "SHIP-2024-002", "destination", "Apollo Pharmacy, Delhi", "carrier", "DHL Express", "dispatchDate", "2024-04-06", "estimatedArrival", "2024-04-08", "status", "ACTIVE"),
            Map.of("id", 3, "shipmentNumber", "SHIP-2024-003", "destination", "Medplus, Bangalore", "carrier", "FedEx", "dispatchDate", "2024-04-08", "estimatedArrival", "2024-04-10", "status", "ACTIVE")
        ));
    }

    @GetMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Map<String, Object>>> getReports() {
        return Mono.just(List.of(
            Map.of("id", 1, "reportName", "Monthly Production Summary", "reportType", "Production", "generatedBy", "admin", "parameters", "April 2024", "status", "ACTIVE"),
            Map.of("id", 2, "reportName", "Inventory Stock Report", "reportType", "Inventory", "generatedBy", "admin", "parameters", "Q1 2024", "status", "ACTIVE"),
            Map.of("id", 3, "reportName", "QC Failure Analysis", "reportType", "Quality", "generatedBy", "pharmacist1", "parameters", "April 2024", "status", "ACTIVE"),
            Map.of("id", 4, "reportName", "Supplier Performance Report", "reportType", "Procurement", "generatedBy", "admin", "parameters", "FY 2024", "status", "ACTIVE")
        ));
    }
}
