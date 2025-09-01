package com.niyiment.mhis.facility.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;

import java.time.LocalDateTime;
import java.util.UUID;

public record FacilityResponse(
        UUID facilityId,
        String facilityCode,
        String facilityName,
        FacilityType facilityType,
        LevelOfCare levelOfCare,
        String state,
        String lga,
        String contactPhone,
        String dhis2OrgUnitId,
        Boolean isActive,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {}
