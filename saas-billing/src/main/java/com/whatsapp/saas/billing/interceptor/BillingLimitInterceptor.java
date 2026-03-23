package com.whatsapp.saas.billing.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class BillingLimitInterceptor {

    private static final Logger log = LoggerFactory.getLogger(BillingLimitInterceptor.class);

    // This Pointcut intercepts the AI generating method
    @Around("execution(* com.whatsapp.saas.ai.service.AiRouterService.handleAiFallback(..)) && args(tenantId, ..)")
    public Object enforceAiBillingLimits(ProceedingJoinPoint joinPoint, UUID tenantId) throws Throwable {
        
        log.info("Billing Interceptor: Checking AI usage limits for Tenant: {}", tenantId);

        // 1. Query billing_metrics for this tenant (cached in Redis ideally)
        // int tokensUsed = billingMetricsService.getTokensUsedThisMonth(tenantId);
        // int limit = tenantService.getPlanLimits(tenantId).getMaxAiTokens();

        boolean hitLimit = false; 

        if (hitLimit) {
            log.warn("AI limits exceeded for Tenant {}. Graceful degradation triggered.", tenantId);
            return "I am unable to answer right now, however, I am connecting you to our human agent. Please hold!";
        }

        // Proceed to generate AI response
        Object result = joinPoint.proceed();

        // Increment usage asynchronously after generation
        log.info("Billing Interceptor: Incrementing token usage for Tenant: {}", tenantId);
        // kafkaTemplate.send("billing.event", "increment_token:150");

        return result;
    }
}
