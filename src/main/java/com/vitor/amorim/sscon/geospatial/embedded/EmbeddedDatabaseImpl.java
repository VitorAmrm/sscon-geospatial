package com.vitor.amorim.sscon.geospatial.embedded;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.dto.PessoaDTO;
import com.vitor.amorim.sscon.geospatial.domain.mapper.PessoaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmbeddedDatabaseImpl implements EmbeddedDatabase {

    private final ConcurrentHashMap<Long, Pessoa> database = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Pessoa> get() {
        return this.database.values().stream().toList();
    }

    @Override
    public Optional<Pessoa> getOne(Long id) {
        return Optional.ofNullable(this.database.get(id));
    }

    @Override
    public Optional<Pessoa> update(Long id, PessoaDTO pessoa) {
        return getOne(id).map(pessoaExistente -> {
            PessoaMapper.INSTANCE.updateEntityFromDto(pessoa, pessoaExistente);
            database.put(id, pessoaExistente);
            return pessoaExistente;
        });
    }

    @Override
    public Optional<Pessoa> create(Pessoa pessoa) {
        if (Objects.isNull(pessoa.getId()))
            pessoa.setId(getSequence());
        else
            sequence.updateAndGet(current -> Math.max(current, pessoa.getId()));

        this.database.put(pessoa.getId(), pessoa);

        return Optional.of(pessoa);
    }

    @Override
    public Optional<Pessoa> delete(Pessoa pessoa) {
        if(Objects.isNull(pessoa))
            return Optional.empty();
        return delete(pessoa.getId());
    }

    @Override
    public Optional<Pessoa> delete(Long id) {
        return Optional.ofNullable(this.database.remove(id));
    }

    @Override
    public Long getSequence() {
        return  sequence.incrementAndGet();
    }

    @Override
    public boolean existsById(Long id) {
        return Objects.nonNull(id) && this.database.containsKey(id);
    }
}
