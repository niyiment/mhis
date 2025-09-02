package com.niyiment.mhis.facility.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.FacilityStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface FacilityService {
    /**
     * Create a new facility
     */
    Facility createFacility(Facility facility);

    /**
     * Update an existing facility
     */
    Facility updateFacility(UUID facilityId, Facility facility);

    /**
     * Find facility by ID
     */
    Optional<Facility> findById(UUID facilityId);

    /**
     * Find facility by facility code
     */
    Optional<Facility> findByFacilityCode(String facilityCode);

    /**
     * Find all facilities with pagination
     */
    Page<Facility> findAll(Pageable pageable);

    /**
     * Find active facilities
     */
    List<Facility> findActiveFacilities();

    /**
     * Find facilities by district
     */
    List<Facility> findByDistrict(String district);

    /**
     * Find facilities by region
     */
    List<Facility> findByRegion(String region);

    /**
     * Find facilities by type
     */
    List<Facility> findByFacilityType(FacilityType facilityType);

    /**
     * Find facilities by level of care
     */
    List<Facility> findByLevelOfCare(LevelOfCare levelOfCare);

    /**
     * Soft delete facility (set inactive)
     */
    void deactivateFacility(UUID facilityId);

    /**
     * Reactivate facility
     */
    void reactivateFacility(UUID facilityId);

    /**
     * Check if facility code exists
     */
    boolean existsByFacilityCode(String facilityCode);

    /**
     * Get facility statistics
     */
    FacilityStatistics getFacilityStatistics(UUID facilityId);

    /**
     * Search facilities by name or code
     */
    List<Facility> searchFacilities(String searchTerm);
}

