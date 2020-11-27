package entities;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrimaryKeyJoinColumn;

import embedded_key.DataClassStudentKey;

@Entity
public class DataClassStudent {

	@EmbeddedId
	private DataClassStudentKey dataClassStudentKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("dataClassId")
	@PrimaryKeyJoinColumn
	private DataClass dataClass;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("studentId")
	@PrimaryKeyJoinColumn
	private Student student;

	public DataClassStudentKey getDataClassStudentKey() {
		return dataClassStudentKey;
	}

	public void setDataClassStudentKey(DataClassStudentKey dataClassStudentKey) {
		this.dataClassStudentKey = dataClassStudentKey;
	}

	public DataClass getDataClass() {
		return dataClass;
	}

	public void setDataClass(DataClass dataClass) {
		this.dataClass = dataClass;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public int hashCode() {
		return Objects.hash("");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || (this.getClass() != obj.getClass()))
			return false;
		DataClassStudent other = (DataClassStudent) obj;
		return this.getDataClass().equals(other.getDataClass()) && this.getStudent().equals(other.getStudent());
	}

}
