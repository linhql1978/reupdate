package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.hibernate.Session;

import entities.DataClass;
import entities.Student;
import qualifier.HibernateSession;

public class DataClassServiceImpl implements DataClassService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@HibernateSession
	private Session session;

	@Override
	public Collection<DataClass> getDataClasses() {
		return session.createQuery(
				"select dc from DataClass dc left join fetch dc.dataClassStudents left join fetch dc.monitor",
				DataClass.class).list().stream().collect(Collectors.toSet());
	}

	@Override
	public DataClass getDataClassById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDataClass(DataClass dataClass) {
		session.save(dataClass);
	}

	@Override
	public void updateDataClass(DataClass dataClass) {
		session.update(dataClass);
	}

	@Override
	public void mergeDataClass(DataClass dataClass) {
		session.merge(dataClass);
	}

	@Override
	public Collection<Student> getListStudentsOfDataClass(DataClass dataClass) {
		Collection<Long> keys = dataClass.getDataClassStudents().stream()
				.mapToLong(dcs -> dcs.getDataClassStudentKey().getStudentId()).boxed().collect(Collectors.toSet());

		return session.createQuery(
				"select s from Student s left join fetch s.dataClassStudents left join fetch s.dataClass where s.id in :keys",
				Student.class).setParameter("keys", keys).list().stream().collect(Collectors.toSet());
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
