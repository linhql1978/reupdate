package service;

import java.util.Collection;

import entities.DataClass;

public interface DataClassService {

	Collection<DataClass> getDataClasses();

	DataClass getDataClassById(long id);

	void saveDataClass(DataClass dataClass);

	void mergeDataClass(DataClass dataClass);

	Collection<DataClass> sortedDataClassById(Collection<DataClass> dataClasses);
}
