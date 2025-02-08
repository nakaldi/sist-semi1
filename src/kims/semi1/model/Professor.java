package kims.semi1.model;

import java.time.LocalDate;

public class Professor {
	private int professorId;
	private String name;
	private String phone;
	private LocalDate birthDate;
	private String email;
	private String password;
	private int departmentId;
	private LocalDate hireDate;

	public Professor(int professorId, String name, String phone, LocalDate birthDate, String email, String password,
			int departmentId, LocalDate hireDate) {
		super();
		this.professorId = professorId;
		this.name = name;
		this.phone = phone;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.departmentId = departmentId;
		this.hireDate = hireDate;
	}
	
	public Professor(Professor another) {
		super();
		this.professorId = another.getProfessorId();
		this.name = another.getName();
		this.phone = another.getPhone();
		this.birthDate = another.getBirthDate();
		this.email = another.getEmail();
		this.password = another.getPassword();
		this.departmentId = another.getDepartmentId();
		this.hireDate = another.getHireDate();
		
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

}
