package com.niyiment.mhis.mother.validation.rule;


import com.niyiment.mhis.facility.repository.FacilityRepository;
import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Validate that the Facility is valid
 */
@Component
@RequiredArgsConstructor
public class ValidFacilityRule implements BusinessRule<MotherCreateRequest> {
    private final FacilityRepository facilityRepository;

    @Override
    public boolean isApplicable(MotherCreateRequest data) {
        return data != null && data.facilityId() != null;
    }

    @Override
    public ValidationResult validate(MotherCreateRequest data) {
        boolean facilityExists = facilityRepository.existsById(data.facilityId());
        if (!facilityExists) {
            return ValidationResult.failure(
                "FACILITY_NOT_FOUND",
                "Facility not found with ID: " + data.facilityId(),
                "facilityId",
                data.facilityId()
            );
        }

        return ValidationResult.success();
    }

    @Override
    public String getRuleName() {
        return "ValidFacilityRule";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
