package meta_model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import entities.DataClass;
import entities.DataClassStudent;
import entities.Student;

@StaticMetamodel(value = DataClass.class)
public class DataClass_ {
	public static volatile SingularAttribute<DataClass, Long> id;
	public static volatile SingularAttribute<DataClass, String> name;
	public static volatile SingularAttribute<DataClass, Student> monitor;
	public static volatile SetAttribute<DataClass, DataClassStudent> dataClassStudents;

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String MONITOR = "monitor";
	public static final String DATACLASSSTUDENTS = "dataClassStudents";
}
