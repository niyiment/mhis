package com.niyiment.mhis.mother.dto;

import com.niyiment.mhis.mother.domain.HivStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PartnerDetailsDto (
    HivStatus hivStatus,
    Boolean tested,
    Boolean notificationDone,
    LocalDate notificationDate,
    String notificationMethod,
    Boolean counselingCompleted,
    String partnerName,
    String partnerPhone,
    LocalDate partnerTestDate
){}
