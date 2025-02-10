package kims.semi1.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import kims.semi1.config.DBConnector;
import kims.semi1.dao.ProfessorDao;
import kims.semi1.model.Professor;
import kims.semi1.service.ProfessorService;
import kims.semi1.view.ProfessorFrame;

public class ProfessorController {

	LoginController loginController;
	private final ProfessorService professorService;
	int currentUserId;
	Professor professor;
	ProfessorDao professorDao;
	ProfessorFrame professorFrame;
	// 변경 값 저장 변수
	String typeName = "name";
	String typePhone = "phone";
	String typeEmail = "email";
	String typePw = "password";

	public ProfessorController() {
		this.professorService = new ProfessorService();
		this.professorDao = new ProfessorDao();
	}

	public ProfessorController(int currentUserId) {
		this.currentUserId = currentUserId;
		this.professorDao = new ProfessorDao();

		this.professorService = new ProfessorService();
		Object[] temps = professorService.getProfessorInfo(currentUserId);
		professor = (Professor) temps[0];
	}

	public ProfessorController(int currentUserId, ProfessorFrame professorFrame) {
		this.professorFrame = professorFrame;
		this.currentUserId = currentUserId;
		this.professorDao = new ProfessorDao();

		this.professorService = new ProfessorService();
		Object[] temps = professorService.getProfessorInfo(currentUserId);
		professor = (Professor) temps[0];
	}

	public void printProfessorMenu(Scanner sc) {

		System.out.println("==교수 정보 메뉴==");
		System.out.println("1.교수 정보 수정");
		System.out.println("2.강의 관리");
		System.out.println("3.학생 성적 관리");
		System.out.println("4.로그아웃");
		System.out.println("메뉴 > ");

		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {

		case 1:
			modifyProfessorInfo(sc);

		case 2:
			managementCourse(sc);
		case 3:
			studentGradeManagement(sc);
		case 4:
			loginController = new LoginController();
			loginController.handleUserInput(sc);
		}

	}

	public void studentGradeManagement(Scanner sc) {

		while (true) {
			System.out.println("학생 성적 관리");
			System.out.println("1. 학생 성적 기입");
			System.out.println("2. 학생 성적 조회");
			System.out.println("3. 학생 성적 수정");
			System.out.println("4. 나가기");

			int gradeInput = sc.nextInt();
			sc.nextLine();

			switch (gradeInput) {

			case 1:
				professorDao.selectStudentInfo(currentUserId, sc);
			case 2:
				System.out.println("학생 성적 조회");
				System.out.println("1. 전체 조회");
				System.out.println("2. 검색 조회");
				System.out.println("3. 나가기");
				int selectInput = sc.nextInt();
				sc.nextLine();

				switch (selectInput) {

				case 1:
					professorDao.selectAllGrade(currentUserId);
					studentGradeManagement(sc);
				case 2:
					System.out.println("1. 학번 조회");
					System.out.println("2. 이름 조회");

					int input = sc.nextInt();
					sc.nextLine();

					if (input == 1) {
						professorDao.studentIdSelectGrade(currentUserId, sc);
					} else if (input == 2) {
						professorDao.studentNameGrade(currentUserId, sc);
					}
					studentGradeManagement(sc);
				}
			case 3:
				professorDao.correctionGrade(currentUserId, sc);
				studentGradeManagement(sc);
			case 4:
				System.out.println("나가기");
				printProfessorMenu(sc);
			}
		}

	}

	public void managementCourse(Scanner sc) {
		while (true) {
			System.out.println("==강의 관리 메뉴==");
			System.out.println("1. 강의 등록");
			System.out.println("2. 강의 현황 조회");
			System.out.println("3. 강의 평가 조회");
			System.out.println("4. 시간표 조회");
			System.out.println("5. 나가기");

			int professorInfoInput = sc.next().charAt(0) - '0';
			sc.nextLine();

			switch (professorInfoInput) {
			case 1:
				professorDao.printCourseInfoInRegistCourse(currentUserId);
				professorDao.registCourse(currentUserId, sc);
			case 2:
				professorDao.printCourseInfo(currentUserId);
			case 3:
				professorDao.printStudentRivew(currentUserId);
			case 4:
				professorDao.selectSchedule(currentUserId);
			case 5:
				printProfessorMenu(sc);

			}
		}
	}

	public void modifyProfessorInfo(Scanner sc) {
		Professor updateProfessorInfo = new Professor(professor);
		while (true) {

			printProfessorInfo();
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
				System.out.print("이름>>");
				updateProfessorInfo.setName(sc.next());
				sc.nextLine();
				break;
			case 2:
				System.out.print("전화번호>>");
				updateProfessorInfo.setPhone(sc.next());
				sc.nextLine();
				break;
			case 3:
				System.out.print("email>>");
				updateProfessorInfo.setEmail(sc.next());
				sc.nextLine();
				break;
			case 4:
				System.out.print("비밀번호>>");
				updateProfessorInfo.setPassword(sc.next());
				sc.nextLine();
				break;
			case 5:
				if (professorService.updateStudentInfo(updateProfessorInfo) == null) {
					System.out.println("등록 실패.");
					break;
				} else {
					professor = updateProfessorInfo;
					System.out.println("등록 성공!");
					managementCourse(sc);
					return;
				}
			case 6:
				System.out.println("취소되었습니다.");
				managementCourse(sc);
				return;
			default:
				System.out.println("다시 입력해주세요.");
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
