package com.vitor.amorim.sscon.geospatial.service;

import com.vitor.amorim.sscon.geospatial.domain.outputs.AgeOutput;
import com.vitor.amorim.sscon.geospatial.domain.Pessoa;

import java.util.List;

public interface PessoaService {
    public Pessoa getOne(Long id);
    public List<Pessoa> get();
    public Pessoa update(Long id, Pessoa pessoa);
    public Pessoa create(Pessoa pessoa);
    public Pessoa delete(Long id);
}
