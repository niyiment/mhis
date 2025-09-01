package com.niyiment.mhis.facility.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelOfCare {
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    TERTIARY("Tertiary");

    private final String displayName;
}
