package com.lpstech.demo.service;

import java.util.List;

import com.lpstech.demo.entity.Student;

public interface StudentService {
	List<Student> getAllStudents();
	
	Student ssveStudent(Student student);

	Student getStudentById(Long id);

	Student updateStudent(Student student);
	
	void deleteStudentById(Long id);
}
