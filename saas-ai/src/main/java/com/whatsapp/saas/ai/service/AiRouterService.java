package com.whatsapp.saas.ai.service;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AiRouterService {

    private static final Logger log = LoggerFactory.getLogger(AiRouterService.class);

    private final ChatLanguageModel chatModel;
    
    // Abstracted LLM Agent defining instructions
    interface Assistant {
        @SystemMessage("You are a helpful assistant for a business. Answer strictly based on context. Do not invent pricing.")
        String chat(String userMessage);
    }

    public AiRouterService(@Value("${openai.api.key:dummy_key_for_build}") String apiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .maxTokens(150)
                .temperature(0.2) // Low temp for deterministic RAG answers
                .build();
    }

    /**
     * AI Fallback when the intent is a QUESTION, or the deterministic workflow doesn't match options.
     */
    public String handleAiFallback(UUID tenantId, UUID conversationId, String userMessage) {
        log.info("LLM Fallback triggered for Tenant: {} on Conversation: {}", tenantId, conversationId);

        // 1. Fetch RAG Context (Vector DB Search). Crucially, MUST filter by tenantId to prevent data leakage!
        // String context = vectorDbService.findNearest(tenantId, userMessage, 3);
        String mockContext = "Company works monday to friday, 9 to 5.";

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(5))
                .build();

        // 2. Pass context + message to LLM
        String response = assistant.chat("Context: [" + mockContext + "] \n User: " + userMessage);
        
        return response;
    }
}
