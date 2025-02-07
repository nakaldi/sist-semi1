package kims.semi1.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import kims.semi1.config.DBConnector;

public class ManagerController {

	public ManagerController() {

	}

	public ManagerController(int currentUserId) {

	}

	public void selectManagertMenu(Scanner sc) {
		System.out.println("1. 시간표 관리");
		System.out.println("2. 교수정보 관리");
		System.out.println("3. 학생 정보 관리");
		System.out.println("4. 강의 조회");
		System.out.println("5. 로그아웃");
		System.out.print("메뉴>");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:

		case 4:
			searchCourseInfo(sc);
		}
	}

//	// 2.교수정보관리
//	public void professorSearch(Scanner sc) {
//		
//		//교수 정보 조회 
//		System.out.println("====교수 정보 관리====");
//		System.out.println("1.교수 정보 조회");
//		System.out.println("2.교수 강의 조회");
//		System.out.print("메뉴>> ");
//		int professorScInput = sc.next().charAt(0) - '0';
//		sc.nextLine();
//	}

	// 4.강의정보관리
	public void searchCourseInfo(Scanner sc) {
		while (true) {

			System.out.println("====강의 조회====");
			System.out.println("1.전체 강의 조회");
			System.out.println("2.특정 강의 조회");
			System.out.println("3.나가기");
			System.out.print("메뉴>> ");
			int coursesInfoInput = sc.next().charAt(0) - '0';
			sc.nextLine();

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {

				conn = DBConnector.getConnection();

				switch (coursesInfoInput) {
				case 1:
					String coursesInfoSqlAll = "select course_id,courses.name,professors.name as \"담당교수\",departments.name as \"학과\" \r\n"
							+ "from courses,professors,departments\r\n"
							+ "where courses.professor_id = professors.professor_id \r\n"
							+ "and courses.department_id = departments.department_id";
					ps = conn.prepareStatement(coursesInfoSqlAll);
					rs = ps.executeQuery();

					while (rs.next()) {
						int courseId = rs.getInt("course_id");
						String courseName = rs.getString("name");
						String professorName = rs.getString("담당교수");
						String departmentsName = rs.getString("학과");

						System.out.println(
								courseId + " | " + courseName + " | " + professorName + " | " + departmentsName);
					}
					break;

				case 2:
					System.out.print("강의 ID>> ");
					int coursesIdInput = sc.next().charAt(0) - '0';
					sc.nextLine();

					String coursesInfoSql = "select course_id,courses.name,professors.name as \"담당교수\",departments.name as \"학과\" \r\n"
							+ "from courses,professors,departments\r\n"
							+ "where courses.professor_id = professors.professor_id \r\n"
							+ "and courses.department_id = departments.department_id\r\n" + "and course_id = ?";
					ps = conn.prepareStatement(coursesInfoSql);

					ps.setInt(1, coursesIdInput);
					rs = ps.executeQuery();

					if (rs.next()) {
						do {
							int courseId = rs.getInt("course_id");
							String courseName = rs.getString("name");
							String professorsName = rs.getString("담당교수");
							String departmentName = rs.getString("학과");

							System.out
									.println(courseId + "|" + courseName + "|" + professorsName + "|" + departmentName);

						} while (rs.next());
					} else {
						System.out.println("검색된 강의 ID가 없습니다. 다시 입력해주세요.");
					}
					break;

				case 3:
					selectManagertMenu(sc);
				default:
					System.out.println("올바른 메뉴를 선택해주세요.");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBConnector.close(conn, ps, rs);

			}

		}
	}
}
