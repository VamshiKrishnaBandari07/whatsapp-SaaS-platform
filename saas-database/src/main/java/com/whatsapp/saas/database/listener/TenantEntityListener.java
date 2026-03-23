package com.whatsapp.saas.database.listener;

import com.whatsapp.saas.core.tenant.TenantContext;
import com.whatsapp.saas.database.entity.TenantAwareEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.UUID;

public class TenantEntityListener {

    @PrePersist
    @PreUpdate
    public void setTenantId(Object entity) {
        if (entity instanceof TenantAwareEntity) {
            UUID tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                ((TenantAwareEntity) entity).setTenantId(tenantId);
            } else {
                // If it's a tenant-aware entity being saved without context, throw error 
                // to prevent cross-tenant data corruption!
                throw new IllegalStateException("Tenant context not found for saving TenantAwareEntity");
            }
        }
    }
}
