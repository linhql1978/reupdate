package service;

import java.util.Collection;

import entities.DataClass;
import entities.Student;

public interface StudentService {

	Collection<Student> getStudents();

	Student getStudentById(long id);

	void updateStudent(Student student);

	void saveStudent(Student student);

	Collection<Student> getListStudentsOfDataClass(DataClass dataClass);

	Collection<Student> sortedStudentById(Collection<Student> students);
}
