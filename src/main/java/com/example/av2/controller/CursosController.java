package com.example.av2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.av2.model.Cursos;
import com.example.av2.service.CursosService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/courses")
@Tag(name = "Cursos", description = "Operações relacionadas à gestão de cursos - acesso via perfil USER")
public class CursosController {

    @Autowired
    private CursosService cursosService;

    @Operation(summary = "Lista todos os cursos disponíveis (requer permissão USER)")
    @GetMapping
    public List<Cursos> getAll() {
        return cursosService.findAll();
    }

    @Operation(summary = "Cadastra um novo curso (requer permissão USER)")
    @PostMapping
    public Cursos create(@RequestBody Cursos novoCurso) {
        return cursosService.save(novoCurso);
    }

    @Operation(summary = "Busca detalhes de um curso pelo ID (requer permissão USER)")
    @GetMapping("/{id}")
    public ResponseEntity<Cursos> getCurso(@PathVariable Long id) {
        return cursosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza as informações de um curso existente (requer permissão USER)")
    @PutMapping("/{id}")
    public ResponseEntity<Cursos> updateCurso(@PathVariable Long id, @RequestBody Cursos curso) {
        return cursosService.update(id, curso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove um curso do sistema (requer permissão USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return cursosService.delete(id);
    }
}
