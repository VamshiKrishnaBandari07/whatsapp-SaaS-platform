package com.whatsapp.saas.workflow.engine;

import com.whatsapp.saas.workflow.model.WorkflowTemplateDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkflowEngine {

    private static final Logger log = LoggerFactory.getLogger(WorkflowEngine.class);
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    // private final ConversationRepository conversationRepository;
    // private final WorkflowRepository workflowRepository;

    public WorkflowEngine(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Core state machine transition logic.
     * Takes incoming message, evaluates against current state, saves data, transitions state, triggers next action.
     */
    public void processMessage(UUID tenantId, UUID conversationId, String messageContent) {
        log.info("Executing workflow for Tenant: {}, Conversation: {}", tenantId, conversationId);

        // 1. Fetch Conversation from DB (with Optimistic Locking version mapping)
        // Conversation conv = conversationRepository.findById(conversationId).orElseThrow();
        
        // 2. Load active Workflow definition for this tenant
        // WorkflowTemplateDefinition workflowDef = workflowRepository.findByTenantId(tenantId);
        
        // 3. Find current step matching conv.getCurrentStep()
        // WorkflowTemplateDefinition.WorkflowStep currentStep = findStep(workflowDef, conv.getCurrentStep());

        // 4. Validate input against step rules (e.g., if OPTIONS, check if message is in options bounds)
        // if (!isValid(currentStep, messageContent)) {
        //     sendOutgoingMessage(tenantId, conversationId, "Sorry, didn't catch that. " + currentStep.getQuestionText());
        //     return;
        // }

        // 5. Update Conversation state
        // conv.getCompletedFields().put(currentStep.getFieldToSave(), messageContent);
        // conv.setCurrentStep(currentStep.getNextStepId());
        
        // 6. Save Conversation (will throw OptimisticLockException if concurrent messages arrived)
        // conversationRepository.save(conv);

        // 7. Find Next Step and Execute
        // WorkflowTemplateDefinition.WorkflowStep nextStep = findStep(workflowDef, conv.getCurrentStep());
        // if (nextStep.getType() == WorkflowTemplateDefinition.StepType.AI_RAG_HANDOFF) {
        //     kafkaTemplate.send("ai.process", "trigger RAG");
        // } else if (nextStep.getType() == WorkflowTemplateDefinition.StepType.BOOKING) {
        //     sendOutgoingMessage(tenantId, conversationId, nextStep.getQuestionText());
        // } else {
        //     sendOutgoingMessage(tenantId, conversationId, nextStep.getQuestionText());
        // }
    }

    private void sendOutgoingMessage(UUID tenantId, UUID conversationId, String text) {
        // Create an OutgoingMessageEvent JSON and push to 'whatsapp.outgoing'
        String payload = String.format("{\"tenantId\":\"%s\", \"conversationId\":\"%s\", \"text\":\"%s\"}", tenantId, conversationId, text);
        kafkaTemplate.send("whatsapp.outgoing", payload);
    }
}
