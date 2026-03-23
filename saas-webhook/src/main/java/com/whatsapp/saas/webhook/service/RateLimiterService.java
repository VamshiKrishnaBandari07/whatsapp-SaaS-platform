package com.whatsapp.saas.webhook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic in-memory rate limiter using Token Bucket / Window concept for WhatsApp rate limits.
 * In a fully distributed production system, this would be backed by Redis (e.g. Redisson RateLimiter).
 */
@Service
public class RateLimiterService {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterService.class);
    
    // Simplistic mock for Tenant -> Call Count mapping
    private final ConcurrentHashMap<UUID, AtomicInteger> tenantLimits = new ConcurrentHashMap<>();

    private static final int MAX_MESSAGES_PER_SECOND = 50;

    /**
     * Checks if the tenant has exceeded their allowed burst rate.
     */
    public boolean checkRateLimit(UUID tenantId) {
        AtomicInteger count = tenantLimits.computeIfAbsent(tenantId, k -> new AtomicInteger(0));
        if (count.incrementAndGet() > MAX_MESSAGES_PER_SECOND) {
            log.warn("Rate limit exceeded for tenant {}", tenantId);
            return false;
        }
        return true;
    }

    /**
     * Called periodically (e.g. every second via @Scheduled) to reset the bucket.
     */
    public void resetBuckets() {
        tenantLimits.clear();
    }
}
