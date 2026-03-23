package com.whatsapp.saas.workflow.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.saas.workflow.model.WorkflowTemplateDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateRegistryService {

    private static final Logger log = LoggerFactory.getLogger(TemplateRegistryService.class);
    
    // In production, these templates are saved into the `workflows` table on onboarding
    // This serves as the default seeding repository

    public WorkflowTemplateDefinition getDefaultInteriorDesignTemplate() {
        WorkflowTemplateDefinition def = new WorkflowTemplateDefinition();
        def.setTemplateId("INTERIOR_DESIGN_DEFAULT");
        def.setName("Interior Design Lead Gen");

        WorkflowTemplateDefinition.WorkflowStep step1 = new WorkflowTemplateDefinition.WorkflowStep();
        step1.setStepId("step_1_name");
        step1.setQuestionText("Hi! Welcome to our Interior Design studio. What's your name?");
        step1.setType(WorkflowTemplateDefinition.StepType.TEXT);
        step1.setFieldToSave("customer_name");
        step1.setNextStepId("step_2_service");

        WorkflowTemplateDefinition.WorkflowStep step2 = new WorkflowTemplateDefinition.WorkflowStep();
        step2.setStepId("step_2_service");
        step2.setQuestionText("Nice to meet you! What kind of service are you looking for?");
        step2.setType(WorkflowTemplateDefinition.StepType.OPTIONS);
        step2.setOptions(List.of("Kitchen Remodel", "Living Room", "Full House", "Just Consultation"));
        step2.setFieldToSave("service_type");
        step2.setNextStepId("step_3_budget");

        WorkflowTemplateDefinition.WorkflowStep step3 = new WorkflowTemplateDefinition.WorkflowStep();
        step3.setStepId("step_3_budget");
        step3.setQuestionText("Do you have a rough budget in mind?");
        step3.setType(WorkflowTemplateDefinition.StepType.TEXT);
        step3.setFieldToSave("budget");
        step3.setNextStepId("step_4_booking");

        WorkflowTemplateDefinition.WorkflowStep step4 = new WorkflowTemplateDefinition.WorkflowStep();
        step4.setStepId("step_4_booking");
        step4.setQuestionText("Let's schedule a quick call to discuss your project!");
        step4.setType(WorkflowTemplateDefinition.StepType.BOOKING);
        step4.setNextStepId("step_end");

        def.setSteps(List.of(step1, step2, step3, step4));
        return def;
    }

    public WorkflowTemplateDefinition getDefaultRealEstateTemplate() {
        // ... similar configuration for Real Estate
        WorkflowTemplateDefinition def = new WorkflowTemplateDefinition();
        def.setTemplateId("REAL_ESTATE_DEFAULT");
        def.setName("Real Estate Lead Gen");
        // omitted for brevity
        return def;
    }
}
