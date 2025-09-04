package com.niyiment.mhis.mother.repository;



import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import com.niyiment.mhis.mother.domain.Mother;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface MotherRepository extends JpaRepository<Mother, UUID> {

    Optional<Mother> findByAncUniqueId(String ancUniqueId);

    Optional<Mother> findByNationalId(String nationalId);

    Page<Mother> findByFacilityFacilityId(UUID facilityId, Pageable pageable);

    Page<Mother> findByHivStatus(HivStatus hivStatus, Pageable pageable);

    Page<Mother> findByArtStatus(ArtStatus artStatus, Pageable pageable);

    List<Mother> findByFacilityFacilityIdAndIsActiveTrue(UUID facilityId);

    boolean existsByAncUniqueId(String ancUniqueId);

    boolean existsByNationalId(String nationalId);

    @Query("SELECT m FROM Mother m WHERE m.facility.facilityId = :facilityId AND " +
            "(m.previousCs = true OR m.previousPph = true OR m.previousEclampsia = true OR " +
            "m.hivStatus = 'POSITIVE' OR m.ageYears < 18 OR m.ageYears > 35)")
    List<Mother> findHighRiskMothers(@Param("facilityId") UUID facilityId);

    @Query("SELECT m FROM Mother m JOIN Pregnancy p ON m.motherId = p.mother.motherId " +
            "WHERE m.facility.facilityId = :facilityId AND p.isCurrent = true")
    List<Mother> findMothersWithCurrentPregnancies(@Param("facilityId") UUID facilityId);

    @Query("SELECT m FROM Mother m WHERE m.facility.facilityId = :facilityId AND " +
            "m.hivStatus = 'POSITIVE' AND " +
            "(m.lastVlDate IS NULL OR m.lastVlDate < :cutoffDate)")
    List<Mother> findMothersDueForViralLoad(@Param("facilityId") UUID facilityId,
                                            @Param("cutoffDate") LocalDate cutoffDate);

    @Query("SELECT m FROM Mother m WHERE m.facility.facilityId = :facilityId AND " +
            "m.hivStatus = 'POSITIVE' AND m.vlSuppressed = false")
    List<Mother> findMothersWithUnsuppressedVl(@Param("facilityId") UUID facilityId);

    @Query("SELECT m FROM Mother m WHERE m.isActive = true AND " +
            "(UPPER(CONCAT(m.firstName, ' ', COALESCE(m.middleName, ''), ' ', m.lastName)) " +
            "LIKE UPPER(CONCAT('%', :searchTerm, '%')) OR " +
            "UPPER(m.ancUniqueId) LIKE UPPER(CONCAT('%', :searchTerm, '%')) OR " +
            "UPPER(COALESCE(m.nationalId, '')) LIKE UPPER(CONCAT('%', :searchTerm, '%')))")
    Page<Mother> searchMothers(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT m.motherId) FROM Mother m WHERE m.facility.facilityId = :facilityId AND " +
            "m.registrationDate >= :startDate AND m.registrationDate <= :endDate")
    Long countNewRegistrations(@Param("facilityId") UUID facilityId,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Mother m WHERE " +
            "m.facility.facilityId = :facilityId AND " +
            "(m.hivStatus = 'POSITIVE' AND m.vlSuppressed = false) OR " +
            "(m.lastVlDate IS NULL AND m.hivStatus = 'POSITIVE') OR " +
            "(m.eddDate BETWEEN :startDate AND :endDate)")
    List<Mother> getMothersRequiringFollowUp(@Param("facilityId") UUID facilityId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT MAX(SUBSTRING(m.ancUniqueId, LENGTH(:facilityCode) + 2)) " +
            "FROM Mother m WHERE m.ancUniqueId LIKE CONCAT(:facilityCode, '-%')")
    Optional<String> findMaxAncSequenceByFacilityCode(@Param("facilityCode") String facilityCode);
}


