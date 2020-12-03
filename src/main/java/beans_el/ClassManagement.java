package beans_el;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import beans_utils.class_management.Display;
import beans_utils.class_management.Message;
import entities.DataClass;
import entities.DataClassStudent;
import entities.Student;
import qualifier.AddStudentToDataClassDisplay;
import qualifier.AddStudentToDataClassMessage;
import service.DataClassService;
import service.DataClassStudentService;
import service.StudentService;

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
		dataClassService.mergeDataClass(dataClass);
	}

	public void addDataClass() {
		dataClass = new DataClass();
		dataClassService.saveDataClass(dataClass);
		dataClasses.add(dataClass);
	}

	public Collection<DataClass> getDataClasses() {
		if (dataClasses == null)
			this.dataClasses = dataClassService.getDataClasses();
		return dataClassService.sortedDataClassById(dataClasses);
	}
	// /####

	// ####
	@Inject
	private StudentService studentService;
	private Collection<Student> studentsOfDataClass;

	public void clearStudentsOfDataClass() {
		this.studentsOfDataClass = null;
	}

	public Collection<Student> getStudentsOfDataClass() {
		if (this.studentsOfDataClass == null)
			this.studentsOfDataClass = studentService.getListStudentsOfDataClass(this.dataClass);
		return this.studentService.sortedStudentById(this.studentsOfDataClass);
	}

	private Student studentToAdd;
	@Inject
	@AddStudentToDataClassDisplay
	private Display displayAddStudent;

	public Display getDisplayAddStudent() {
		return displayAddStudent;
	}

	public Student getStudentToAdd() {
		return studentToAdd;
	}

	public void setStudentToAdd(Student studentToAdd) {
		this.studentToAdd = studentToAdd;
	}

	@Inject
	private DataClassStudentService dataClassStudentService;
	@Inject
	@AddStudentToDataClassMessage
	private Message addStudentToDataClassMessage;

	public Message getAddStudentToDataClassMessage() {
		return addStudentToDataClassMessage;
	}

	public void addStudentToDataClass() {
		if (studentToAdd != null && !studentsOfDataClass.contains(studentToAdd)) {
			dataClassStudentService.saveDataClassStudent(new DataClassStudent(dataClass, studentToAdd));
			studentsOfDataClass.add(studentToAdd);
		} else if (studentToAdd != null)
			addStudentToDataClassMessage.updateMessage();
	}

	public void removeStudentFromDataClass(Student student) {
		if (studentsOfDataClass.contains(student)) {
			dataClassStudentService.removeDataClassStudent(new DataClassStudent(dataClass, student));
			studentsOfDataClass.remove(student);
		}
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
