package kims.semi1.service;

import java.sql.Date;
import java.time.LocalDate;

import kims.semi1.dao.ManagerDao;
import kims.semi1.dao.ProfessorDao;
import kims.semi1.dao.StudentDao;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;

public class FindingIdService {

	private final ManagerDao managerDao;
	private final ProfessorDao professorDao;
	private final StudentDao studentDao;

	public FindingIdService() {
		managerDao = new ManagerDao();
		professorDao = new ProfessorDao();
		studentDao = new StudentDao();
	}

	/**
	 * 이름, 생년월일, 이메일 매개변수로 아이디를 찾아 반환.
	 * 
	 * @return ({@code -1} if the info does not exist; {@code user ID} if the
	 *         finding is successful.
	 */
	public int findId(String name, LocalDate birthDate, String email) {
		Student student = studentDao.findStudentByNameAndBirthDateAndEmail(name, birthDate, email);
		Professor professor = professorDao.findProfessorByNameAndBirthDateAndEmail(name, birthDate, email);
		if (student != null) {
			return student.getStudentId();
		} else if (professor != null) {
			return professor.getProfessorId();
		} else {
			return -1;
		}
	}
}
