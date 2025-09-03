package com.niyiment.mhis.mother.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HivStatus {
    NEGATIVE("Negative"),
    POSITIVE("Positive"),
    UNKNOWN("Unknown"),
    NOT_DISCLOSED("Not Disclosed");

    private final String displayName;

}
