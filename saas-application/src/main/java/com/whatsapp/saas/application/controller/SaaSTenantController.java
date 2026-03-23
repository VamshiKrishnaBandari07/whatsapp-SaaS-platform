package com.whatsapp.saas.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tenant/dashboard")
public class SaaSTenantController {

    // Inject Dashboard Analytics Service here

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getTenantMetrics() {
        // Automatically filtered by TenantContext in Service layer
        return ResponseEntity.ok(Map.of(
            "activeLeads", 145,
            "wonDeals", 23,
            "messagesSent", 1540,
            "aiTokensUsed", 45000,
            "estimatedCost", "$45.20",
            "campaignConversionRate", "12.4%"
        ));
    }

    @GetMapping("/campaigns")
    public ResponseEntity<Object> getCampaigns() {
        // Return running campaigns and their success metrics
        return ResponseEntity.ok(Map.of("status", "success", "data", "[]"));
    }
}
