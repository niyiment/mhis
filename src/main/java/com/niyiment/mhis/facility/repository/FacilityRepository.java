package com.niyiment.mhis.facility.repository;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface FacilityRepository extends JpaRepository<Facility, UUID>,
        JpaSpecificationExecutor<Facility> {

    Optional<Facility> findByFacilityCode(String facilityCode);

    List<Facility> findByIsActiveTrue();

    List<Facility> findByState(String state);

    List<Facility> findByStateAndIsActiveTrue(String state);

    List<Facility> findByLga(String lga);

    List<Facility> findByLgaAndIsActiveTrue(String lga);

    List<Facility> findByFacilityType(FacilityType facilityType);

    List<Facility> findByFacilityTypeAndIsActiveTrue(FacilityType facilityType);

    List<Facility> findByLevelOfCare(LevelOfCare levelOfCare);

    List<Facility> findByLevelOfCareAndIsActiveTrue(LevelOfCare levelOfCare);

    boolean existsByFacilityCode(String facilityCode);

    boolean existsByFacilityCodeAndIdNot(String facilityCode, UUID id);

    @Query("SELECT f FROM Facility f WHERE f.isActive = true AND " +
            "(UPPER(f.facilityName) LIKE UPPER(CONCAT('%', :searchTerm, '%')) OR " +
            "UPPER(f.facilityCode) LIKE UPPER(CONCAT('%', :searchTerm, '%')))")
    List<Facility> searchByNameOrCode(@Param("searchTerm") String searchTerm);

    @Query("SELECT f FROM Facility f WHERE f.state = :state AND f.isActive = true")
    List<Facility> findActiveFacilitiesByState(@Param("state") String state);

    @Query("SELECT COUNT(f) FROM Facility f WHERE f.isActive = true")
    long countActiveFacilities();

    @Query("SELECT COUNT(f) FROM Facility f WHERE f.isActive = true AND f.state = :state")
    long countActiveFacilitiesByState(@Param("state") String state);

    @Query("SELECT COUNT(f) FROM Facility f WHERE f.isActive = true AND f.lga = :lga")
    long countActiveFacilitiesByLga(@Param("lga") String lga);
}

