package service;

import java.util.Collection;

import entities.DataClass;
import entities.Student;

public interface DataClassService {

	Collection<DataClass> getDataClasses();

	DataClass getDataClassById(long id);

	void saveDataClass(DataClass dataClass);

	void updateDataClass(DataClass dataClass);

	void mergeDataClass(DataClass dataClass);

	Collection<Student> getListStudentsOfDataClass(DataClass dataClass);
}
