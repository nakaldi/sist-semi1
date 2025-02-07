package kims.semi1.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import kims.semi1.config.DBConnector;
import kims.semi1.dao.ProfessorDao;

public class ProfessorController {

	int currentUserId;
	ProfessorDao professorDao;
	// 변경 값 저장 변수
	String typeName = "name";
	String typePhone = "phone";
	String typeEmail = "email";
	String typePw = "password";

	public ProfessorController() {

		this.professorDao = new ProfessorDao();
	}

	public ProfessorController(int currentUserId) {
		this.currentUserId = currentUserId;
		this.professorDao = new ProfessorDao();
	}

	public void printProfessorMenu(Scanner sc) {

		printProfessorInfo();

		System.out.println("1.교수 정보 수정");
		System.out.println("2.강의 관리");
		System.out.println("3.학생 출결 관리");
		System.out.println("4.학생 성적 관리");
		System.out.println("5.로그아웃");
		System.out.println("메뉴 > ");

		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			modifyProfessorInfo(sc);

		}
	}

	public void modifyProfessorInfo(Scanner sc) {
		while (true) {

			System.out.println("==개인 정보 수정==");
			System.out.println("1.이름 수정");
			System.out.println("2.전화번호 수정");
			System.out.println("3.이메일 수정");
			System.out.println("4.비밀번호 수정");
			System.out.println("5.등록");
			System.out.println("6.나가기");

			int professorInfoInput = sc.next().charAt(0) - '0';
			sc.nextLine();

			switch (professorInfoInput) {

			case 1:
				System.out.println("변경할 이름 : ");
				String modifyName = sc.nextLine();

				professorDao.modifyProfessorInfo(typeName, modifyName, currentUserId, sc);
				break;

			case 2:
				System.out.println("변경할 전화번호 : ");
				String modifyPhone = sc.nextLine();

				professorDao.modifyProfessorInfo(typePhone, modifyPhone, currentUserId, sc);
				break;

			case 3:
				System.out.println("변경할 이메일 : ");
				String modifyEmail = sc.nextLine();

				professorDao.modifyProfessorInfo(typeEmail, modifyEmail, currentUserId, sc);
				break;

			case 4:
				System.out.println("변경할 비밀번호 : ");
				String modifyPw = sc.nextLine();

				professorDao.modifyProfessorInfo(typePw, modifyPw, currentUserId, sc);
				break;

			case 5:
				// 미구현
				break;

			case 6:
				printProfessorInfo();
				break;
			}

		}
	}

	private void printProfessorInfo() {
		System.out.println("==교수 정보==");

		String professorInfoSql = "select name,phone,email,hire_date from professors where professor_id = ? ";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement ps = conn.prepareStatement(professorInfoSql)) {
			ps.setInt(1, currentUserId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String professorName = rs.getString("name");
					String professorPhone = rs.getString("phone");
					String professorEmail = rs.getString("email");
					LocalDate professorHireDate = rs.getDate("hire_date").toLocalDate();

					System.out.println(professorName + " | " + professorPhone + " | " + professorEmail + " | "
							+ professorHireDate);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
