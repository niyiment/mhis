package com.niyiment.mhis.mother.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PregnancyDto (
    UUID pregnancyId,
    LocalDate lmpDate,
    LocalDate eddDate,
    Integer gestationalWeeks,
    Boolean isCurrent,
    String pregnancyOutcome,
    LocalDate outcomeDate,
    Integer birthWeight,
    String complications
){}
