package kims.semi1.model;

public class CourseInfo {
	Course course;
	Department department;
	ClassSchedule classSchedule;
	Professor professor;

	public CourseInfo(Course course, Department department, ClassSchedule classSchedule, Professor professor) {
		super();
		this.course = course;
		this.department = department;
		this.classSchedule = classSchedule;
		this.professor = professor;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public ClassSchedule getClassSchedule() {
		return classSchedule;
	}

	public void setClassSchedule(ClassSchedule classSchedule) {
		this.classSchedule = classSchedule;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
