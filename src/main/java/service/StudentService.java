package service;

import java.util.Collection;

import entities.Student;

public interface StudentService {

	Collection<Student> getStudents();

	Student getStudentById(long id);

	void updateStudent(Student student);
	
	void saveStudent(Student student);
}
