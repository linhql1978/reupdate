package service;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import entities.DataClassStudent;
import meta_model.DataClassStudent_;

@Transactional
public class DataClassStudentServiceImpl implements DataClassStudentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Collection<DataClassStudent> getDataClassStudents() {
		return null;
	}

	@Override
	public void saveDataClassStudent(DataClassStudent dataClassStudent) {
		entityManager.merge(dataClassStudent);
	}

	@Override
	public void removeDataClassStudent(DataClassStudent dataClassStudent) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<DataClassStudent> cd = cb.createCriteriaDelete(DataClassStudent.class);
		Root<DataClassStudent> dataClassStudentRoot = cd.from(DataClassStudent.class);
		Predicate conditionDataClass = cb.equal(dataClassStudentRoot.get(DataClassStudent_.DATACLASS),
				dataClassStudent.getDataClass());
		Predicate conditionStudent = cb.equal(dataClassStudentRoot.get(DataClassStudent_.STUDENT),
				dataClassStudent.getStudent());
		Predicate conditionFinal = cb.and(conditionDataClass, conditionStudent);
		cd.where(conditionFinal);

		entityManager.createQuery(cd).executeUpdate();

//		entityManager.createNativeQuery(
//				"delete from dataclassstudent dcs where dcs.dataclass_id=" + dataClassStudent.getDataClass().getId()
//						+ " and dcs.student_id=" + dataClassStudent.getStudent().getId())
//				.executeUpdate();
	}

}
