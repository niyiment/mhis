package com.niyiment.mhis.mother.dto;


import lombok.Builder;

@Builder
public record MotherProfile(
        MotherResponse mother,
        PregnancyDto pregnancies,
        VisitDto recentVisits,
        RiskAssessmentDto riskAssessment,
        HivCareDto hivCare,
        PartnerDetailsDto partnerDetails
) {}
