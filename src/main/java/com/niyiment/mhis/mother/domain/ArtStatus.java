package com.niyiment.mhis.mother.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArtStatus {
    NAIVE("Naive"),
    ON_ART("On ART"),
    DEFAULTED("Defaulted"),
    STOPPED("Stopped");

    private final String displayName;
}
