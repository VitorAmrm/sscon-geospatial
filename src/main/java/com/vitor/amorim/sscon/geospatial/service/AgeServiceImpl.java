package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.outputs.AgeOutput;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import com.vitor.amorim.sscon.geospatial.exception.AgeOutputInvalidException;
import com.vitor.amorim.sscon.geospatial.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
public class AgeServiceImpl implements AgeService {

    private final EmbeddedDatabase database;

    public AgeServiceImpl(EmbeddedDatabase database) {
        this.database = database;
    }

    @Override
    public Long getAge(Long id, AgeOutput output) {
        if(Objects.isNull(output))
            throw new AgeOutputInvalidException();

        return this.database.getOne(id)
                .stream()
                .mapToLong(pessoa -> getPessoaAge(pessoa, output))
                .findFirst()
                .orElseThrow( () -> new NotFoundException("Pessoa",id));
    }

    private Long getPessoaAge(Pessoa pessoa , AgeOutput output) {
        LocalDate today = LocalDate.now();
        return switch (output) {
            case DAYS -> ChronoUnit.DAYS.between(pessoa.getDataDeNascimento(), today);
            case MONTHS -> ChronoUnit.MONTHS.between(pessoa.getDataDeNascimento(), today);
            case YEARS -> (long) Period.between(pessoa.getDataDeNascimento(), today).getYears();
        };
    }
}
