package embedded_key;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DataClassStudentKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "class_id")
	private long dataClassId;
	@Column(name = "student_id")
	private long studentId;

	public long getDataClassId() {
		return dataClassId;
	}

	public void setDataClassId(long dataClassId) {
		this.dataClassId = dataClassId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataClassId + "" + studentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || (this.getClass() != obj.getClass()))
			return false;
		DataClassStudentKey other = (DataClassStudentKey) obj;
		return this.dataClassId == other.dataClassId && this.studentId == other.studentId;
	}
}
