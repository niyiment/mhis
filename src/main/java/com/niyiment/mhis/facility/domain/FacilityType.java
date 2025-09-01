package com.niyiment.mhis.facility.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FacilityType {
    HOSPITAL("Hospital"),
    HEALTH_CENTRE("Health Centre"),
    DISPENSARY("Dispensary");

    private final String displayName;
}
