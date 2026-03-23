package com.whatsapp.saas.webhook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class IncomingWebhookController {

    private static final Logger log = LoggerFactory.getLogger(IncomingWebhookController.class);

    // private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${whatsapp.verify.token:my-secret-token}")
    private String verifyToken;

    // public IncomingWebhookController(KafkaTemplate<String, String> kafkaTemplate) {
    //     this.kafkaTemplate = kafkaTemplate;
    // }

    /**
     * Required by Meta for Webhook URL verification.
     */
    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String token) {

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            log.info("Webhook verified successfully.");
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Receives incoming messages and status updates from WhatsApp.
     * Must return 200 OK immediately to prevent Meta from retrying and blocking the queue.
     */
    @PostMapping
    public ResponseEntity<Void> receiveMessage(@RequestBody String payload) {
        log.debug("Received WhatsApp Webhook payload: {}", payload);
        
        // Push payload to Kafka for async processing (Idempotency managed by consumers)
        // kafkaTemplate.send("whatsapp.incoming", payload);
        
        // Fast return 200 OK
        return ResponseEntity.ok().build();
    }
}
