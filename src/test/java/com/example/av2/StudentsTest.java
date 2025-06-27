package com.example.av2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.av2.controller.StudentController;
import com.example.av2.model.Student;
import com.example.av2.model.Cursos;
import com.example.av2.service.AlunosService;

@SpringBootTest
public class StudentsTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private AlunosService alunosService;

    List<Student> alunosList = new ArrayList<>();

    @Test
    @BeforeEach
    void test_insert() {
        Student newStudent = new Student(
                null,
                "StudentA",
                "studentA@email.com",
                Date.valueOf("2003-07-14"),
                new Cursos(1L, null, null, null)
        );
        when(alunosService.save(newStudent)).thenReturn(newStudent);

        Student result = studentController.create(newStudent);

        assertEquals(newStudent, result);

        alunosList.add(newStudent);
    }

    @Test
    void test_getAll() {
        when(alunosService.findAll()).thenReturn(alunosList);

        List<Student> response = studentController.getAll();

        assertEquals(1, response.size());
        verify(alunosService, times(1)).findAll();
    }

    @Test
    void test_update() {
        Student existingStudent = alunosList.get(0);
        Long studentId = existingStudent.getId();

        Student updatedStudent = new Student(
                studentId,
                "StudentA",
                "studentA@email.com",
                Date.valueOf("2003-07-14"),
                new Cursos(1L, null, null, null)
        );

        when(alunosService.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(alunosService.update(studentId, updatedStudent)).thenReturn(Optional.of(updatedStudent));

        ResponseEntity<Student> response = studentController.updateAluno(studentId, updatedStudent);

        assertEquals(ResponseEntity.ok(updatedStudent), response);
        verify(alunosService, times(1)).update(studentId, updatedStudent);
    }

    @Test
    void test_delete() {
        Student student = alunosList.get(0);
        Long id = student.getId();

        when(alunosService.delete(id)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = studentController.delete(id);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(alunosService, times(1)).delete(id);
    }
}
