package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.embedded.EmbeddedDatabase;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PopulateServiceImpl implements PopulateService {

    private final EmbeddedDatabase database;

    public PopulateServiceImpl(EmbeddedDatabase database) {
        this.database = database;
    }

    @PostConstruct
    public void setUp () {
        this.populate();
    }

    @Override
    public void populate() {
        database.create(new Pessoa(1L,"José Maria da Silva",LocalDate.of(1999,7,14),LocalDate.of(2017,2,9)));
        database.create(new Pessoa(2L,"Maria José da Silva",LocalDate.of(1985,2,23),LocalDate.of(1999,12,11)));
        database.create(new Pessoa(3L,"Silva da José Maria", LocalDate.of(1995,2,5),LocalDate.of(2011,6,1)));
    }
}
