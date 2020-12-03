package service;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.DataClassStudent;

public class DataClassStudentServiceImpl implements DataClassStudentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Collection<DataClassStudent> getDataClassStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDataClassStudent(DataClassStudent dataClassStudent) {
		entityManager.persist(dataClassStudent);
	}

	@Override
	public void removeDataClassStudent(DataClassStudent dataClassStudent) {
		entityManager.createNativeQuery(
				"delete from dataclassstudent dcs where dcs.dataclass_id=" + dataClassStudent.getDataClass().getId()
						+ " and dcs.student_id=" + dataClassStudent.getStudent().getId())
				.executeUpdate();
	}

}
