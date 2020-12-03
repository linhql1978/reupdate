package beans_utils.class_management;

import javax.enterprise.context.RequestScoped;

import qualifier.AddStudentToDataClassMessage;

@RequestScoped
@AddStudentToDataClassMessage
public class MessageImpl implements Message {

	private String message = "";

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void updateMessage() {
		this.message = "Student already add to DataClass";
	}

}
