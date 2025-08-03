package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.springframework.stereotype.Service;

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
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));


    public SalaryServiceImpl(EmbeddedDatabase database) {
        this.database = database;
        CURRENCY_FORMATTER.setMaximumFractionDigits(2);
        CURRENCY_FORMATTER.setMinimumFractionDigits(2);
    }

    @Override
    public String getSalary(Long pessoaId, SalaryOutput output) {
        Pessoa currPerson = this.database.getOne(pessoaId).orElseThrow(() -> new NotFoundException("Pessoa",pessoaId));
        int yearsInCompany = Period.between(currPerson.getDataDeAdmissao(), LocalDate.now()).getYears();
        long salaryInLong;
        if (SalaryOutput.MIN.equals(output)) {
            salaryInLong = this.calculateMininumSalaryQuantity(yearsInCompany) * BASE_MIN_SALARY;
        } else {
            salaryInLong = this.calculateSalaryIncrease(yearsInCompany);
        }
        return CURRENCY_FORMATTER.format(salaryInLong);
    }

    @Override
    public Long calculateSalaryIncrease(int yearsInCompany) {
        long finalSalary = BASE_COMPANY_SALARY;
        for (int i = 0; i < yearsInCompany; i++) {
            finalSalary += (long)(finalSalary * (INCREASE_PERCENTAGE / 100.0));
            finalSalary += BONUS_PER_YEAR;
        }
        return finalSalary;
    }

    @Override
    public Long calculateMininumSalaryQuantity(int yearsInCompany) {
        Long fullSalary = this.calculateSalaryIncrease(yearsInCompany);
        return fullSalary / BASE_MIN_SALARY;
    }
}
