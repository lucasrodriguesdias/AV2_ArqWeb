package com.example.av2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.av2.controller.CursosController;
import com.example.av2.model.Cursos;
import com.example.av2.service.CursosService;

@SpringBootTest
public class CursosTest {

    @InjectMocks
    private CursosController cursosController;

    @Mock
    private CursosService cursosService;

    List<Cursos> cursosList = new ArrayList<>();

    @Test
    @BeforeEach
    void test_insert() {
        Cursos novoCurso = new Cursos(
                null,
                "CursoAlpha",
                "Curso introdutório para iniciantes",
                10);

        when(cursosService.save(novoCurso)).thenReturn(novoCurso);

        Cursos resultado = cursosController.create(novoCurso);

        assertEquals(novoCurso, resultado);

        cursosList.add(novoCurso);
    }

    @Test
    void test_getAll() {
        when(cursosService.findAll()).thenReturn(cursosList);

        List<Cursos> response = cursosController.getAll();

        assertEquals(1, response.size());
        verify(cursosService, times(1)).findAll();
    }

    @Test
    void test_update() {
        Cursos cursoExistente = cursosList.get(0);
        Long cursoId = cursoExistente.getId();

        Cursos cursoAtualizado = new Cursos(
                null,
                "CursoAlpha",
                "Curso introdutório para iniciantes",
                10);

        when(cursosService.findById(cursoId)).thenReturn(Optional.of(cursoExistente));
        when(cursosService.update(cursoId, cursoAtualizado)).thenReturn(Optional.of(cursoAtualizado));

        ResponseEntity<Cursos> resposta = cursosController.updateCurso(cursoId, cursoAtualizado);

        assertEquals(ResponseEntity.ok(cursoAtualizado), resposta);
        verify(cursosService, times(1)).update(cursoId, cursoAtualizado);
    }

    @Test
    void test_delete() {
        Cursos curso = cursosList.get(0);
        Long id = curso.getId();

        when(cursosService.delete(id)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> resposta = cursosController.delete(id);

        assertEquals(ResponseEntity.noContent().build(), resposta);
        verify(cursosService, times(1)).delete(id);
    }
}
