package com.whatsapp.saas.workflow.model;

import lombok.Data;
import java.util.List;

@Data
public class WorkflowTemplateDefinition {

    private String templateId; // INTERIOR_DESIGN, REAL_ESTATE
    private String name;
    private List<WorkflowStep> steps;

    @Data
    public static class WorkflowStep {
        private String stepId;
        private String fieldToSave; // e.g., 'customer_name', 'budget'
        private String questionText;
        private StepType type; // TEXT, OPTIONS, DATE, BOOKING
        private List<String> options; // if type == OPTIONS
        private String nextStepId; // Static next step or dynamic rule evaluating
    }

    public enum StepType {
        TEXT,
        OPTIONS,
        DATE,
        BOOKING,
        AI_RAG_HANDOFF,
        END
    }
}
