package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SalaryServiceImplTest {

    private EmbeddedDatabase database;
    private SalaryServiceImpl salaryService;

    private final Long pessoaId = 1L;

    private final Pessoa pessoaExample = new Pessoa(
            pessoaId,
            "Maria Silva",
            LocalDate.of(1990, 1, 1),
            LocalDate.of(2015, 6, 1)
    );

    @BeforeEach
    void setup() {
        database = Mockito.mock(EmbeddedDatabase.class);
        salaryService = new SalaryServiceImpl(database);
    }

    @Test
    void getSalary_withFullOutput_returnsFormattedSalary() {
        when(database.getOne(pessoaId)).thenReturn(Optional.of(pessoaExample));

        String salaryStr = salaryService.getSalary(pessoaId, SalaryOutput.FULL);

        assertNotNull(salaryStr);
        assertTrue(salaryStr.matches("\\d{1,3}(\\.\\d{3})*,\\d{2}"));
    }

    @Test
    void getSalary_withMinOutput_returnsFormattedMinimumSalaryQuantity() {
        when(database.getOne(pessoaId)).thenReturn(Optional.of(pessoaExample));

        String minSalaryStr = salaryService.getSalary(pessoaId, SalaryOutput.MIN);

        assertNotNull(minSalaryStr);
        assertTrue(minSalaryStr.matches("\\d{1,3}(\\.\\d{3})*,\\d{2}"));
    }

    @Test
    void getSalary_nonExistingPessoa_throwsNotFoundException() {
        when(database.getOne(pessoaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> salaryService.getSalary(pessoaId, SalaryOutput.FULL));
    }

    @Test
    void calculateSalaryIncrease_correctCalculation() {
        // Act
        BigDecimal salary = salaryService.calculateSalaryIncrease(3);

        BigDecimal expected = new BigDecimal("4346.04").setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, salary.compareTo(expected),
                "O salário após 3 anos deve ser exatamente 4.346,04");
    }

    @Test
    void calculateMininumSalaryQuantity_correctCalculation() {
        BigDecimal minQuantity = salaryService.calculateMininumSalaryQuantity(3);

        BigDecimal expected = new BigDecimal("3.34");

        assertEquals(0, minQuantity.compareTo(expected));
    }
}
