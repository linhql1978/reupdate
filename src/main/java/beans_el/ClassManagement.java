package beans_el;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import entities.DataClass;
import entities.Student;
import service.DataClassService;

@Named
@ConversationScoped
public class ClassManagement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ####
	@Inject
	private DataClassService dataClassService;
	private Collection<DataClass> dataClasses;
	private DataClass dataClass;

	public DataClass getDataClass() {
		return dataClass;
	}

	public void setDataClass(DataClass dataClass) {
		this.dataClass = dataClass;
	}

	public void updateDataClass() {
		if (dataClass != null)
			dataClassService.updateDataClass(dataClass);
	}

	public void mergeDataClass() {
		if (dataClass != null)
			dataClassService.mergeDataClass(dataClass);
	}

	public void addDataClass() {
		dataClass = new DataClass();
		dataClassService.saveDataClass(dataClass);
		dataClasses.add(dataClass);
	}

	public Collection<DataClass> getDataClasses() {
		if (dataClasses == null)
			dataClasses = dataClassService.getDataClasses();
		return dataClasses.stream().sorted((dc1, dc2) -> {
			if (dc1.getId() > dc2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}
	// /####

	// ####
	private Collection<Student> studentsOfDataClass;

	public Collection<Student> getStudentsOfDataClass() {
		if (studentsOfDataClass == null)
			if (dataClass != null)
				studentsOfDataClass = dataClassService.getListStudentsOfDataClass(dataClass);
		return studentsOfDataClass.stream().sorted((s1, s2) -> {
			if (s1.getId() > s2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}

	private Student studentToAdd;

	public Student getStudentToAdd() {
		return studentToAdd;
	}

	public void setStudentToAdd(Student studentToAdd) {
		this.studentToAdd = studentToAdd;
	}
	// ###########################################
	public void checkStudentToAdd() {
		if (studentToAdd != null)
			if (studentsOfDataClass.contains(studentToAdd))
				throw new ValidatorException(new FacesMessage("Student already add to DataClass"));
	}
	// /####

	// ####
//	private DataClass dataClass;
//
//	public DataClass getDataClass() {
//		return dataClass;
//	}
//
//	public void toggle(DataClass dataClass) {
//		this.dataClass = dataClass;
//		setUpStudentsOuterDataClass();
//	}
//
//	public void toggle() {
//		dataClass = null;
//	}
//
//	public void updateDataClass() {
//		session.update(dataClass);
//	}
//
//	public void addDataClass() {
//		dataClass = new DataClass();
//		session.save(dataClass);
//
//		dataClasses.add(dataClass);
//	}
//
//	public void removeDataClass(DataClass dataClass) {
//		if (dataClasses != null && dataClass != null && dataClasses.contains(dataClass)) {
//			session.remove(dataClass);
//			dataClasses.remove(dataClass);
//		}
//	}

	// remove student from dataClass
//	public void removeStudent(Student student) {
//		if (dataClass != null && student != null) {
//			student = session // just only owner remove is ok
//					.createQuery("select s from Student s left join fetch s.dataClasses where s.id=" + student.getId(),
//							Student.class)
//					.list().get(0);
//			dataClass.getStudents().remove(student);
//			studentsOuterDataClass.add(student);
//			student.getDataClasses().remove(dataClass);
//			session.update(student);
//		}
//	}
	// /####

	// ####
//	private Student studentToAdd;
//
//	public Student getStudentToAdd() {
//		return studentToAdd;
//	}
//
//	public void setStudentToAdd(Student studentToAdd) {
//		this.studentToAdd = studentToAdd;
//	}
//
//	// ###
//	private Collection<Student> studentsOuterDataClass;
//
//	public void setUpStudentsOuterDataClass() {
//		Collection<Student> listStudent = session
//				.createQuery("select s from Student s left join fetch s.dataClasses", Student.class).list().stream()
//				.collect(Collectors.toSet());
//		studentsOuterDataClass = listStudent.stream().filter(s -> !dataClass.getStudents().contains(s))
//				.collect(Collectors.toList());
//	}
//
//	// get students which not relation with dataClass
//	public Collection<Student> getStudentsOuterDataClass() { // <f:selectItems call to four times ???
//		if (studentsOuterDataClass == null)
//			studentsOuterDataClass = new HashSet<Student>();
//		return studentsOuterDataClass.stream().filter(s -> !dataClass.getStudents().contains(s)).sorted((s1, s2) -> {
//			if (s1.getId() > s2.getId())
//				return 1;
//			return -1;
//		}).collect(Collectors.toList());
//	}
//	// /###
//
//	public void addStudentToDataClass() {
//		if (dataClass != null && studentToAdd != null) {
//			dataClass.getStudents().add(studentToAdd);
//			studentsOuterDataClass.remove(studentToAdd);
//			session.detach(studentToAdd);
//			studentToAdd.getDataClasses().add(dataClass);
//			session.update(studentToAdd);
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
