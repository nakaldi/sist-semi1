package kims.semi1.service;

import kims.semi1.dao.ManagerDao;
import kims.semi1.model.Manager;

public class LoginService {
	private final ManagerDao managerDao;

	public LoginService() {
		managerDao = new ManagerDao();
	}

	public int loginUser(int managerId, String password) {

		if (managerDao.isManagerIdExist(managerId) == false) {
			return 0;
		}
		Manager loggedInUser = managerDao.getManagerById(managerId);
		if (password.equals(loggedInUser.getPassword()) == false) {
			System.out.println("Password is incorrect");
			return 0;
		}
		return loggedInUser.getManagerId();
	}

}
