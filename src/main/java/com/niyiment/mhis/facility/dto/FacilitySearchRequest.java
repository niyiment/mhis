package com.niyiment.mhis.facility.dto;

import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;

public record FacilitySearchRequest(
        String searchTerm,
        String state,
        String lga,
        FacilityType facilityType,
        LevelOfCare levelOfCare,
        Boolean isActive,
        int page,
        int size,
        String sortBy,
        String sortDirection
) {
    public FacilitySearchRequest {
        if (page < 0) page = 0;
        if (size <= 0 || size > 100) size = 20;
        if (sortBy == null || sortBy.isBlank()) sortBy = "facilityName";
        if (sortDirection == null || !sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")){
            sortDirection = "asc";
        }
        if (isActive == null) isActive = true;
    }

}
