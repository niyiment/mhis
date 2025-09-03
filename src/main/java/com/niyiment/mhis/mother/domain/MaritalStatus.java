package com.niyiment.mhis.mother.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaritalStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed");

    private final String displayName;
}

