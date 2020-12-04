package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import entities.DataClass;
import entities.Student;
import meta_model.Student_;

@Transactional // indicate CMT and EPC can aware CMT and join to this transaction
public class StudentServiceImpl implements StudentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(type = PersistenceContextType.EXTENDED) // can't use outside EJB -> it is PC not EPC
	private EntityManager entityManager;

	@PersistenceUnit // use entityManagerFactory and create entityManager -> EM use EPC
	private EntityManagerFactory entityManagerFactory; // -> Application management EPC

	@Resource
	private UserTransaction userTransaction;

	@Override
	public Collection<Student> getStudents() {
//		Student student = entityManager.find(Student.class, 2L); // find ok even with PC and without active transaction
//		System.out.println(entityManager.contains(student)); // entityManager still use PC not EPC which without EJB
//		entityManager.persist(student); // -> throw Transaction is required to perform this operation (either use a transaction or extended persistence context)

//		EntityManager entityManager1 = entityManagerFactory.createEntityManager(); // EM created along with EPC
//		Student student1 = entityManager1.find(Student.class, 2L);
//		System.out.println(entityManager1.contains(student1)); // -> true due use EPC
//		student1.setName(student1.getName() + "a"); // EPC queue this operate when without transaction and not throw
//													// exception, if have CMT -> EPC can aware and join it and flush when CMT commit
//		// but EPC management by Application can't aware BMT -> must join EPC to
//		// transaction or create EPC inside transaction
////		try {
////			userTransaction.begin();
////			entityManager1.joinTransaction(); // EPC join to active transaction -> when commit queue operates flush to
////												// db
////			userTransaction.commit();
////		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
////				| HeuristicMixedException | HeuristicRollbackException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		entityManager1.close();

		return entityManager.createQuery(
				"select distinct s from Student s left join fetch s.dataClassStudents left join fetch s.dataClasses",
				Student.class).getResultList();
	}

	@Override
	public Student getStudentById(long id) {
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
