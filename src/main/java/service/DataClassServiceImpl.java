package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import entities.DataClass;

@Transactional
public class DataClassServiceImpl implements DataClassService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = "sample")
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
