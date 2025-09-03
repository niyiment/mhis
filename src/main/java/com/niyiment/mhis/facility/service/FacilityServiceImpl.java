package com.niyiment.mhis.facility.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.*;
import com.niyiment.mhis.facility.exception.DuplicateFacilityCodeException;
import com.niyiment.mhis.facility.exception.FacilityNotFoundException;
import com.niyiment.mhis.facility.repository.FacilityRepository;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import com.niyiment.mhis.facility.repository.FacilitySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    private final FacilityValidationService validationService;
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9]{3,20}$");


    @Override
    @Transactional
    public FacilityResponse createFacility(FacilityCreateRequest request) {
        log.info("Creating facility with code: {}", request.facilityCode());

        if (facilityRepository.existsByFacilityCode(request.facilityCode())) {
            throw new DuplicateFacilityCodeException(request.facilityCode());
        }
        Facility facility = facilityMapper.toEntity(request);
        validationService.validateFacilityCreation(facility);
        facility.setFacilityCode(validateAndFormatFacilityCode(request.facilityCode()));
        Facility savedFacility = facilityRepository.save(facility);

        log.info("Created facility with ID: {} and code: {}",
                savedFacility.getId(), savedFacility.getFacilityCode());
        return facilityMapper.toResponse(savedFacility);
    }

    @Override
    @Transactional
    public FacilityResponse updateFacility(UUID facilityId, FacilityUpdateRequest request) {
        log.info("Updating facility with ID: {}", facilityId);
        Facility existingFacility = findEntityById(facilityId);
        existingFacility.setFacilityName(request.facilityName());
        existingFacility.setFacilityType(request.facilityType());
        existingFacility.setLevelOfCare(request.levelOfCare());
        existingFacility.setState(request.state());
        existingFacility.setLga(request.lga());
        existingFacility.setContactPhone(request.contactPhone());
        existingFacility.setDhis2OrgUnitId(request.dhis2OrgUnitId());
        validationService.validateFacilityUpdate(existingFacility, existingFacility);
        Facility updatedFacility = facilityRepository.save(existingFacility);
        log.info("Updated facility with ID: {}", updatedFacility.getId());

        return facilityMapper.toResponse(updatedFacility);
    }

    @Override
    public FacilityResponse findById(UUID facilityId) {
        log.debug("Finding facility with ID: {}", facilityId);
        Facility facility = findEntityById(facilityId);

        return facilityMapper.toResponse(facility);
    }

    @Override
    public FacilityResponse findByFacilityCode(String facilityCode) {
        log.debug("Finding facility with code: {}", facilityCode);
        Facility facility = facilityRepository.findByFacilityCode(facilityCode)
                .orElseThrow(() -> new FacilityNotFoundException(facilityCode));

        return facilityMapper.toResponse(facility);
    }

    @Override
    public Page<FacilityResponse> findAll(Pageable pageable) {
        log.debug("Finding all facilities");
        Page<Facility> facilities = facilityRepository.findAll(pageable);

        return facilities.map(facilityMapper::toResponse);
    }

    @Override
    public List<FacilityResponse> findActiveFacilities() {
        log.debug("Find all active facilities");
        List<Facility> facilities = facilityRepository.findByIsActiveTrue();

        return facilities.stream()
                .map(facilityMapper::toResponse)
                .toList();
    }

    @Override
    public List<FacilityResponse> findByState(String state, boolean activeOnly) {
        log.debug("Find facilities by state: {}, active only: {}", state, activeOnly);
        List<Facility> facilities = activeOnly ?
                facilityRepository.findByStateAndIsActiveTrue(state)
                : facilityRepository.findByState(state);

        return facilities.stream()
                .map(facilityMapper::toResponse)
                .toList();
    }

    @Override
    public List<FacilityResponse> findByLga(String lga, boolean activeOnly) {
        log.debug("Find facilities by lga: {}, active only: {}", lga, activeOnly);
        List<Facility> facilities = activeOnly ?
                facilityRepository.findByLgaAndIsActiveTrue(lga)
                : facilityRepository.findByLga(lga);

        return facilities.stream()
                .map(facilityMapper::toResponse)
                .toList();
    }

    @Override
    public List<FacilityResponse> findByFacilityType(FacilityType facilityType, boolean activeOnly) {
        log.debug("Find facilities by type: {}, activeOnly: {}", facilityType, activeOnly);
        List<Facility> facilities = activeOnly ?
                facilityRepository.findByFacilityTypeAndIsActiveTrue(facilityType)
                : facilityRepository.findByFacilityType(facilityType);
        return facilities.stream()
                .map(facilityMapper::toResponse)
                .toList();
    }

    @Override
    public List<FacilityResponse> findByLevelOfCare(LevelOfCare levelOfCare, boolean activeOnly) {
        log.debug("Find facilities by level of care: {}, activeOnly: {}", levelOfCare, activeOnly);
        List<Facility> facilities = activeOnly ?
                facilityRepository.findByLevelOfCareAndIsActiveTrue(levelOfCare)
                : facilityRepository.findByLevelOfCare(levelOfCare);
        return facilities.stream()
                .map(facilityMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deactivateFacility(UUID facilityId) {
        log.info("Deactivating facility with ID: {}", facilityId);
        Facility facility = findEntityById(facilityId);
        facility.deactivate();;
        facilityRepository.save(facility);
        log.info("Deactivated facility with ID: {}", facilityId);
    }

    @Override
    @Transactional
    public void reactivateFacility(UUID facilityId) {
        log.info("Reactivating facility with ID: {}", facilityId);
        Facility facility = findEntityById(facilityId);
        facility.activate();
        facilityRepository.save(facility);
        log.info("Reactivated facility with ID: {}", facilityId);
    }

    @Override
    public boolean existsByFacilityCode(String facilityCode) {
        return facilityRepository.existsByFacilityCode(facilityCode);
    }

    @Override
    public FacilityStatisticsResponse getFacilityStatistics(UUID facilityId) {
        log.debug("Getting facility statistics for facility with ID: {}", facilityId);
        Facility facility = findEntityById(facilityId);

        FacilityStatistics statistics = FacilityStatistics.empty(
                facility.getId(),
                facility.getFacilityCode(),
                facility.getFacilityName()
        );

        return facilityMapper.toStatisticsResponse(statistics);
    }

    @Override
    public Page<FacilityResponse> searchFacilities(FacilitySearchRequest request) {
        log.debug("Searching facilities with request: {}", request);

        Specification<Facility> specification = FacilitySpecification.searchFacilities(request);

        Sort sort = Sort.by(request.sortDirection().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC, request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);
        Page<Facility> facilities = facilityRepository.findAll(specification, pageable);

        return facilities.map(facilityMapper::toResponse);
    }

    @Override
    public long getActiveFacilityCount() {
        return facilityRepository.countActiveFacilities();
    }

    @Override
    public long getActiveFacilityCountByState(String state) {
        return facilityRepository.countActiveFacilitiesByState(state);
    }

    private Facility findEntityById(UUID id) {
        return facilityRepository.findById(id).orElseThrow(() -> new FacilityNotFoundException(id));
    }

    private String validateAndFormatFacilityCode(String code) {
        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("Facility code cannot be empty");
        }
        String formatted = code.trim().toUpperCase();
        if (!CODE_PATTERN.matcher(formatted).matches()) {
            throw new IllegalArgumentException("Facility code must be 3 - 20 characters long and contain only uppercase letters and numbers");
        }

        return formatted;
    }
}

