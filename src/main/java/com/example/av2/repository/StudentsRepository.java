package com.example.av2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.av2.model.Student;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Long>{}