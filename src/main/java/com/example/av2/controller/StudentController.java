package com.example.av2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.av2.model.Student;
import com.example.av2.service.AlunosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/students")
@Tag(name = "Alunos", description = "Endpoints de gestão de alunos - acesso restrito a administradores")
public class StudentController {

    @Autowired
    private AlunosService alunosService;

    @Operation(summary = "Retorna todos os alunos cadastrados (somente para ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Student> getAll() {
        return alunosService.findAll();
    }

    @Operation(summary = "Cadastra um novo aluno (somente para ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Student create(@RequestBody Student aluno) {
        return alunosService.save(aluno);
    }

    @Operation(summary = "Consulta aluno pelo ID (somente para ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getAluno(@PathVariable Long id) {
        return alunosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza os dados de um aluno existente (somente para ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateAluno(@PathVariable Long id, @RequestBody Student aluno) {
        return alunosService.update(id, aluno)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Exclui um aluno do sistema (somente para ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return alunosService.delete(id);
    }
}
