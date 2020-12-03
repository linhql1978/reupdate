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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import entities.DataClass;
import meta_model.DataClass_;

@Transactional
public class DataClassServiceImpl implements DataClassService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Collection<DataClass> getDataClasses() {
//		DataClass dc = entityManager.find(DataClass.class, 1L); // demo auto-commit after every single SQL statement for 
//		System.out.println(entityManager.contains(dc)); // Container-managed entity manager

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DataClass> cq = cb.createQuery(DataClass.class);
		Root<DataClass> dataClassRoot = cq.from(DataClass.class);
		dataClassRoot.fetch(DataClass_.MONITOR, JoinType.LEFT);
		dataClassRoot.fetch(DataClass_.DATACLASSSTUDENTS, JoinType.LEFT);
		cq.select(dataClassRoot).distinct(true);
		TypedQuery<DataClass> query = entityManager.createQuery(cq);
		return query.getResultList();

//		return entityManager.createQuery(
//				"select distinct dc from DataClass dc left join fetch dc.dataClassStudents left join fetch dc.monitor",
//				DataClass.class).getResultList();

//		return entityManager.createQuery(
//				"select dc from DataClass dc left join fetch dc.dataClassStudents left join fetch dc.monitor",
//				DataClass.class).getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public DataClass getDataClassById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDataClass(DataClass dataClass) {
		entityManager.persist(dataClass);
	}

	@Override
	public void mergeDataClass(DataClass dataClass) {
		entityManager.merge(dataClass);
	}

	@Override
	public Collection<DataClass> sortedDataClassById(Collection<DataClass> dataClasses) {
		if (dataClasses == null)
			return new LinkedList<DataClass>();
		return dataClasses.stream().sorted((dc1, dc2) -> {
			if (dc1.getId() > dc2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}

}
