package kims.semi1.service;

import java.util.List;

import kims.semi1.dao.DepartmentDao;
import kims.semi1.dao.GenericDao;
import kims.semi1.dao.StudentDao;
import kims.semi1.model.Building;
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Department;
import kims.semi1.model.Enrollment;
import kims.semi1.model.Student;

public class StudentService {
	private final StudentDao studentDao;
	private final DepartmentDao departmentDao;
	private final GenericDao genericDao;

	public StudentService() {
		studentDao = new StudentDao();
		departmentDao = new DepartmentDao();
		genericDao = new GenericDao();
		
	}

	public Object[] getStudentAndDepartmentAndBuildingInfo(int studentId) {
		Student student = studentDao.getStudentById(studentId);
		Department department = departmentDao.getDepartmentById(student.getDepartmentId());
		Building building = genericDao.findModel(Building.class, "building_id", department.getBuildingId());
		Object[] result = new Object[3];
		result[0] = student;
		result[1] = department;
		result[2] = building;
		return result;
	}

	// TODO 새롭게 입력받아 만들어진 newStudent의 값들을 유효성검사 해줘야함. ex)전화번호 형식 등
	public Student updateStudentInfo(Student newStudent) {
		return studentDao.updateStudent(newStudent) ? newStudent : null;
	}

	public List<CourseInfo> getCourseInfoBySemester(String semester) {

		if ((semester.equals("1") || semester.equals("2")) == false) {
			System.out.println("학기는 1, 2만 입력 가능합니다");
			return null;
		}
		return studentDao.findCourseInfosBySemester(semester);
	}

	public List<Enrollment> getEnrollmentInfosByStudentIdAndSemester(int studentId, String semester) {

		if ((semester.equals("1") || semester.equals("2")) == false) {
			System.out.println("학기는 1, 2만 입력 가능합니다");
			return null;
		}
		return studentDao.findEnrollmentInfosByStudentIdAndSemester(studentId, semester);
	}

	public boolean registerEnrollment(int studentId, int courseId) {
		if (studentDao.existsEnrollmentByStudentIdAndCourseId(studentId, courseId)) {
			System.out.println("이미 강의가 존재합니다.");
			return false;
		}
		return studentDao.insultEnrollment(studentId, courseId);
	}
}
