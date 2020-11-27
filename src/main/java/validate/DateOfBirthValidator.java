package validate;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "dateOfBirthValidator")
public class DateOfBirthValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		LocalDate ldNow = LocalDate.now();
		LocalDate dateOfBirth = LocalDate.ofInstant(((Date) value).toInstant(), ZoneId.systemDefault());
		int age = Period.between(dateOfBirth, ldNow).getYears();
		if (age < 18) {
			FacesMessage msg = new FacesMessage("You are not 18 years old.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
