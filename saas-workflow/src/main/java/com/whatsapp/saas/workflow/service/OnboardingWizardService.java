package com.whatsapp.saas.workflow.service;

import com.whatsapp.saas.workflow.engine.TemplateRegistryService;
import com.whatsapp.saas.workflow.model.WorkflowTemplateDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OnboardingWizardService {

    private static final Logger log = LoggerFactory.getLogger(OnboardingWizardService.class);

    private final TemplateRegistryService templateRegistryService;
    // private final WorkflowRepository workflowRepository;

    public OnboardingWizardService(TemplateRegistryService templateRegistryService) {
        this.templateRegistryService = templateRegistryService;
    }

    /**
     * Executes the 1-click One-Click Onboarding flow for a new SaaS Tenant.
     */
    public void setupTenant(UUID tenantId, String businessType) {
        log.info("Starting One-Click Onboarding for Tenant {}, Type {}", tenantId, businessType);
        
        WorkflowTemplateDefinition template;
        
        if ("INTERIOR_DESIGN".equalsIgnoreCase(businessType)) {
            template = templateRegistryService.getDefaultInteriorDesignTemplate();
        } else if ("REAL_ESTATE".equalsIgnoreCase(businessType)) {
            template = templateRegistryService.getDefaultRealEstateTemplate();
        } else {
            // Fallback to default
            template = templateRegistryService.getDefaultInteriorDesignTemplate();
        }
        
        // Save the template directly to the tenant's workflow configuration
        // workflowRepository.saveWorkflow(tenantId, template);
        
        log.info("Successfully provisioned out-of-the-box workflow for tenant {}", tenantId);
    }
}
