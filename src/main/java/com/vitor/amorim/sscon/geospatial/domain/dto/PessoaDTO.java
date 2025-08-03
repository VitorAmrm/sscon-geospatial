package com.vitor.amorim.sscon.geospatial.domain.dto;

import java.time.LocalDate;

public record PessoaDTO(
        Long id,
        String nome,
        LocalDate dataDeNascimento,
        LocalDate dataDeAdmissao
) {}