package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;

public interface SalaryService {
    public String getSalary(Long pessoaId, SalaryOutput output);
    public Long calculateSalaryIncrease(int yearsInCompany);
    public Long calculateMininumSalaryQuantity(int yearsInCompany);
}
