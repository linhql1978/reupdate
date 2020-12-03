package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.DataClass;
import entities.Student;

public class DataClassServiceImpl implements DataClassService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Collection<DataClass> getDataClasses() {
		return entityManager.createQuery(
				"select dc from DataClass dc left join fetch dc.dataClassStudents left join fetch dc.monitor",
				DataClass.class).getResultList().stream().collect(Collectors.toSet());
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
	public void updateDataClass(DataClass dataClass) {
		entityManager.merge(dataClass);
	}

	@Override
	public void mergeDataClass(DataClass dataClass) {
		entityManager.merge(dataClass);
	}

	@Override
	public Collection<Student> getListStudentsOfDataClass(DataClass dataClass) {
		Collection<Long> keys = dataClass.getDataClassStudents().stream()
				.mapToLong(dcs -> dcs.getDataClassStudentKey().getStudentId()).boxed().collect(Collectors.toSet());

		return entityManager.createQuery(
				"select s from Student s left join fetch s.dataClassStudents left join fetch s.dataClasses where s.id in :keys",
				Student.class).setParameter("keys", keys).getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public Collection<DataClass> sortedDataClassById(Collection<DataClass> dataClasses) {
		return dataClasses.stream().sorted((dc1, dc2) -> {
			if (dc1.getId() > dc2.getId())
				return 1;
			return -1;
		}).collect(Collectors.toList());
	}

}
