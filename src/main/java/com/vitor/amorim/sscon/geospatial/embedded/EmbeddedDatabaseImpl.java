package com.vitor.amorim.sscon.geospatial.embedded;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.dto.PessoaDTO;
import com.vitor.amorim.sscon.geospatial.domain.mapper.PessoaMapper;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmbeddedDatabaseImpl implements EmbeddedDatabase {

    private final Map<Long, Pessoa> database = new LinkedHashMap<>();
    private final Collator ptBrCollator = Collator.getInstance( Locale.of("pt", "BR"));

    public EmbeddedDatabaseImpl() {
        this.ptBrCollator.setStrength(Collator.PRIMARY);
    }


    @Override
    public List<Pessoa> get() {
        return this.database
                .values()
                    .stream()
                        .sorted(Comparator.comparing(Pessoa::getNome, ptBrCollator))
                          .toList();
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
        return  this.database.keySet()
                .stream()
                .filter(Objects::nonNull)
                .max(Long::compare)
                .map(id -> id + 1)
                .orElse(1L);
    }

    @Override
    public boolean existsById(Long id) {
        return Objects.nonNull(id) && this.database.containsKey(id);
    }
}
