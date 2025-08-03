package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.mapper.PessoaMapper;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.AlreadyExistsException;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final EmbeddedDatabase database;
    private static final String SCOPE = "Pessoa";

    public PessoaServiceImpl(EmbeddedDatabase database) {
        this.database = database;
    }

    @Override
    public Pessoa getOne(Long id) {
        return database.getOne(id).orElseThrow(() -> new NotFoundException(SCOPE,id));
    }

    @Override
    public List<Pessoa> get() {
        return List.of();
    }

    @Override
    public Pessoa update(Long id, Pessoa pessoa) {
        return database.update(id, PessoaMapper.INSTANCE.entityToDto(pessoa))
                            .orElseThrow(() -> new NotFoundException(SCOPE,id));
    }

    @Override
    public Pessoa create(Pessoa pessoa) {
        if(Objects.nonNull(pessoa) && this.database.existsById(pessoa.getId()))
            throw new AlreadyExistsException(SCOPE, pessoa.getId());

        return this.database.create(pessoa).orElse(null);
    }

    @Override
    public Pessoa delete(Long id) {
        return this.database.delete(id).orElse(null);
    }

}
