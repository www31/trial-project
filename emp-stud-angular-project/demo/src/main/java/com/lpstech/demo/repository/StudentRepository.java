package com.lpstech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lpstech.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
}
