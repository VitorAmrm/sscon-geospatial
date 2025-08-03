package com.vitor.amorim.sscon.geospatial.embedded;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.dto.PessoaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmbeddedDatabaseImplTest {

    private EmbeddedDatabaseImpl embeddedDatabase;

    @BeforeEach
    void setup() {
        embeddedDatabase = new EmbeddedDatabaseImpl();
    }

    private Pessoa createPessoa(Long id, String nome) {
        return new Pessoa(id, nome, LocalDate.of(1990,1,1), LocalDate.of(2015,1,1));
    }

    @Test
    void create_shouldAssignIdIfNull() {
        Pessoa p = new Pessoa(null, "João", LocalDate.of(1990,1,1), LocalDate.of(2015,1,1));
        Optional<Pessoa> created = embeddedDatabase.create(p);
        assertTrue(created.isPresent());
        assertNotNull(created.get().getId());
        assertEquals("João", created.get().getNome());
        assertEquals(1L, created.get().getId());
    }

    @Test
    void create_shouldUseExistingIdIfProvidedAndNotExists() {
        Pessoa p = createPessoa(10L, "Maria");
        Optional<Pessoa> created = embeddedDatabase.create(p);
        assertTrue(created.isPresent());
        assertEquals(10L, created.get().getId());
    }

    @Test
    void create_shouldOverwriteIfSameId() {
        Pessoa p1 = createPessoa(5L, "Ana");
        Pessoa p2 = createPessoa(5L, "Ana Updated");

        embeddedDatabase.create(p1);
        Optional<Pessoa> created2 = embeddedDatabase.create(p2);

        assertTrue(created2.isPresent());
        assertEquals("Ana Updated", created2.get().getNome());
        assertEquals(5L, created2.get().getId());
    }

    @Test
    void get_shouldReturnAll() {
        embeddedDatabase.create(createPessoa(1L, "A"));
        embeddedDatabase.create(createPessoa(2L, "B"));

        List<Pessoa> all = embeddedDatabase.get();
        assertEquals(2, all.size());
    }

    @Test
    void getOne_shouldReturnPessoaIfExists() {
        Pessoa p = createPessoa(3L, "Carlos");
        embeddedDatabase.create(p);

        Optional<Pessoa> result = embeddedDatabase.getOne(3L);
        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getNome());
    }

    @Test
    void getOne_shouldReturnEmptyIfNotExists() {
        Optional<Pessoa> result = embeddedDatabase.getOne(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void update_shouldModifyExistingPessoa() {
        Pessoa p = createPessoa(4L, "Diana");
        embeddedDatabase.create(p);

        PessoaDTO dto = new PessoaDTO("Diana Updated", p.getDataDeNascimento(), p.getDataDeAdmissao());

        Optional<Pessoa> updated = embeddedDatabase.update(4L, dto);
        assertTrue(updated.isPresent());
        assertEquals("Diana Updated", updated.get().getNome());
    }

    @Test
    void update_shouldReturnEmptyIfNotExists() {
        PessoaDTO dto = new PessoaDTO("No One", LocalDate.of(2000,1,1), LocalDate.of(2020,1,1));

        Optional<Pessoa> updated = embeddedDatabase.update(999L, dto);
        assertTrue(updated.isEmpty());
    }

    @Test
    void delete_shouldRemovePessoaByObject() {
        Pessoa p = createPessoa(6L, "Elias");
        embeddedDatabase.create(p);

        Optional<Pessoa> deleted = embeddedDatabase.delete(p);
        assertTrue(deleted.isPresent());
        assertEquals(6L, deleted.get().getId());
        assertTrue(embeddedDatabase.getOne(6L).isEmpty());
    }

    @Test
    void delete_shouldReturnEmptyWhenDeletingNullPessoa() {
        Optional<Pessoa> deleted = embeddedDatabase.delete((Pessoa)null);
        assertTrue(deleted.isEmpty());
    }

    @Test
    void delete_shouldRemovePessoaById() {
        Pessoa p = createPessoa(7L, "Fábio");
        embeddedDatabase.create(p);

        Optional<Pessoa> deleted = embeddedDatabase.delete(7L);
        assertTrue(deleted.isPresent());
        assertTrue(embeddedDatabase.getOne(7L).isEmpty());
    }

    @Test
    void delete_shouldReturnEmptyIfIdNotExists() {
        Optional<Pessoa> deleted = embeddedDatabase.delete(999L);
        assertTrue(deleted.isEmpty());
    }

    @Test
    void getSequence_shouldIncrementProperly() {
        assertEquals(1L, embeddedDatabase.getSequence());

        embeddedDatabase.create(createPessoa(null, "Gustavo"));
        assertEquals(2L, embeddedDatabase.getSequence());

        embeddedDatabase.create(createPessoa(null, "Helena"));
        assertEquals(3L, embeddedDatabase.getSequence());
    }

    @Test
    void existsById_shouldReturnTrueIfExists() {
        Pessoa p = createPessoa(8L, "Isabela");
        embeddedDatabase.create(p);

        assertTrue(embeddedDatabase.existsById(8L));
    }

    @Test
    void existsById_shouldReturnFalseIfNotExists() {
        assertFalse(embeddedDatabase.existsById(999L));
        assertFalse(embeddedDatabase.existsById(null));
    }
}
