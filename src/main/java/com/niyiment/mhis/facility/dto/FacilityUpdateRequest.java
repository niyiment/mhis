package com.niyiment.mhis.facility.dto;

import com.niyiment.mhis.facility.constants.FacilityValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FacilityUpdateRequest(

        @NotBlank(message = FacilityValidationMessage.FACILITY_NAME_REQUIRED)
        @Size(max=255, message = FacilityValidationMessage.FACILITY_NAME_MAX_LENGTH)
        String facilityName,

        @NotBlank(message = FacilityValidationMessage.FACILITY_TYPE_REQUIRED)
        String facilityType,

        @NotBlank(message = FacilityValidationMessage.LEVEL_OF_CARE_REQUIRED)
        String levelOfCare,

        @NotBlank(message = FacilityValidationMessage.STATE_REQUIRED)
        @Size(max=100, message = FacilityValidationMessage.STATE_MAX_LENGTH)
        String state,

        @NotBlank(message = FacilityValidationMessage.LGA_REQUIRED)
        @Size(max=100, message = FacilityValidationMessage.LGA_MAX_LENGTH)
        String lga,

        @Pattern(regexp = "^[+]?[0-9\\s-()]+$", message = FacilityValidationMessage.CONTACT_PHONE_PATTERN)
        @Size(max=20, message = FacilityValidationMessage.CONTACT_PHONE_MAX_LENGTH)
        String contactPhone,

        @Size(max=50, message = FacilityValidationMessage.DHIS2_ID_MAX_LENGTH)
        String dhis2OrgUnitId
){}
