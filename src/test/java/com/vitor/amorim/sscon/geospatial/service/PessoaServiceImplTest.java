package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.AlreadyExistsException;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class PessoaServiceImplTest {

    private EmbeddedDatabase database;
    private PessoaServiceImpl pessoaService;

    private final Long pessoaId = 1L;

    private final Pessoa pessoaExample = new Pessoa(
            pessoaId,
            "José da Silva",
            LocalDate.of(1990, 1, 1),
            LocalDate.of(2015, 6, 1)
    );

    @BeforeEach
    void setup() {
        database = Mockito.mock(EmbeddedDatabase.class);
        pessoaService = new PessoaServiceImpl(database);
    }

    @Test
    void getOne_existingId_returnsPessoa() {
        when(database.getOne(pessoaId)).thenReturn(Optional.of(pessoaExample));

        Pessoa result = pessoaService.getOne(pessoaId);

        assertEquals(pessoaExample, result);
    }

    @Test
    void getOne_nonExistingId_throwsNotFoundException() {
        when(database.getOne(pessoaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.getOne(pessoaId));
    }

    @Test
    void get_returnsSortedList() {
        Pessoa p1 = new Pessoa(2L, "Ana", LocalDate.of(1985, 5, 10), LocalDate.of(2010, 3, 5));
        Pessoa p2 = new Pessoa(3L, "Bruno", LocalDate.of(1992, 8, 15), LocalDate.of(2018, 7, 20));

        when(database.get()).thenReturn(List.of(p2, p1));

        List<Pessoa> result = pessoaService.get();

        assertEquals(List.of(p1, p2), result);
    }

    @Test
    void create_withExistingId_throwsAlreadyExistsException() {
        when(database.existsById(pessoaExample.getId())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> pessoaService.create(pessoaExample));
    }

    @Test
    void create_newPessoa_returnsCreatedPessoa() {
        when(database.existsById(pessoaExample.getId())).thenReturn(false);
        when(database.create(pessoaExample)).thenReturn(Optional.of(pessoaExample));

        Pessoa result = pessoaService.create(pessoaExample);

        assertEquals(pessoaExample, result);
    }

    @Test
    void update_existingId_returnsUpdatedPessoa() {
        Pessoa updated = new Pessoa(pessoaId, "José Atualizado", LocalDate.of(1990, 1, 1), LocalDate.of(2015, 6, 1));
        when(database.update(eq(pessoaId), any())).thenReturn(Optional.of(updated));

        Pessoa result = pessoaService.update(pessoaId, updated);

        assertEquals(updated, result);
    }

    @Test
    void update_nonExistingId_throwsNotFoundException() {
        when(database.update(eq(pessoaId), any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.update(pessoaId, pessoaExample));
    }

    @Test
    void delete_existingId_returnsDeletedPessoa() {
        when(database.delete(pessoaId)).thenReturn(Optional.of(pessoaExample));

        Pessoa result = pessoaService.delete(pessoaId);

        assertEquals(pessoaExample, result);
    }

    @Test
    void delete_nonExistingId_throwsNotFoundException() {
        when(database.delete(pessoaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.delete(pessoaId));
    }
}