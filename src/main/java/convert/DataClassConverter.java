package convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.DataClass;

@Named
public class DataClassConverter implements Converter {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		try {
			return entityManager
					.createQuery("select dc from DataClass dc where dc.id=" + Long.valueOf(value), DataClass.class)
					.getResultList().get(0);
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(value + " is not valid a DataClass ID"), e);
		} catch (IndexOutOfBoundsException e) {
			throw new ConverterException(new FacesMessage("ID " + value + " is not exist for a DataClass"), e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		if (value.getClass() == DataClass.class) {
			return String.valueOf(((DataClass) value).getId());
		} else {
			throw new ConverterException(new FacesMessage(value.toString() + " is not valid a DataClass"));
		}
	}

}
