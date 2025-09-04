package com.niyiment.mhis.mother.dto;

import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HivCareDto(
        HivStatus hivStatus,
        LocalDate hivTestDate,
        ArtStatus artStatus,
        LocalDate artStartDate,
        String currentRegimen,
        Integer lastVlResult,
        LocalDate lastVlDate,
        Boolean vlSuppressed,
        LocalDate nextVlDueDate,

        String testingFacility,
        String counselorName,
        Boolean disclosureStatus,
        LocalDate lastCd4Date,
        Integer lastCd4Count,
        String adherenceLevel // GOOD, FAIR, POOR
) {}
