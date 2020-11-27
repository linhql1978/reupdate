package convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.hibernate.Session;

import entities.Student;
import utiltis.HibernateUtils;

@FacesConverter("studentConverter")
public class StudentConverter implements Converter {

	private Session session = HibernateUtils.getSessionFactory().openSession();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		try {
			return session.createQuery(
					"select s from Student s left join fetch s.dataClassStudents left join fetch s.dataClass where s.id="
							+ Long.valueOf(value),
					Student.class).list().get(0);
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(value + " is not valid a Student ID"), e);
		} catch (IndexOutOfBoundsException e) {
			throw new ConverterException(new FacesMessage("Id " + value + " is not exist for a Student"), e);
		} finally {
			session.close();
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
