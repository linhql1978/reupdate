package beans_el;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

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
		return students.stream().sorted((s1, s2) -> {
			if (s1.getId() > s2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}

	// /####

	// ####
//	private Student student;
//
//	public Student getStudent() {
//		return student;
//	}
//
//	public void toggle(Student student) {
//		this.student = student;
//		setUpDataClassesNotRegistryByStudent();
//	}
//
//	public void toggle() {
//		student = null;
//	}
//
//	public void updateStudent() {
//		session.update(student);
//	}
//
//	public void addStudent() {
//		student = new Student();
//		session.save(student);
//
//		students.add(student);
//	}
//
//	public void removeStudent(Student student) {
//		if (students != null && student != null && students.contains(student)) {
//			session.delete(student);
//			students.remove(student);
//		}
//	}
	// /####

	// ####
//	private DataClass dataClassToJoin;
//
//	public DataClass getDataClassToJoin() {
//		return dataClassToJoin;
//	}
//
//	public void setDataClassToJoin(DataClass dataClassToJoin) {
//		this.dataClassToJoin = dataClassToJoin;
//	}
//
//	public void joinDataClass() {
//		if (student != null && dataClassToJoin != null) {
//			student.getDataClasses().add(dataClassToJoin);
//			dataClassesNotRegistryByStudent.remove(dataClassToJoin);
//			session.update(student);
//		}
//	}

	// ###
//	private Collection<DataClass> dataClassesNotRegistryByStudent;
//
//	public void setUpDataClassesNotRegistryByStudent() {
//		Collection<DataClass> listDataClasses = session.createQuery("select dc from DataClass dc", DataClass.class)
//				.list();
//		dataClassesNotRegistryByStudent = listDataClasses.stream().filter(dc -> !student.getDataClasses().contains(dc))
//				.collect(Collectors.toList());
//	}
//
//	public Collection<DataClass> getDataClassesNotRegistryByStudent() { // <f:selectItems call to four times ???
//		if (dataClassesNotRegistryByStudent == null)
//			dataClassesNotRegistryByStudent = new HashSet<DataClass>();
//		return dataClassesNotRegistryByStudent.stream().filter(dc -> !student.getDataClasses().contains(dc))
//				.sorted((dc1, dc2) -> {
//					if (dc1.getId() > dc2.getId())
//						return 1;
//					return -1;
//				}).collect(Collectors.toList());
//	}

	// /###

//	public void leaveDataClass(DataClass dataClass) {
//		if (student != null && dataClass != null) {
//			student.getDataClasses().remove(dataClass);
//			dataClassesNotRegistryByStudent.add(dataClass);
//			session.update(student);
//		}
//	}
//
//	public void leaveAllDataClasses() {
//		if (student != null && !student.getDataClasses().isEmpty() && dataClassesNotRegistryByStudent != null) {
//			dataClassesNotRegistryByStudent.addAll(student.getDataClasses());
//			student.getDataClasses().clear();
//			session.update(student);
//		}
//	}
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
