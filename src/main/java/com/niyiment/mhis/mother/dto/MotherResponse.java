package com.niyiment.mhis.mother.dto;

import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import com.niyiment.mhis.mother.domain.MaritalStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record MotherResponse(
        UUID id,
        String ancUniqueId,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        UUID facilityId,
        String nationalId,
        MaritalStatus maritalStatus,
        String educationLevel,
        String occupation,
        String phonePrimary,
        String phoneSecondary,
        String addressLine1,
        String addressLine2,
        String ward,
        String state,
        String lga,

        String nextOfKinName,
        String nextOfKinPhone,
        String nextOfKinRelationship,

        Integer gravidity,
        Integer parity,
        Integer termBirths,
        Integer pretermBirths,
        Integer abortions,
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