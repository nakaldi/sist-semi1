package kims.semi1.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import kims.semi1.config.DBConnector;
import kims.semi1.dao.ClassScheduleDao;
import kims.semi1.dao.ProfessorDao;
import kims.semi1.model.ClassSchedule;

public class ManagerController {
	ClassScheduleDao classScheduledao;

	public ManagerController() {
		this.classScheduledao = new ClassScheduleDao();
	}

	public ManagerController(int currentUserId) {
		this.classScheduledao = new ClassScheduleDao();
	}

	public void selectManagertMenu(Scanner sc) {
		while(true) {
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
				selectClassScheduleMenu(sc);
				break;
			case 2:
				professorInfo(sc);
				break;
			case 3:
				break;
			case 4:
				searchCourseInfo(sc);
				break;
			case 5:
				break;
			}
		}
	}

	// 2.교수정보관리
	public void professorInfo(Scanner sc) {
		while (true) {
			System.out.println("====교수 정보 관리====");
			System.out.println("1.교수 정보 조회");
			System.out.println("2.교수 정보 등록");
			System.out.println("3.메뉴로 돌아가기");
			System.out.print("메뉴>> ");
			int professorScInput = sc.next().charAt(0) - '0';
			sc.nextLine();

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				conn = DBConnector.getConnection();

				switch (professorScInput) {
				// 1.교수정보조회
				case 1:
					System.out
							.println("-----------------------------------교수 정보 조회-----------------------------------");
					String professorInfoSql = "select professor_id,name,phone,to_char(birth_date,'yyyy-mm-dd'),email,to_char(hire_date,'yy-mm-dd') from professors";
					ps = conn.prepareStatement(professorInfoSql);
					rs = ps.executeQuery();

					while (rs.next()) {
						String professorID = rs.getString("professor_id");
						String professorName = rs.getString("name");
						String professorPhone = rs.getString("phone");
						String professorBirth = rs.getString("to_char(birth_date,'yyyy-mm-dd')");
						String professorEmail = rs.getString("email");
						String professorHiredate = rs.getString("to_char(hire_date,'yy-mm-dd')");

						System.out.println(professorID + "|" + professorName + "|" + professorPhone + "|"
								+ professorBirth + "|" + professorEmail + "|" + professorHiredate);
					}
					break;

				// 2.교수정보등록
				case 2:
					
					System.out
							.println("-----------교수 정보 등록-----------");
					String professorSaveSql = "insert into professors(professor_id,name,phone,birth_date,email,password,department_id,hire_date )\r\n"
							+ "values(to_char(sysdate,'yyyy')||LPAD(seq_professor_num.nextval,6,'0'),?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
					ps = conn.prepareStatement(professorSaveSql);
	
					System.out.print("이름>> ");
					String saveName = sc.nextLine();
					ps.setString(1, saveName);

					System.out.print("전화번호>> ");
					String savePhone = sc.nextLine();
					ps.setString(2, savePhone);

					System.out.print("생년월일>> ");
					String saveBirthday = sc.nextLine();
					ps.setString(3, saveBirthday);

					System.out.print("이메일>> ");
					String saveEmail = sc.nextLine();
					ps.setString(4, saveEmail);

					String savePassword = saveBirthday;
					ps.setString(5, savePassword);

					System.out.print("학과번호>> ");
					String saveDepartmentId = sc.nextLine();
					ps.setString(6, saveDepartmentId);

					System.out.println("등록이 완료되었습니다.");
					
					ps.executeUpdate();
					
					break;

				case 3:
					System.out.println("학사관리시스템 메인화면으로 돌아갑니다.");
					return;
					
				default:
					System.out.println("올바른 메뉴를 선택해주세요.");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DBConnector.close(conn, ps);
			}
		}
	}

	// 4.강의정보관리
	public void searchCourseInfo(Scanner sc) {
		while (true) {

			System.out.println("====강의 조회====");
			System.out.println("1.전체 강의 조회");
			System.out.println("2.특정 강의 조회");
			System.out.println("3.메뉴로 돌아가기");
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
					System.out.println("학사관리시스템 메인화면으로 돌아갑니다.");
					return;
					
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

	public void selectClassScheduleMenu(Scanner sc) {
		while (true) {
			System.out.println("1.시간표 조회");
			System.out.println("2.시간표 등록");
			System.out.println("3.시간표 삭제");
			System.out.print(">>");
			int input = sc.next().charAt(0) - '0';
			switch (input) {
			case 1:
				printClassScheduleInofo();
				break;
			case 2:
				saveClassScheduleInfo(sc);
				break;
			case 3:

				break;
			}
		}
	}

	// 데이터베이스에 저장되어있는 전체시간표를 보여주는 메소드
	public void printClassScheduleInofo() {

	}

	// 시간표 데이터 저장.
	public void saveClassScheduleInfo(Scanner sc) {
		ClassSchedule classchedules = new ClassSchedule(0, 0, null, null, null);
		System.out.print("강의ID>>");
		classchedules.setCourseId(sc.nextInt());
		System.out.print("요일>>");
		classchedules.setDayOfWeek(sc.next());
		System.out.print("시작시간>>");
		classchedules.setStartTime(sc.next());
		System.out.print("종료시간>>");
		classchedules.setEndTime(sc.next());
		System.out.println("1.등록 2.취소");
		int input = sc.nextInt();
		sc.nextLine();
		switch (input) {
		case 1:
			if (insertClassScheduleInfo(classchedules) == null) {
				System.out.println("등록실패");
			} else {
				System.out.println("등록성공");
			}
			break;
		case 2:
			System.out.println("취소되었습니다");
			break;
		}
	}

	public ClassSchedule insertClassScheduleInfo(ClassSchedule newClassSchedule) {
		return classScheduledao.insertClassSchedule(newClassSchedule) ? newClassSchedule : null;

	}

}
