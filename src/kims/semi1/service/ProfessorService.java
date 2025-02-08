package kims.semi1.service;

import kims.semi1.dao.ProfessorDao;
import kims.semi1.model.Professor;

public class ProfessorService {
	private final ProfessorDao professorDao;

	public ProfessorService() {

		professorDao = new ProfessorDao();
	}

	public Object[] getProfessorInfo(int currentId) {

		Professor professor = professorDao.getProfessorById(currentId);
		Object[] result = new Object[2];
		result[0] = professor;
		return result;
	}

	public Professor updateStudentInfo(Professor professor) {
		return professorDao.updateProfessor(professor) ? professor : null;
	}
}
