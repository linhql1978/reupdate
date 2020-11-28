package beans_utils;

import java.io.Serializable;

import qualifier.AddStudentToDataClassDisplay;

@AddStudentToDataClassDisplay
public class DisplayImpl implements Display, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean value = true;

	@Override
	public boolean check() {
		return value;
	}

	@Override
	public void change() {
		value = !value;
	}

	@Override
	public void set(boolean value) {
		this.value = value;
	}

}
