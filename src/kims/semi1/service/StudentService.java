package kims.semi1.service;

import kims.semi1.dao.StudentDao;
import kims.semi1.model.Student;

public class StudentService {
	private final StudentDao studentDao;

	public StudentService() {
		studentDao = new StudentDao();
	}

	public Student getStudentInfo(int studentId) {
		Student student = studentDao.getStudentById(studentId);
		return student;
	}

	public void printStudentInfo(Student student) {
		System.out.println("---------------------------학생 정보----------------------------");
		System.out.println("학번 : " + student.getStudentId() + " \t 이름 : " + student.getName());
		System.out.println("전화번호 : " + student.getPhone() + " \t 생년월일 : " + student.getBirthDate());
		System.out.println("email : " + student.getEmail());
		System.out.println("학과 : " + student.getDepartmentId() + " \t 입학년도 : " + student.getEnrollmentYear());
		System.out.println("---------------------------------------------------------------");
	}
}
