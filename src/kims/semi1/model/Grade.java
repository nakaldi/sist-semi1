package kims.semi1.model;

public class Grade {
	private int gradeId;
	private int enrollmentId;
	private double grade;
	private String studentReview;

	public Grade() {
	}

	public Grade(int gradeId, int enrollmentId, double grade, String studentReview) {
		super();
		this.gradeId = gradeId;
		this.enrollmentId = enrollmentId;
		this.grade = grade;
		this.studentReview = studentReview;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public int getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public String getStudentReview() {
		return studentReview;
	}

	public void setStudentReview(String studentReview) {
		this.studentReview = studentReview;
	}

}
