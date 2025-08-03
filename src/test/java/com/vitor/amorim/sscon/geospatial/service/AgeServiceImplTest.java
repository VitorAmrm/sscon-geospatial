package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.outputs.AgeOutput;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.AgeOutputInvalidException;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AgeServiceImplTest {
    private EmbeddedDatabase database;
    private AgeServiceImpl ageService;

    private final Long pessoaId = 1L;

    @BeforeEach
    void setup() {
        database = Mockito.mock(EmbeddedDatabase.class);
        ageService = new AgeServiceImpl(database);
    }

    @Test
    void testGetAgeInYears() {
        Pessoa pessoa = new Pessoa(
                pessoaId,
                "João Silva",
                LocalDate.now().minusYears(30),
                LocalDate.now().minusYears(5)
        );
        when(database.getOne(pessoaId)).thenReturn(Optional.of(pessoa));

        Long age = ageService.getAge(pessoaId, AgeOutput.YEARS);

        assertEquals(30L, age);
    }

    @Test
    void testGetAgeInMonths() {
        Pessoa pessoa = new Pessoa(
                pessoaId,
                "Maria Souza",
                LocalDate.now().minusMonths(18),
                LocalDate.now().minusYears(3)
        );
        when(database.getOne(pessoaId)).thenReturn(Optional.of(pessoa));

        Long age = ageService.getAge(pessoaId, AgeOutput.MONTHS);

        assertEquals(18L, age);
    }

    @Test
    void testGetAgeInDays() {
        Pessoa pessoa = new Pessoa(
                pessoaId,
                "Carlos Pereira",
                LocalDate.now().minusDays(100),
                LocalDate.now().minusYears(1)
        );
        when(database.getOne(pessoaId)).thenReturn(Optional.of(pessoa));

        Long age = ageService.getAge(pessoaId, AgeOutput.DAYS);

        assertEquals(100L, age);
    }

    @Test
    void testGetAgeThrowsAgeOutputInvalidExceptionWhenOutputIsNull() {
        assertThrows(AgeOutputInvalidException.class, () -> ageService.getAge(pessoaId, null));
    }

    @Test
    void testGetAgeThrowsNotFoundExceptionWhenPessoaNotFound() {
        when(database.getOne(pessoaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ageService.getAge(pessoaId, AgeOutput.YEARS));
    }
}
