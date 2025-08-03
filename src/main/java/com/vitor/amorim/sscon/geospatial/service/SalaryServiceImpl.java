package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final EmbeddedDatabase database;
    private static final int BASE_MIN_SALARY = 1302;
    private static final int BASE_COMPANY_SALARY = 1558;
    private static final int INCREASE_PERCENTAGE = 18;
    private static final int BONUS_PER_YEAR = 500;
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));


    public SalaryServiceImpl(EmbeddedDatabase database) {
        this.database = database;
        CURRENCY_FORMATTER.setMaximumFractionDigits(2);
        CURRENCY_FORMATTER.setMinimumFractionDigits(2);
    }

    @Override
    public String getSalary(Long pessoaId, SalaryOutput output) {
        Pessoa currPerson = this.database.getOne(pessoaId).orElseThrow(() -> new NotFoundException("Pessoa",pessoaId));
        int yearsInCompany = Period.between(currPerson.getDataDeAdmissao(), LocalDate.now()).getYears();
        BigDecimal salary;
        if (SalaryOutput.MIN.equals(output)) {
            salary = this.calculateMininumSalaryQuantity(yearsInCompany);
        } else {
            salary = this.calculateSalaryIncrease(yearsInCompany);
        }
        return CURRENCY_FORMATTER.format(salary);
    }

    @Override
    public BigDecimal calculateSalaryIncrease(int yearsInCompany) {
        BigDecimal finalSalary = BigDecimal.valueOf(BASE_COMPANY_SALARY);
        BigDecimal percentage = BigDecimal.valueOf(INCREASE_PERCENTAGE)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        for (int i = 0; i < yearsInCompany; i++) {
            finalSalary = finalSalary.add(finalSalary.multiply(percentage));
            finalSalary = finalSalary.add(BigDecimal.valueOf(BONUS_PER_YEAR));
        }

        return finalSalary.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateMininumSalaryQuantity(int yearsInCompany) {
        BigDecimal fullSalary = this.calculateSalaryIncrease(yearsInCompany);
        BigDecimal baseSalary = BigDecimal.valueOf(BASE_MIN_SALARY);

        return fullSalary.divide(baseSalary, 2, RoundingMode.HALF_UP);
    }

}
