package service;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.hibernate.Session;

import entities.DataClassStudent;
import qualifier.HibernateSession;

public class DataClassStudentServiceImpl implements DataClassStudentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@HibernateSession
	private Session session;

	@Override
	public Collection<DataClassStudent> getDataClassStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDataClassStudent(DataClassStudent dataClassStudent) {
		session.save(dataClassStudent);
	}

	@Override
	public void removeDataClassStudent(DataClassStudent dataClassStudent) {
		session.createNativeQuery(
				"delete from dataclassstudent dcs where dcs.dataclass_id=" + dataClassStudent.getDataClass().getId()
						+ " and dcs.student_id=" + dataClassStudent.getStudent().getId())
				.executeUpdate();
	}

}
