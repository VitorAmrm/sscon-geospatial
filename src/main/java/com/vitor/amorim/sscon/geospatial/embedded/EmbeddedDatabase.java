package com.vitor.amorim.sscon.geospatial.embedded;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.dto.PessoaDTO;

import java.util.List;
import java.util.Optional;

public interface EmbeddedDatabase {
    public List<Pessoa> get();
    public Optional<Pessoa> getOne(Long id);
    public Optional<Pessoa> update(Long id, PessoaDTO pessoa);
    public Optional<Pessoa> create(Pessoa pessoa);
    public Optional<Pessoa> delete(Pessoa pessoa);
    public Optional<Pessoa> delete(Long id);
    public Long getSequence();
    public boolean existsById(Long id);
}
