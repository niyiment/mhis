package com.niyiment.mhis.facility.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface FacilityService {
    /**
     * Create a new facility
     * @param request
     */
    FacilityResponse createFacility(FacilityCreateRequest request);

    /**
     * Update an existing facility
     * @param facilityId, request
     */
    FacilityResponse updateFacility(UUID facilityId, FacilityUpdateRequest request);

    /**
     * Find facility by ID
     * @param facilityId
     */
    FacilityResponse findById(UUID facilityId);

    /**
     * Find facility by facility code
     * @param facilityCode
     */
    FacilityResponse findByFacilityCode(String facilityCode);

    /**
     * Find all facilities with pagination
     * @param pageable
     */
    Page<FacilityResponse> findAll(Pageable pageable);

    /**
     * Find active facilities
     */
    List<FacilityResponse> findActiveFacilities();

    /**
     * Find facilities by state
     * @param state, activeOnly
     */
    List<FacilityResponse> findByState(String state, boolean activeOnly);

    /**
     * Find facilities by lga
     * @param lga, activeOnly
     */
    List<FacilityResponse> findByLga(String lga, boolean activeOnly);

    /**
     * Find facilities by type
     * @param facilityType, activeOnly
     */
    List<FacilityResponse> findByFacilityType(FacilityType facilityType, boolean activeOnly);

    /**
     * Find facilities by level of care
     * @param levelOfCare, activeOnly
     */
    List<FacilityResponse> findByLevelOfCare(LevelOfCare levelOfCare, boolean activeOnly);

    /**
     * Soft delete facility (set inactive)
     * @param facilityId
     */
    void deactivateFacility(UUID facilityId);

    /**
     * Reactivate facility
     * @param facilityId
     */
    void reactivateFacility(UUID facilityId);

    /**
     * Check if facility code exists
     * @param facilityCode
     */
    boolean existsByFacilityCode(String facilityCode);

    /**
     * Get facility statistics
     * @param facilityId
     */
    FacilityStatisticsResponse getFacilityStatistics(UUID facilityId);

    /**
     * Search facilities by name or code
     * @param request
     */
    Page<FacilityResponse> searchFacilities(FacilitySearchRequest request);

    /**
     * Get active facility count
     */
    long getActiveFacilityCount();

    /**
     * Get active facility count by state
     * @param state
     * @return
     */
    long getActiveFacilityCountByState(String state);
}

