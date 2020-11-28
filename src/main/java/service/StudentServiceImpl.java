package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.hibernate.Session;

import entities.Student;
import qualifier.HibernateSession;

public class StudentServiceImpl implements StudentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@HibernateSession
	private Session session;

	@Override
	public Collection<Student> getStudents() {
		return session
				.createQuery("select s from Student s left join fetch s.dataClassStudents left join fetch s.dataClass",
						Student.class)
				.list().stream().collect(Collectors.toSet());
	}

	@Override
	public Student getStudentById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStudent(Student student) {
		session.update(student);
	}

	@Override
	public void saveStudent(Student student) {
		session.save(student);
	}

	@Override
	public Collection<Student> sortedStudentById(Collection<Student> students) {
		return students.stream().sorted((s1, s2) -> {
			if (s1.getId() > s2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}

}
