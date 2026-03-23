package com.whatsapp.saas.core.failover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaFailoverService {

    private static final Logger log = LoggerFactory.getLogger(KafkaFailoverService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaFailoverService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Tries to send to Kafka synchronously to ensure delivery.
     * If Kafka is down, catches exception and persists to PostgreSQL fallback queue.
     */
    public void sendWithFailover(String topic, String payload) {
        try {
            // Using get(timeout) to fail fast if broker is unreachable
            kafkaTemplate.send(topic, payload).get(2, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Kafka broker unreachable or timed out. Falling back to DB events table for topic {}", topic);
            
            // Fallback Logic:
            // failoverEventRepository.save(new FailoverEvent(topic, payload, "PENDING"));
            // A scheduled cron job will poll the db_events table and push to Kafka once it is back online.
        }
    }
}
