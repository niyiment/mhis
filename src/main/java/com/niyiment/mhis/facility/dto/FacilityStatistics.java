package com.niyiment.mhis.facility.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record FacilityStatistics(
        UUID facilityId,
        String facilityCode,
        String facilityName,
        Long totalPatients,
        Long activeStaff,
        Double utilizationRate,
        java.time.LocalDateTime lastUpdated
) {
    public static FacilityStatistics empty(UUID facilityId, String facilityCode, String facilityName) {
        return new FacilityStatistics(
                facilityId,
                facilityCode,
                facilityName,
                0L,
                0L,
                0.0,
                LocalDateTime.now()
        );
    }
}
