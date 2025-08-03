package com.vitor.amorim.sscon.geospatial.domain.outputs;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SalaryOutput {
    MIN,
    FULL;

    @JsonCreator
    public static SalaryOutput from(String value) {
        return value == null ? null : SalaryOutput.valueOf(value.toUpperCase());
    }
}
