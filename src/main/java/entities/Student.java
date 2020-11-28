package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date dateOfBirth;
	private String email;
	private String phoneNumber;
	private Date joinDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "id= " + id + ", name= " + name;
	}

	// ####
	@OneToMany(mappedBy = "monitor")
	private Set<DataClass> dataClasses;

	public Set<DataClass> getDataClasses() {
		if (dataClasses == null)
			dataClasses = new HashSet<>();
		return dataClasses;
	}
	// /####

	// ####
	@OneToMany(mappedBy = "student")
	private Set<DataClassStudent> dataClassStudents;

	public Set<DataClassStudent> getDataClassStudents() {
		if (dataClassStudents == null)
			dataClassStudents = new HashSet<>();
		return dataClassStudents;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id + name + dateOfBirth + email + phoneNumber + joinDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Student std = (Student) obj;
		return this.id == std.id;
	}
	// /####
}
