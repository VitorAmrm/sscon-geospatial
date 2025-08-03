package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;

import java.math.BigDecimal;

public interface SalaryService {
    public String getSalary(Long pessoaId, SalaryOutput output);
    public BigDecimal calculateSalaryIncrease(int yearsInCompany);
    public BigDecimal calculateMininumSalaryQuantity(int yearsInCompany);
}
