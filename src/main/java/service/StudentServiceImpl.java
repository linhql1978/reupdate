package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import entities.DataClass;
import entities.Student;
import meta_model.Student_;

@Transactional
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
		if (students == null)
			return new LinkedList<Student>();
		return students.stream().sorted((s1, s2) -> {
			if (s1.getId() > s2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}

	@Override
	public Collection<Student> getListStudentsOfDataClass(DataClass dataClass) {
		Collection<Long> keys = dataClass.getDataClassStudents().stream()
				.mapToLong(dcs -> dcs.getDataClassStudentKey().getStudentId()).boxed().collect(Collectors.toSet());
		if (keys.isEmpty())
			return new LinkedList<Student>();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> studentRoot = cq.from(Student.class);
		ParameterExpression<Collection> peKeys = cb.parameter(Collection.class);
		Predicate conditionKey = studentRoot.get(Student_.ID).in(peKeys);
		cq.select(studentRoot).where(conditionKey).distinct(true);
		TypedQuery<Student> query = entityManager.createQuery(cq).setParameter(peKeys, keys);
		return query.getResultList();

//		return entityManager.createQuery("select distinct s from Student s where s.id in :keys", Student.class)
//				.setParameter("keys", keys).getResultList();
	}

}
