package convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import entities.Student;
import meta_model.Student_;

@Named
public class StudentConverter implements Converter {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Student> cq = cb.createQuery(Student.class);
			Root<Student> studentRoot = cq.from(Student.class);
			Predicate conditionId = cb.equal(studentRoot.get(Student_.ID), Long.valueOf(value));
			cq.select(studentRoot).where(conditionId);
			TypedQuery<Student> query = entityManager.createQuery(cq);

			return query.getSingleResult();

//			return entityManager.createQuery("select s from Student s where s.id=" + Long.valueOf(value), Student.class)
//					.getResultList().get(0);
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(value + " is not valid a Student ID"), e);
		} catch (NoResultException e) {
			throw new ConverterException(new FacesMessage("Id " + value + " is not exist for a Student"), e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		if (value.getClass() == Student.class) {
			return String.valueOf(((Student) value).getId());
		} else {
			throw new ConverterException(new FacesMessage(value.toString() + " is not valid a Student"));
		}
	}

}
