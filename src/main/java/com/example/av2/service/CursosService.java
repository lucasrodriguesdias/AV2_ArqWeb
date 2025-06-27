package com.example.av2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.av2.model.Cursos;
import com.example.av2.repository.CursosRepository;

@Service
public class CursosService {
    @Autowired
    private CursosRepository cursoRepository;

    public List<Cursos> findAll(){
        return cursoRepository.findAll();
    }

        public Cursos save(Cursos curso){
        return cursoRepository.save(curso);
    }

    public Optional<Cursos> findById(Long id){
        return cursoRepository.findById(id);
    }

    public Optional<Cursos> update(Long id, Cursos updateCurso){
        return cursoRepository.findById(id).map(existingModel ->{
            existingModel.setNome(updateCurso.getNome());
            existingModel.setDescricao(updateCurso.getDescricao());
            existingModel.setCargaHoraria(updateCurso.getCargaHoraria());

            return cursoRepository.save(existingModel);
        });
    }

    public ResponseEntity<Void> delete(Long id){
        if(cursoRepository.existsById(id)){
            cursoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
