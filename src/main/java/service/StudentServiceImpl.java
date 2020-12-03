package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Student;

public class StudentServiceImpl implements StudentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Collection<Student> getStudents() {
		return entityManager.createQuery(
				"select s from Student s left join fetch s.dataClassStudents left join fetch s.dataClasses",
				Student.class).getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public Student getStudentById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStudent(Student student) {
		entityManager.merge(student);
	}

	@Override
	public void saveStudent(Student student) {
		entityManager.persist(student);
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
