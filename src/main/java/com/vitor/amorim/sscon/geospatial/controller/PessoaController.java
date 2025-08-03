package com.vitor.amorim.sscon.geospatial.controller;

import com.vitor.amorim.sscon.geospatial.domain.outputs.AgeOutput;
import com.vitor.amorim.sscon.geospatial.domain.Pessoa;
import com.vitor.amorim.sscon.geospatial.domain.dto.PessoaDTO;
import com.vitor.amorim.sscon.geospatial.domain.mapper.PessoaMapper;
import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;
import com.vitor.amorim.sscon.geospatial.service.AgeService;
import com.vitor.amorim.sscon.geospatial.service.PessoaService;
import com.vitor.amorim.sscon.geospatial.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PessoaController {

    private final PessoaService pessoaService;
    private final SalaryService salaryService;
    private final AgeService ageService;

    public PessoaController(PessoaService pessoaService, SalaryService salaryService, AgeService ageService) {
        this.pessoaService = pessoaService;
        this.salaryService = salaryService;
        this.ageService = ageService;
    }
    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll() {
        return ResponseEntity.ok(this.pessoaService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getOne(@PathVariable Long id) {
        Pessoa pessoa = this.pessoaService.getOne(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/{id}/age")
    public ResponseEntity<Long> getAge(@PathVariable Long id, @RequestParam(defaultValue = "YEARS") AgeOutput output) {
        return ResponseEntity.ok(this.ageService.getAge(id, output));
    }

    @GetMapping("/{id}/salary")
    public ResponseEntity<String> getSalary(@PathVariable Long id, @RequestParam(defaultValue = "FULL") SalaryOutput output) {
        return ResponseEntity.ok(this.salaryService.getSalary(id, output));
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody Pessoa pessoa) {
        Pessoa created = this.pessoaService.create(pessoa);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Pessoa updated = pessoaService.update(id, pessoa);
        return ResponseEntity.ok(PessoaMapper.INSTANCE.entityToDto(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaDTO> delete(@PathVariable Long id) {
        Pessoa deleted = pessoaService.delete(id);
        return ResponseEntity.ok(PessoaMapper.INSTANCE.entityToDto(deleted));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePatch(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Pessoa updated = pessoaService.update(id, pessoa);
        return ResponseEntity.ok(PessoaMapper.INSTANCE.entityToDto(updated));
    }
}