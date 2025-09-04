package com.niyiment.mhis.mother.dto;

import com.niyiment.mhis.mother.constants.MotherValidationMessage;
import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import com.niyiment.mhis.mother.domain.MaritalStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record MotherCreateRequest(
        @NotBlank(message = MotherValidationMessage.ANC_UNIQUE_ID_REQUIRED)
        String ancUniqueId,
        @NotBlank(message = MotherValidationMessage.FIRST_NAME_REQUIRED)
        @Size(max = 100, message = MotherValidationMessage.FIRST_NAME_MAX_LENGTH)
        String firstName,
        @Size(max = 100, message = MotherValidationMessage.MIDDLE_NAME_MAX_LENGTH)
        String middleName,
        @NotBlank(message = MotherValidationMessage.LAST_NAME_REQUIRED)
        @Size(max = 100, message = MotherValidationMessage.LAST_NAME_MAX_LENGTH)
        String lastName,
        @NotBlank(message = MotherValidationMessage.DATE_OF_BIRTH_REQUIRED)
        @Past(message = MotherValidationMessage.DATE_OF_BIRTH_PAST)
        LocalDate dateOfBirth,
        @NotBlank(message = MotherValidationMessage.FACILITY_REQUIRED)
        UUID facilityId,
        @Size(max = 50, message = MotherValidationMessage.NATIONAL_ID_MAX_LENGTH)
        String nationalId,
        MaritalStatus maritalStatus,
        @Size(max = 50, message = MotherValidationMessage.EDUCATION_LEVEL_MAX_LENGTH)
        String educationLevel,
        @Size(max = 100, message = MotherValidationMessage.OCCUPATION_MAX_LENGTH)
        String occupation,
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = MotherValidationMessage.PHONE_PRIMARY_INVALID)
        String phonePrimary,
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = MotherValidationMessage.PHONE_SECONDARY_INVALID)
        String phoneSecondary,
        String addressLine1,
        String addressLine2,
        String ward,
        String state,
        String lga,

        String nextOfKinName,
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = MotherValidationMessage.PHONE_SECONDARY_INVALID)
        String nextOfKinPhone,
        String nextOfKinRelationship,

        @Min(value = 0, message = MotherValidationMessage.GRAVIDITY_INVALID)
        Integer gravidity,
        @Min(value = 0, message = MotherValidationMessage.PARITY_INVALID)
        Integer parity,
        @Min(value = 0, message = MotherValidationMessage.TERM_BIRTHS_INVALID)
        Integer termBirths,
        @Min(value = 0, message = MotherValidationMessage.PRETERM_BIRTHS_INVALID)
        Integer pretermBirths,
        @Min(value = 0, message = MotherValidationMessage.ABORTIONS_INVALID)
        Integer abortions,
        @Min(value = 0, message = MotherValidationMessage.LIVING_CHILDREN_INVALID)
        Integer livingChildren,

        Boolean previousCs,
        Boolean previousPph,
        Boolean previousEclampsia,
        Boolean previousStillbirth,

        List<String> chronicConditions,
        String allergies,

        LocalDate lmpDate,
        LocalDate eddDate,
        LocalDate pregnancyConfirmedDate,

        HivStatus hivStatus,
        LocalDate hivTestDate,
        LocalDate artStartDate,
        ArtStatus artStatus,
        String currentArtRegimen,
        Integer lastVlResult,
        LocalDate lastVlDate,
        Boolean vlSuppressed,

        HivStatus partnerHivStatus,
        Boolean partnerTested,
        Boolean partnerNotificationDone
) {}


