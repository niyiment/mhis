package com.niyiment.mhis.mother.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record RiskAssessmentDto(
        Boolean isHighRisk,
        String riskLevel, // LOW, MEDIUM, HIGH, CRITICAL
        List<String> riskFactors,
        LocalDate assessmentDate,
        String assessedBy,
        String notes,
        Integer riskScore,
        String riskCategory //OBSTERIC, MEDICAL, SOCIAL, HIV
){}
