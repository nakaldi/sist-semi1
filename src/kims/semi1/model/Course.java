package kims.semi1.model;

public class Course {
	private int courseId;
	private String name;
	private int professorId;
	private int departmentId;
	private String credit;
	private String semester;
	private String syllabus;

	public Course(int courseId, String name, int professorId, int departmentId, String credit, String semester,
			String syllabus) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.professorId = professorId;
		this.departmentId = departmentId;
		this.credit = credit;
		this.semester = semester;
		this.syllabus = syllabus;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}
}
