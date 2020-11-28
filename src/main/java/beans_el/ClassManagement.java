package beans_el;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entities.DataClass;
import entities.DataClassStudent;
import entities.Student;
import message.Message;
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
		if (dataClass != null)
			dataClassService.updateDataClass(dataClass);
	}

	public void addDataClass() {
		dataClass = new DataClass();
		dataClassService.saveDataClass(dataClass);
		dataClasses.add(dataClass);
	}

	public Collection<DataClass> getDataClasses() {
		if (dataClasses == null)
			dataClasses = dataClassService.getDataClasses();
		return dataClassService.sortedDataClassById(dataClasses);
	}
	// /####

	// ####
	@Inject
	private StudentService studentService;
	private Collection<Student> studentsOfDataClass;

	public Collection<Student> getStudentsOfDataClass() {
		if (studentsOfDataClass == null)
			if (dataClass != null)
				studentsOfDataClass = dataClassService.getListStudentsOfDataClass(dataClass);
		return studentService.sortedStudentById(studentsOfDataClass);
	}

	private Student studentToAdd;

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

	public String getAddStudentToDataClassMessage() {
		return addStudentToDataClassMessage.getMessage();
	}

	public void addStudentToDataClass() {
		if (dataClass != null && studentToAdd != null && !studentsOfDataClass.contains(studentToAdd)) {
			dataClassStudentService.saveDataClassStudent(new DataClassStudent(dataClass, studentToAdd));
			studentsOfDataClass.add(studentToAdd);
		} else
			addStudentToDataClassMessage.updateMessage();
	}

	public void removeStudentFromDataClass(Student student) {
		if (dataClass != null && student != null && studentsOfDataClass.contains(student)) {
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
