package service;

import java.util.Collection;

import entities.DataClassStudent;

public interface DataClassStudentService {

	Collection<DataClassStudent> getDataClassStudents();

	void saveDataClassStudent(DataClassStudent dataClassStudent);

	void removeDataClassStudent(DataClassStudent dataClassStudent);
}
