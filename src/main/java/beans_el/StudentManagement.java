package beans_el;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Student;
import service.StudentService;

@Named
@ConversationScoped
public class StudentManagement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ####

	@Inject
	private StudentService studentService;
	private Collection<Student> students;
	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void updateStudent() {
		if (student != null)
			studentService.updateStudent(this.student);
	}

	public void addStudent() {
		student = new Student();
		studentService.saveStudent(student);
		students.add(student);
	}

	public Collection<Student> getStudents() {
		if (students == null)
			students = studentService.getStudents();
		return studentService.sortedStudentById(students);
	}
	// /####

	// ####
	@PostConstruct
	public void print() {
		System.out.println("PostConstruct " + this);
	}

	@PreDestroy
	public void print1() {
		System.out.println("PreDestroy " + this);
	}
	// /####
}
