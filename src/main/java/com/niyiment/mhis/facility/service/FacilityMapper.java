package com.niyiment.mhis.facility.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.dto.FacilityCreateRequest;
import com.niyiment.mhis.facility.dto.FacilityResponse;
import com.niyiment.mhis.facility.dto.FacilityStatistics;
import com.niyiment.mhis.facility.dto.FacilityStatisticsResponse;
import org.springframework.stereotype.Component;

@Component
public class FacilityMapper {

  public Facility toEntity(FacilityCreateRequest request) {

    return Facility.builder()
        .facilityCode(request.facilityCode())
        .facilityName(request.facilityName())
        .facilityType(request.facilityType())
        .levelOfCare(request.levelOfCare())
        .state(request.state())
        .lga(request.lga())
        .contactPhone(request.contactPhone())
        .dhis2OrgUnitId(request.dhis2OrgUnitId())
        .isActive(true)
        .build();
  }

  public FacilityResponse toResponse(Facility facility) {
    return new FacilityResponse(
        facility.getId(),
        facility.getFacilityCode(),
        facility.getFacilityName(),
        facility.getFacilityType(),
        facility.getLevelOfCare(),
        facility.getState(),
        facility.getLga(),
        facility.getContactPhone(),
        facility.getDhis2OrgUnitId(),
        facility.isActive(),
        facility.getCreatedAt(),
        facility.getUpdatedAt());
  }

  public FacilityStatisticsResponse toStatisticsResponse(FacilityStatistics statistics) {
    return new FacilityStatisticsResponse(
            statistics.facilityId(),
            statistics.facilityCode(),
            statistics.facilityName(),
            statistics.totalPatients(),
            statistics.activeStaff(),
            statistics.utilizationRate(),
            statistics.lastUpdated()
    );
  }
}
