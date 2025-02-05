package kims.semi1.model;

import java.time.LocalDate;

public class Student {
	private int studentId;
	private String name;
	private String phone;
	private LocalDate birthDate;
	private String email;
	private String password;
	private int departmentId;
	private int enrollmentYear;

	public Student(int studentId, String name, String phone, LocalDate birthDate, String email, String password,
			int departmentId, int enrollmentYear) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.phone = phone;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.departmentId = departmentId;
		this.enrollmentYear = enrollmentYear;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public int getEnrollmentYear() {
		return enrollmentYear;
	}

	public void setEnrollmentYear(int enrollmentYear) {
		this.enrollmentYear = enrollmentYear;
	}

}
