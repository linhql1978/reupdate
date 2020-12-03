package convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Student;

@Named
public class StudentConverter implements Converter {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		try {
			return entityManager.createQuery(
					"select s from Student s left join fetch s.dataClassStudents left join fetch s.dataClasses where s.id="
							+ Long.valueOf(value),
					Student.class).getResultList().get(0);
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(value + " is not valid a Student ID"), e);
		} catch (IndexOutOfBoundsException e) {
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
