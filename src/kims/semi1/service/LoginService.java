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

		if (managerDao.existsManagerId(id)) {
			user = managerDao.getManagerById(id);
		} else if (professorDao.existsProfessorId(id)) {
			user = professorDao.getProfessorById(id);
		} else if (studentDao.existsStudentId(id)) {
			user = studentDao.getStudentById(id);
		} else {
			System.out.println("id does not exist. return -1");
			return -1;
		}

		// || && 논리연산자는 첫 번째 조건이 참이면 두 번째 조건을 평가하지 않는 단축 평가(short-circuit evaluation)를
		// 사용
		// 그래서 user 가 Manager 의 인스턴스가 아니면 뒤에 캐스팅은 일어나지 않아 예외가 발생하지 않음.
		if (user instanceof Manager && password.equals(((Manager) user).getPassword())) {
			userId = ((Manager) user).getManagerId();
		} else if (user instanceof Professor && password.equals(((Professor) user).getPassword())) {
			userId = ((Professor) user).getProfessorId();
		} else if (user instanceof Student && password.equals(((Student) user).getPassword())) {
			userId = ((Student) user).getStudentId();
		} else {
			System.out.println("password is incorrect. return -2");
			return -2;
		}

		return userId;
	}

}
