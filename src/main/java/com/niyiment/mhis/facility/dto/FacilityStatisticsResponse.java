package com.niyiment.mhis.facility.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public record FacilityStatisticsResponse(
        UUID facilityId,
        String facilityCode,
        String facilityName,
        Long totalPatients,
        Long activeStaff,
        Double utilizationRate,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime lastUpdated
) {}
