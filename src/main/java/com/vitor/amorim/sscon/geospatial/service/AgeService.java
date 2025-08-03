package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.outputs.AgeOutput;

public interface AgeService {
    public Long getAge(Long pessoaId, AgeOutput output);
}
