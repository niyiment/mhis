package com.niyiment.mhis.mother.dto;


import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record VisitDto (
    UUID visitId,
    LocalDate visitDate,
    String visitType,
    String clinicianName,
    String chiefComplaint,
    String diagnosis,
    String treatment,
    LocalDate nextVisitDate,
    String notes
){
}
