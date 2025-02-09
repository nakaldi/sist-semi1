package kims.semi1.model;

public class Enrollment {
	private int enrollmentId;
	private int studentId;
	private int courseId;
	private CourseInfo courseInfo;
	private Grade grade;

	public Enrollment() {
	}

	public Enrollment(int enrollmentId, int studentId, int courseId) {
		super();
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
	}

	public Enrollment(int enrollmentId, int studentId, int courseId, CourseInfo courseInfo, Grade grade) {
		super();
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.courseInfo = courseInfo;
		this.grade = grade;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public int getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

}
