package kims.semi1.service;

import kims.semi1.dao.DepartmentDao;
import kims.semi1.dao.StudentDao;
import kims.semi1.model.Department;
import kims.semi1.model.Student;

public class StudentService {
	private final StudentDao studentDao;
	private final DepartmentDao departmentDao;

	public StudentService() {
		studentDao = new StudentDao();
		departmentDao = new DepartmentDao();
	}

	public Object[] getStudentAndDepartmentInfo(int studentId) {
		Student student = studentDao.getStudentById(studentId);
		Department department = departmentDao.getDepartmentById(student.getDepartmentId());
		Object[] result = new Object[2];
		result[0] = student;
		result[1] = department;
		return result;
	}

	//TODO 새롭게 입력받아 만들어진 newStudent의 값들을 유효성검사 해줘야함. ex)전화번호 형식 등
	public Student updateStudentInfo(Student newStudent) {
		return studentDao.updateStudent(newStudent) ? newStudent : null;
	}
}
