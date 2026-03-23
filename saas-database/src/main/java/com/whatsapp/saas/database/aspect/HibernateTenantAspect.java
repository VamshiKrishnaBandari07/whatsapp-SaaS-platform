package com.whatsapp.saas.database.aspect;

import com.whatsapp.saas.core.tenant.TenantContext;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HibernateTenantAspect {

    private final EntityManager entityManager;

    public HibernateTenantAspect(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Pointcut("execution(* org.springframework.data.repository.Repository+.*(..))")
    public void anyRepositoryMethod() {
    }

    @Before("anyRepositoryMethod()")
    public void enableTenantFilter() {
        if (TenantContext.getTenantId() != null) {
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("tenantFilter").setParameter("tenantId", TenantContext.getTenantId());
        }
    }
}
