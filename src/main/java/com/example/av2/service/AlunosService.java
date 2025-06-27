package com.example.av2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.av2.model.Student;
import com.example.av2.repository.StudentsRepository;

@Service
public class AlunosService {
    @Autowired
    private StudentsRepository alunoRepository;

    public List<Student> findAll(){
        return alunoRepository.findAll();
    }

    public Student save(Student aluno){
        return alunoRepository.save(aluno);
    }

    public Optional<Student> findById(Long id){
        return alunoRepository.findById(id);
    }

    public Optional<Student> update(Long id, Student updateAluno){
        return alunoRepository.findById(id).map(existingModel ->{
            existingModel.setNome(updateAluno.getNome());
            existingModel.setEmail(updateAluno.getEmail());
            existingModel.setDataNascimento(updateAluno.getDataNascimento());

            return alunoRepository.save(existingModel);
        });
    }

    public ResponseEntity<Void> delete(Long id){
        if(alunoRepository.existsById(id)){
            alunoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
