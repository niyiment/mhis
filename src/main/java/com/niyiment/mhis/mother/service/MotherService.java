package com.niyiment.mhis.mother.service;

import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import com.niyiment.mhis.mother.domain.Mother;
import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.mother.dto.MotherProfile;
import com.niyiment.mhis.mother.dto.MotherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MotherService {

    /**
     * Register a new mother
     */
    MotherResponse registerMother(MotherCreateRequest mother);

    /**
     * Update mother information
     */
    MotherResponse updateMother(UUID motherId, MotherCreateRequest request);

    /**
     * Find mother by ID
     */
    MotherResponse findById(UUID motherId);

    /**
     * Find mother by ANC unique ID
     */
    MotherResponse findByAncUniqueId(String ancUniqueId);

    /**
     * Find mother by national ID
     */
    MotherResponse findByNationalId(String nationalId);

    /**
     * Find all mothers with pagination
     */
    Page<MotherResponse> findAll(Pageable pageable);

    /**
     * Find mothers by facility
     */
    Page<MotherResponse> findByFacility(UUID facilityId, Pageable pageable);

    /**
     * Find mothers by HIV status
     */
    Page<MotherResponse> findByHivStatus(HivStatus hivStatus, Pageable pageable);

    /**
     * Find mothers by ART status
     */
    Page<MotherResponse> findByArtStatus(ArtStatus artStatus, Pageable pageable);

    /**
     * Find high-risk mothers
     */
    List<MotherResponse> findHighRiskMothers(UUID facilityId);

    /**
     * Find mothers with current pregnancies
     */
    List<MotherResponse> findMothersWithCurrentPregnancies(UUID facilityId);

    /**
     * Find mothers due for viral load test
     */
    List<MotherResponse> findMothersDueForViralLoad(UUID facilityId);

    /**
     * Find mothers with unsuppressed viral load
     */
    List<MotherResponse> findMothersWithUnsuppressedVl(UUID facilityId);

    /**
     * Search mothers by name or ID
     */
    Page<MotherResponse> searchMothers(String searchTerm, Pageable pageable);

    /**
     * Update mother's HIV status and related information
     */
    MotherResponse updateHivStatus(UUID motherId, HivStatus hivStatus, LocalDate testDate);

    /**
     * Update mother's ART information
     */
    MotherResponse updateArtInformation(UUID motherId, ArtStatus artStatus,
                                String regimen, LocalDate startDate);

    /**
     * Record viral load result
     */
    MotherResponse recordViralLoadResult(UUID motherId, Integer vlResult, LocalDate testDate);

    /**
     * Check if ANC ID exists
     */
    boolean existsByAncUniqueId(String ancUniqueId);

    /**
     * Check if National ID exists
     */
    boolean existsByNationalId(String nationalId);

    /**
     * Get mothers requiring follow-up
     */
    List<MotherResponse> getMothersRequiringFollowUp(UUID facilityId);

    /**
     * Generate next ANC unique ID
     */
    String generateNextAncUniqueId(UUID facilityId);

    MotherProfile getMotherProfile(UUID motherId);
}