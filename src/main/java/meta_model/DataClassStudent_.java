package meta_model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import entities.DataClass;
import entities.DataClassStudent;
import entities.Student;

@StaticMetamodel(value = DataClassStudent.class)
public class DataClassStudent_ {

	public static volatile SingularAttribute<DataClassStudent, DataClass> dataClass;
	public static volatile SingularAttribute<DataClassStudent, Student> student;

	public static final String DATACLASS = "dataClass";
	public static final String STUDENT = "student";
}
