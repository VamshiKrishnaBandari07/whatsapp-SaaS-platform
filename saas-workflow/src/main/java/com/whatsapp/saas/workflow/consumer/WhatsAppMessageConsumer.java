package com.whatsapp.saas.workflow.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppMessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppMessageConsumer.class);

    // Private final MessageIdempotencyService idempotencyService;
    // Private final WorkflowEngine workflowEngine;

    // public WhatsAppMessageConsumer(MessageIdempotencyService idempotencyService, WorkflowEngine workflowEngine) { ... }

    @KafkaListener(topics = "whatsapp.incoming", groupId = "workflow-group")
    public void consumeIncomingMessage(String payload) {
        log.info("Processing incoming message from Kafka: {}", payload);
        try {
            // 1. Parse JSON payload
            // 2. Extract tenant ID from phone number mapping
            // 3. Extract WhatsApp message_id
            
            // 4. Idempotency Check (will throw exception if message_id exists)
            // boolean isNew = idempotencyService.saveIncomingMessageIfNotExists(tenantId, messageId, payload);
            
            // 5. If new, pass to workflow engine
            // workflowEngine.processNextStep(tenantId, customerId, payload);
                
        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate message received. Ignoring idempotently.");
        } catch (Exception e) {
            log.error("Failed to process message. Sending to DLT.", e);
            throw e; // Relying on Spring Kafka DefaultErrorHandler to retry/DLT
        }
    }
}
