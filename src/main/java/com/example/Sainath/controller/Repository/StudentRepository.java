package com.example.Sainath.controller.Repository;

import com.example.Sainath.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
