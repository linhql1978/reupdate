package entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class DataClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "id= " + id + ", name= " + name;
	}

	// ####
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Student monitor;

	public Student getMonitor() {
		return monitor;
	}

	public void setMonitor(Student monitor) {
		this.monitor = monitor;
	}
	// /####

	// ####
	@OneToMany(mappedBy = "dataClass")
	private Set<DataClassStudent> dataClassStudents;

	public void setDataClassStudents(Set<DataClassStudent> dataClassStudents) {
		this.dataClassStudents = dataClassStudents;
	}

	public Set<DataClassStudent> getDataClassStudents() {
		if (dataClassStudents == null)
			dataClassStudents = new HashSet<>();
		return dataClassStudents;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id + name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		DataClass dbc = (DataClass) obj;
		return this.id == dbc.id;
	}
	// /####
}
