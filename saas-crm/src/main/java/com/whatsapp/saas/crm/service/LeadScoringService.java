package com.whatsapp.saas.crm.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class LeadScoringService {

    public enum LeadScoreCategory {
        HOT, WARM, COLD
    }

    /**
     * Algorithm to classify leads based on conversational AI data extraction and urgency indicators.
     * @param extractedData the parsed JSON block containing user data mapped from workflow fields
     * @param responseTimeMinutes how quickly the user responds to chatbot questions
     */
    public LeadScoreCategory calculateScore(Map<String, String> extractedData, int responseTimeMinutes) {
        int score = 0;

        // Budget indicated
        if (extractedData.containsKey("budget") && !extractedData.get("budget").isBlank()) {
            score += 40;
        }

        // Timeline urgency (extracted by AI or from dropdown)
        if (extractedData.containsKey("urgency")) {
            String urgency = extractedData.get("urgency").toLowerCase();
            if (urgency.contains("asap") || urgency.contains("this week") || urgency.contains("immediate")) {
                score += 30;
            }
        }

        // Fast interactive response from lead
        if (responseTimeMinutes < 15) {
            score += 20;
        }

        if (score >= 70) return LeadScoreCategory.HOT;
        if (score >= 40) return LeadScoreCategory.WARM;
        return LeadScoreCategory.COLD;
    }
}
