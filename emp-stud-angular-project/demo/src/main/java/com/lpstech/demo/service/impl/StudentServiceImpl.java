package com.lpstech.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lpstech.demo.entity.Student;
import com.lpstech.demo.repository.StudentRepository;
import com.lpstech.demo.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;
	
	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentRepository.findAll();
	}

	@Override
	public Student ssveStudent(Student student) {
		// TODO Auto-generated method stub
		return studentRepository.save(student);
	}
	
	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudentById(Long id) {
		studentRepository.deleteById(id);
	}
}
