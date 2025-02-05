package kims.semi1.service;

import kims.semi1.dao.ManagerDao;
import kims.semi1.dao.ProfessorDao;
import kims.semi1.dao.StudentDao;
import kims.semi1.model.Manager;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;

public class LoginService {
	private final ManagerDao managerDao;
	private final ProfessorDao professorDao;
	private final StudentDao studentDao;

	public LoginService() {
		managerDao = new ManagerDao();
		professorDao = new ProfessorDao();
		studentDao = new StudentDao();
	}

	/**
	 * ID 랑 비밀번호를 입력받아 로그인을 수행하는 메서드.
	 * 
	 * @return ({@code -1} if the login fails; {@code user ID} if the login is
	 *         successful.
	 */
	public int loginUser(int id, String password) {
		Object user = null;
		int userId = 0;

		if (managerDao.isManagerIdExist(id)) {
			user = managerDao.getManagerById(id);
		} else if (professorDao.isProfessorIdExist(id)) {
			user = professorDao.getProfessorById(id);
		} else if (studentDao.isStudentIdExist(id)) {
			user = studentDao.getStudentById(id);
		} else {
			System.out.println("id is not exist. return -1");
			return -1;
		}

		if (user instanceof Manager || password.equals(((Manager) user).getPassword())) {
			userId = ((Manager) user).getManagerId();
		} else if (user instanceof Professor || password.equals(((Professor) user).getPassword())) {
			userId = ((Professor) user).getProfessorId();
		} else if (user instanceof Student || password.equals(((Student) user).getPassword())) {
			userId = ((Student) user).getStudentId();
		} else {
			System.out.println("password is incorrect. return -1");
			return -1;
		}

		return userId;
	}

}
