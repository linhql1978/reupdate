package meta_model;

import java.sql.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import entities.DataClass;
import entities.DataClassStudent;
import entities.Student;

@StaticMetamodel(value = Student.class)
public class Student_ {

	public static volatile SingularAttribute<Student, Long> id;
	public static volatile SingularAttribute<Student, String> name;
	public static volatile SingularAttribute<Student, Date> dateOfBirth;
	public static volatile SingularAttribute<Student, String> email;
	public static volatile SingularAttribute<Student, String> phoneNumber;
	public static volatile SingularAttribute<Student, Date> joinDate;
	public static volatile SetAttribute<Student, DataClass> dataClasses;
	public static volatile SingularAttribute<Student, DataClassStudent> dataClassStudents;
}
