package com.vitor.amorim.sscon.geospatial.domain.outputs;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AgeOutput {
    DAYS,
    MONTHS,
    YEARS;

    @JsonCreator
    public static AgeOutput from(String value) {
        return value == null ? null : AgeOutput.valueOf(value.toUpperCase());
    }
}
