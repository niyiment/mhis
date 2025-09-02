package com.niyiment.mhis.facility.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.FacilityStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService{
    @Override
    public Facility createFacility(Facility facility) {
        return null;
    }

    @Override
    public Facility updateFacility(UUID facilityId, Facility facility) {
        return null;
    }

    @Override
    public Optional<Facility> findById(UUID facilityId) {
        return Optional.empty();
    }

    @Override
    public Optional<Facility> findByFacilityCode(String facilityCode) {
        return Optional.empty();
    }

    @Override
    public Page<Facility> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Facility> findActiveFacilities() {
        return List.of();
    }

    @Override
    public List<Facility> findByDistrict(String district) {
        return List.of();
    }

    @Override
    public List<Facility> findByRegion(String region) {
        return List.of();
    }

    @Override
    public List<Facility> findByFacilityType(FacilityType facilityType) {
        return List.of();
    }

    @Override
    public List<Facility> findByLevelOfCare(LevelOfCare levelOfCare) {
        return List.of();
    }

    @Override
    public void deactivateFacility(UUID facilityId) {

    }

    @Override
    public void reactivateFacility(UUID facilityId) {

    }

    @Override
    public boolean existsByFacilityCode(String facilityCode) {
        return false;
    }

    @Override
    public FacilityStatistics getFacilityStatistics(UUID facilityId) {
        return null;
    }

    @Override
    public List<Facility> searchFacilities(String searchTerm) {
        return List.of();
    }
}
