package com.niyiment.mhis.facility.service;


import com.niyiment.mhis.facility.exception.FacilityValidationException;
import com.niyiment.mhis.facility.domain.model.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class FacilityValidationService {
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9]{3,20}$");

    public void validateFacilityCreation(Facility facility) {
        validateBasicFacilityData(facility);
        validateBusinessRules(facility);
    }

    public void validateFacilityUpdate(Facility existingFacility, Facility updatedFacility) {
        validateBasicFacilityData(updatedFacility);
        validateBusinessRules(updatedFacility);
        validateUpdateConstraints(existingFacility, updatedFacility);
    }

    private void validateBasicFacilityData(Facility facility) {
        if (facility.getFacilityCode() == null) {
            throw new FacilityValidationException("Facility code is required");
        }
        if (StringUtils.hasText(facility.getFacilityName())) {
            throw new FacilityValidationException("Facility name is required");
        }
        if (facility.getFacilityName().length() > 255) {
            throw new FacilityValidationException("Facility name cannot exceed 255 characters");
        }
        if (facility.getFacilityType() == null) {
            throw new FacilityValidationException("Facility type is required");
        }
        if (facility.getLevelOfCare() == null) {
            throw new FacilityValidationException("Level of care is required");
        }
        if (StringUtils.hasText(facility.getState())) {
            throw new FacilityValidationException("State is required");
        }
        if (StringUtils.hasText(facility.getLga())) {
            throw new FacilityValidationException("LGA is required");
        }
        validateContactPhone(facility.getContactPhone());
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

    private void validateContactPhone(String contactPhone) {
        if ( StringUtils.hasText(contactPhone)) {
            String cleanPhone = contactPhone.replaceAll("[^0-9+\\-\\s()]", "");
            if (cleanPhone.length() < 10 || cleanPhone.length() > 15) {
                throw new FacilityValidationException("Invalid contact phone number");
            }
        }
    }

    private void validateBusinessRules(Facility facility) {
        if (facility.getLevelOfCare() == LevelOfCare.TERTIARY && facility.getFacilityType() != FacilityType.HOSPITAL) {
            throw new FacilityValidationException("Tertiary level of care is only allowed for Hospital");
        }
        if (facility.getFacilityType() == FacilityType.DISPENSARY && facility.getLevelOfCare() != LevelOfCare.PRIMARY) {
            throw new FacilityValidationException("Dispensaries can only provide primary level of care");
        }
    }

    private void validateUpdateConstraints(Facility existingFacility,Facility updatedFacility) {

        // Business Rule: Cannot change facility if it will violate care level rules
        if (!existingFacility.getFacilityType().equals(updatedFacility.getFacilityType())) {
            validateBusinessRules(updatedFacility);
        }
    }
}
