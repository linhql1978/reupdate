package validate;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "phoneNumberValidator")
public class PhoneNumberValidator implements Validator {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[0-9]{10}$");

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}

		String phoneNumber = (String) value;
		boolean matches = EMAIL_PATTERN.matcher(phoneNumber).matches();
		if (!matches) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"PhoneNumber is invalid (must is 10 digit, not character)", null);
			throw new ValidatorException(msg);
		}

	}

}
