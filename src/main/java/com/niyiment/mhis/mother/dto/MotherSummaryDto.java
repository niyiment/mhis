package com.niyiment.mhis.mother.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.niyiment.mhis.mother.domain.HivStatus;
import lombok.Builder;

@Builder
public record MotherSummaryDto(
    UUID id,
    String ancUniqueId,
    String fullName,
    Integer ageYears,
    LocalDate eddDate,
    Integer gestationalWeeks,
    HivStatus hivStatus,
    Boolean isHighRisk,
    String facilityName,
    LocalDate lastVisitDate,
    LocalDate nextVisitDate
) {}

