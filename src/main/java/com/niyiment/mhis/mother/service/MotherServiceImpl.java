package com.niyiment.mhis.mother.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.repository.FacilityRepository;
import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import com.niyiment.mhis.mother.domain.Mother;
import com.niyiment.mhis.mother.dto.*;
import com.niyiment.mhis.mother.mapper.MotherMapper;
import com.niyiment.mhis.mother.repository.MotherRepository;
import com.niyiment.mhis.mother.validation.registry.MotherBusinessRuleRegistry;
import com.niyiment.mhis.shared.exception.BusinessRuleViolationException;
import com.niyiment.mhis.shared.exception.ResourceNotFoundException;
import com.niyiment.mhis.validation.engine.BusinessRuleEngine;
import com.niyiment.mhis.validation.rule.ValidationResult;
import com.niyiment.mhis.validation.rule.ValidationResults;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MotherServiceImpl implements MotherService {
  private final MotherRepository motherRepository;
  private final FacilityRepository facilityRepository;
  private final MotherMapper motherMapper;
  private final BusinessRuleEngine businessRuleEngine;
  private final MotherBusinessRuleRegistry ruleRegistry;

  @Override
  @Transactional
  public MotherResponse registerMother(MotherCreateRequest request) {
    log.info("Registering new mother with ANC ID: {}", request.ancUniqueId());
    ValidationResults criticalResults =
        businessRuleEngine.execute(request, ruleRegistry.getCriticalCreationRule(), true);

    if (criticalResults.hasFailure()) {
      ValidationResult firstFailure = criticalResults.getFirstFailure();
      log.warn("Critical business rule validation failed: {}", firstFailure);

      throw new BusinessRuleViolationException(
          firstFailure.getErrorCode(),
          firstFailure.getErrorMessage(),
          criticalResults.getFailures());
    }

    ValidationResults consistencyResults =
        businessRuleEngine.execute(request, ruleRegistry.getConsistencyRules(), false);
    if (consistencyResults.hasFailure()) {
      log.warn(
          "Data consistency validation failed with {} errors",
          consistencyResults.getFailures().size());
      throw new BusinessRuleViolationException(
          "DATA_CONSISTENCY_ERROR",
          "Multiple data consistency errors found",
          consistencyResults.getFailures());
    }

    Mother mother = motherMapper.toEntity(request);
    if (mother.getDateOfBirth() != null) {
      mother.setAgeYears(Period.between(mother.getDateOfBirth(), LocalDate.now()).getYears());
    }

    if (mother.getAncUniqueId() == null) {
      mother.setAncUniqueId(generateNextAncUniqueId(mother.getFacility().getId()));
    }

    mother.setRegistrationDate(LocalDate.now());
    mother.setIsActive(true);

    Mother savedMother = motherRepository.save(mother);
    log.info("Successfully registered mother with ID: {}", savedMother.getId());

    return motherMapper.toResponse(savedMother);
  }

  @Override
  @Transactional
  public MotherResponse updateMother(UUID motherId, MotherCreateRequest request) {
    log.info("Updating mother with ID: {}", motherId);
    Mother existingMother = findEntityById(motherId);

    ValidationResults updateResults =
        businessRuleEngine.execute(request, ruleRegistry.getConsistencyRules(), false);

    if (updateResults.hasFailure()) {
      log.warn("Update validation failed with {} errors", updateResults.getFailures().size());
      throw new BusinessRuleViolationException(
          "UPDATE_VALIDATION_ERROR", "Update validation error found ", updateResults.getFailures());
    }

    Mother motherEntity = motherMapper.toEntity(request);
    updateMotherFields(existingMother, motherEntity);

    if (existingMother.getDateOfBirth() != null) {
      existingMother.setAgeYears(
          Period.between(existingMother.getDateOfBirth(), LocalDate.now()).getYears());
    }

    Mother updatedMother = motherRepository.save(existingMother);
    log.info("Successfully updated mother with ID: {}", motherId);

    return motherMapper.toResponse(updatedMother);
  }

  @Override
  public MotherResponse findById(UUID motherId) {
    Mother mother = findEntityById(motherId);

    return motherMapper.toResponse(mother);
  }

  @Override
  public MotherResponse findByAncUniqueId(String ancUniqueId) {
    Mother mother =
        motherRepository
            .findByAncUniqueId(ancUniqueId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Mother not found with ANC ID: " + ancUniqueId));

    return motherMapper.toResponse(mother);
  }

  @Override
  public MotherResponse findByNationalId(String nationalId) {
    Mother mother =
        motherRepository
            .findByNationalId(nationalId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Mother not found with National ID: " + nationalId));

    return motherMapper.toResponse(mother);
  }

  @Override
  public Page<MotherResponse> findAll(Pageable pageable) {
    Page<Mother> mothers = motherRepository.findAll(pageable);
    return mothers.map(motherMapper::toResponse);
  }

  @Override
  public Page<MotherResponse> findByFacility(UUID facilityId, Pageable pageable) {
    Page<Mother> mothers = motherRepository.findByFacilityFacilityId(facilityId, pageable);
    return mothers.map(motherMapper::toResponse);
  }

  @Override
  public Page<MotherResponse> findByHivStatus(HivStatus hivStatus, Pageable pageable) {
    Page<Mother> mothers = motherRepository.findByHivStatus(hivStatus, pageable);
    return mothers.map(motherMapper::toResponse);
  }

  @Override
  public Page<MotherResponse> findByArtStatus(ArtStatus artStatus, Pageable pageable) {
    Page<Mother> mothers = motherRepository.findByArtStatus(artStatus, pageable);
    return mothers.map(motherMapper::toResponse);
  }

  @Override
  public List<MotherResponse> findHighRiskMothers(UUID facilityId) {
    List<Mother> mothers = motherRepository.findHighRiskMothers(facilityId);

    return mothers.stream().map(motherMapper::toResponse).toList();
  }

  @Override
  public List<MotherResponse> findMothersWithCurrentPregnancies(UUID facilityId) {
    List<Mother> mothers = motherRepository.findMothersWithCurrentPregnancies(facilityId);

    return mothers.stream().map(motherMapper::toResponse).toList();
  }

  @Override
  public List<MotherResponse> findMothersDueForViralLoad(UUID facilityId) {
    LocalDate cutoffDate = LocalDate.now().minus(6, ChronoUnit.MONTHS);
    List<Mother> mothers = motherRepository.findMothersDueForViralLoad(facilityId, cutoffDate);

    return mothers.stream().map(motherMapper::toResponse).toList();
  }

  @Override
  public List<MotherResponse> findMothersWithUnsuppressedVl(UUID facilityId) {
    List<Mother> mothers = motherRepository.findMothersWithUnsuppressedVl(facilityId);

    return mothers.stream().map(motherMapper::toResponse).toList();
  }

  @Override
  public Page<MotherResponse> searchMothers(String searchTerm, Pageable pageable) {
    Page<Mother> mothers = motherRepository.searchMothers(searchTerm, pageable);

    return mothers.map(motherMapper::toResponse);
  }

  @Override
  @Transactional
  public MotherResponse updateHivStatus(UUID motherId, HivStatus hivStatus, LocalDate testDate) {
    log.info("Updating HIV status for mother ID: {} to {}", motherId, hivStatus);

    Mother mother = findEntityById(motherId);
    mother.setHivStatus(hivStatus);
    mother.setHivTestDate(testDate);

    if (hivStatus == HivStatus.NEGATIVE) {
        mother.setArtStatus(null);
        mother.setArtStartDate(null);
        mother.setCurrentArtRegimen(null);
        mother.setLastVlResult(null);
        mother.setLastVlDate(null);
        mother.setVlSuppressed(null);
    }
    Mother updatedMother = motherRepository.save(mother);

    return motherMapper.toResponse(updatedMother);
  }

  @Override
  @Transactional
  public MotherResponse updateArtInformation(
      UUID motherId, ArtStatus artStatus, String regimen, LocalDate startDate) {
    log.info("Updating ART information for mother ID: {}", motherId);
    Mother mother = findEntityById(motherId);

    if (mother.getHivStatus() != HivStatus.POSITIVE) {
      throw new BusinessRuleViolationException(
          "INVALID_ART_UPDATE", "Cannot update ART information for HIV negative mother");
    }
    mother.setArtStatus(artStatus);
    mother.setCurrentArtRegimen(regimen);
    mother.setArtStartDate(startDate);
    Mother updatedMother = motherRepository.save(mother);

    return motherMapper.toResponse(updatedMother);
  }

  @Override
  @Transactional
  public MotherResponse recordViralLoadResult(UUID motherId, Integer vlResult, LocalDate testDate) {
    log.info("Recording viral load result for mother ID: {}", motherId);

    Mother mother = findEntityById(motherId);
    if (mother.getHivStatus() != HivStatus.POSITIVE) {
      throw new BusinessRuleViolationException(
          "INVALID_VL_RECORD", "Cannot record viral load for HIV negative mother");
    }
    mother.setLastVlResult(vlResult);
    mother.setLastVlDate(testDate);
    mother.setVlSuppressed(vlResult < 1000);

    Mother updatedMother = motherRepository.save(mother);

    return motherMapper.toResponse(updatedMother);
  }

  @Override
  public boolean existsByAncUniqueId(String ancUniqueId) {
    return motherRepository.existsByAncUniqueId(ancUniqueId);
  }

  @Override
  public boolean existsByNationalId(String nationalId) {
    return motherRepository.existsByNationalId(nationalId);
  }

  @Override
  public List<MotherResponse> getMothersRequiringFollowUp(UUID facilityId) {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusWeeks(4);
    List<Mother> mothers =
        motherRepository.getMothersRequiringFollowUp(facilityId, startDate, endDate);

    return mothers.stream().map(motherMapper::toResponse).toList();
  }

  @Override
  public String generateNextAncUniqueId(UUID facilityId) {
    Facility facility =
        facilityRepository
            .findById(facilityId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Facility not found with ID: " + facilityId));
    String facilityCode = facility.getFacilityCode();
    Optional<String> maxSequence = motherRepository.findMaxAncSequenceByFacilityCode(facilityCode);
    int nextSequence = maxSequence.map(seq -> Integer.parseInt(seq) + 1).orElse(1);

    return String.format("%s-%06d", facilityCode, nextSequence);
  }

  @Override
  public MotherProfile getMotherProfile(UUID motherId) {
      Mother mother = findEntityById(motherId);

      return MotherProfile.builder()
              .mother(motherMapper.toResponse(mother))
              .pregnancies(PregnancyDto.builder().build())
              .recentVisits(VisitDto.builder().build())
              .riskAssessment(assessRisk(mother))
              .hivCare(getHivCareDetails(mother))
              .partnerDetails(getPartnerDetails(mother))
              .build();
  }

  private Mother findEntityById(UUID id) {
    return motherRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Mother not found with ID: " + id));
  }

  private void updateMotherFields(Mother existingMother, Mother updatedMother) {
    existingMother.setFirstName(updatedMother.getFirstName());
    existingMother.setMiddleName(updatedMother.getMiddleName());
    existingMother.setLastName(updatedMother.getLastName());
    existingMother.setDateOfBirth(updatedMother.getDateOfBirth());
    existingMother.setMaritalStatus(updatedMother.getMaritalStatus());
    existingMother.setEducationLevel(updatedMother.getEducationLevel());
    existingMother.setOccupation(updatedMother.getOccupation());

    existingMother.setPhonePrimary(updatedMother.getPhonePrimary());
    existingMother.setPhoneSecondary(updatedMother.getPhoneSecondary());
    existingMother.setAddressLine1(updatedMother.getAddressLine1());
    existingMother.setAddressLine2(updatedMother.getAddressLine2());
    existingMother.setWard(updatedMother.getWard());
    existingMother.setState(updatedMother.getState());
    existingMother.setLga(updatedMother.getLga());

    existingMother.setNextOfKinName(updatedMother.getNextOfKinName());
    existingMother.setNextOfKinPhone(updatedMother.getNextOfKinPhone());
    existingMother.setNextOfKinRelationship(updatedMother.getNextOfKinRelationship());

    existingMother.setGravidity(updatedMother.getGravidity());
    existingMother.setParity(updatedMother.getParity());
    existingMother.setTermBirths(updatedMother.getTermBirths());
    existingMother.setPretermBirths(updatedMother.getPretermBirths());
    existingMother.setAbortions(updatedMother.getAbortions());
    existingMother.setLivingChildren(updatedMother.getLivingChildren());

    existingMother.setPreviousCs(updatedMother.getPreviousCs());
    existingMother.setPreviousPph(updatedMother.getPreviousPph());
    existingMother.setPreviousEclampsia(updatedMother.getPreviousEclampsia());
    existingMother.setPreviousStillbirth(updatedMother.getPreviousStillbirth());
    existingMother.setChronicConditions(updatedMother.getChronicConditions());
    existingMother.setAllergies(updatedMother.getAllergies());

    existingMother.setLmpDate(updatedMother.getLmpDate());
    existingMother.setEddDate(updatedMother.getEddDate());
    existingMother.setPregnancyConfirmedDate(updatedMother.getPregnancyConfirmedDate());
  }

  private RiskAssessmentDto assessRisk(Mother mother) {
      List<String> riskFactors = new ArrayList<>();

      if (mother.getAgeYears() != null) {
          if (mother.getAgeYears() < 18) {
              riskFactors.add("Teenage pregnancy");
          } else if (mother.getAgeYears() > 35) {
              riskFactors.add("Advanced maternal age");
          }
      }

      // Medical history risks
      if (Boolean.TRUE.equals(mother.getPreviousCs())) {
          riskFactors.add("Previous cesarean section");
      }
      if (Boolean.TRUE.equals(mother.getPreviousPph())) {
          riskFactors.add("Previous postpartum hemorrhage");
      }
      if (Boolean.TRUE.equals(mother.getPreviousEclampsia())) {
          riskFactors.add("Previous eclampsia");
      }
      if (Boolean.TRUE.equals(mother.getPreviousStillbirth())) {
          riskFactors.add("Previous stillbirth");
      }

      if (mother.getHivStatus() == HivStatus.POSITIVE) {
          riskFactors.add("HIV Positive");
          if (Boolean.FALSE.equals(mother.getVlSuppressed())) {
              riskFactors.add("Unsuppressed viral load");
          }
      }
      boolean isHighRisk = !riskFactors.isEmpty();

      return RiskAssessmentDto.builder()
              .isHighRisk(isHighRisk)
              .riskLevel(isHighRisk ? "HIGH" : "LOW")
              .riskFactors(riskFactors)
              .assessmentDate(LocalDate.now())
              .build();
  }

  private HivCareDto getHivCareDetails(Mother mother) {
    return HivCareDto.builder()
            .hivStatus(mother.getHivStatus())
            .hivTestDate(mother.getHivTestDate())
            .artStatus(mother.getArtStatus())
            .artStartDate(mother.getArtStartDate())
            .currentRegimen(mother.getCurrentArtRegimen())
            .lastVlResult(mother.getLastVlResult())
            .lastVlDate(mother.getLastVlDate())
            .vlSuppressed(mother.getVlSuppressed())
            .nextVlDueDate(calculateNextVlDate(mother))
            .build();
  }

  private PartnerDetailsDto getPartnerDetails(Mother mother) {
      return PartnerDetailsDto.builder()
              .hivStatus(mother.getPartnerHivStatus())
              .tested(mother.getPartnerTested())
              .notificationDone(mother.getPartnerNotificationDone())
              .build();
  }

  private LocalDate calculateNextVlDate(Mother mother) {
      if (mother.getHivStatus() != HivStatus.POSITIVE) {
          return null;
      }
      LocalDate lastVlDate = mother.getLastVlDate();
      if (lastVlDate == null) {
          // if no previous VL, due immediately
          return LocalDate.now();
      }

      //VL every 6 months for suppressed, every 3 months for unsuppressed
      int monthsToAdd = Boolean.TRUE.equals(mother.getVlSuppressed()) ? 6 : 3;
      return lastVlDate.plusMonths(monthsToAdd);
  }
}
