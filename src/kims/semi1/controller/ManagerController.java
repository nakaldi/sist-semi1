package kims.semi1.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import kims.semi1.config.DBConnector;
import kims.semi1.dao.ClassScheduleDao;
import kims.semi1.model.ClassSchedule;

public class ManagerController {
	ClassScheduleDao classScheduleDao;

	public ManagerController() {
		this.classScheduleDao = new ClassScheduleDao();
	}

	public ManagerController(int currentUserId) {
		this.classScheduleDao = new ClassScheduleDao();
	}

	public void selectManagertMenu(Scanner sc) {
		while (true) {
			System.out.println("1. 시간표 관리");
			System.out.println("2. 교수정보 관리");
			System.out.println("3. 학생 정보 관리");
			System.out.println("4. 강의 조회");
			System.out.println("5. 로그아웃");
			System.out.print("메뉴>> ");
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
				selectStudentMenu(sc);
				break;
			case 4:
				searchCourseInfo(sc);
				break;
			case 5:
				System.out.println("로그아웃 되었습니다");
				return;
			default:
				System.out.println("올바른 메뉴를 선택해주세요.");
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
					String professorInfoSql = "select professor_id,name,phone,to_char(birth_date,'yyyy-mm-dd'),email,to_char(hire_date,'yyyy-mm-dd') from professors";
					ps = conn.prepareStatement(professorInfoSql);
					rs = ps.executeQuery();

					while (rs.next()) {
						String professorID = rs.getString("professor_id");
						String professorName = rs.getString("name");
						String professorPhone = rs.getString("phone");
						String professorBirth = rs.getString("to_char(birth_date,'yyyy-mm-dd')");
						String professorEmail = rs.getString("email");
						String professorHiredate = rs.getString("to_char(hire_date,'yyyy-mm-dd')");

						System.out.println(professorID + "|" + professorName + "|" + professorPhone + "|"
								+ professorBirth + "|" + professorEmail + "|" + professorHiredate);
					}
					break;

				// 2.교수정보등록
				case 2:

					System.out.println("-----------교수 정보 등록-----------");
					String professorSaveSql = "insert into professors(professor_id,name,phone,birth_date,email,password,department_id,hire_date )\r\n"
							+ "values(to_char(sysdate,'yyyy')||LPAD(seq_professor_num.nextval,6,'0'),?,?,?,?,to_char(to_date(?,'yyyy/mm/dd'),'yyyymmdd'),?,to_char(sysdate,'yyyy-mm-dd'))";
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
			} finally {
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
			System.out.println("3.강의실 관리");
			System.out.println("4.메뉴로 돌아가기");
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
					unitandBuildingManage(sc);
					break;

				case 4:
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

	// 강의실 관리 메소드
	public void unitandBuildingManage(Scanner sc) {
		while (true) {
			System.out.println("1.건물 조회");
			System.out.println("2.건물 등록");
			System.out.println("3.강의실 조회");
			System.out.println("4.강의실 등록");
			System.out.println("5.강의실 삭제");
			System.out.println("6.이전 메뉴로 돌아가기");
			System.out.print("메뉴 >> ");
			int ubMenuInput = sc.next().charAt(0) - '0';
			sc.nextLine();

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				conn = DBConnector.getConnection();

				switch (ubMenuInput) {

				case 1: // 건물조회
					String buildingSearchsql = "select * from buildings";
					ps = conn.prepareStatement(buildingSearchsql);
					rs = ps.executeQuery();

					System.out.println("건물 번호	|건물 이름");
					while (rs.next()) {
						int buildingId = rs.getInt("building_id");
						String buildingname = rs.getString("name");
						System.out.println(buildingId + "\t" + "|" + buildingname);
					}

					break;
				case 2: // 건물등록 - 건물 번호가 이미 있으면 다시 입력

					String buildingIDSearch = "select building_id from buildings where building_id = ?";
					ps = conn.prepareStatement(buildingIDSearch);

					System.out.print("건물 번호 : ");
					int buildingId = sc.next().charAt(0) - '0';
					sc.nextLine();

					ps.setInt(1, buildingId);
					rs = ps.executeQuery();

					if (rs.next()) {
						System.out.println("이미 존재하는 건물 번호 입니다. 다른 번호로 입력해주세요.");
					} else {
						String buildingSavesql = "insert into buildings values(?,?)";
						ps = conn.prepareStatement(buildingSavesql);

						ps.setInt(1, buildingId);

						System.out.print("건물 이름 : ");
						String buildingName = sc.nextLine();
						ps.setString(2, buildingName);

						ps.executeUpdate();
					}

					break;
				case 3:
					searchUnitInfo();
					break;
				case 4:
					insertUnit(sc);
					break;
				case 5:
					deleteUnit(sc);
					break;
				case 6:
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
			System.out.println("4.메뉴로 돌아가기");
			System.out.print("메뉴>> ");
			int input = sc.next().charAt(0) - '0';
			sc.nextLine();

			switch (input) {
			case 1:
				printClassScheduleInofo();
				break;
			case 2:
				saveClassScheduleInfo(sc);
				break;
			case 3:
				deleteClassSchedule(sc);
				break;
			case 4:
				System.out.println("학사관리시스템 메인화면으로 돌아갑니다.");
				return;
			}
		}
	}

	// 데이터베이스에 저장되어있는 전체시간표 를 보여주는 메소드
	public void printClassScheduleInofo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnector.getConnection();
			String classScheduleInofo = "SELECT schedule_id,courses.name,day_of_week,start_time,end_time,professors.name AS professorname "
					+ "FROM class_schedules,courses,professors "
					+ "WHERE class_schedules.course_id = courses.course_id "
					+ "AND professors.professor_id = courses.professor_id";
			pstmt = conn.prepareStatement(classScheduleInofo);
			rs = pstmt.executeQuery();
			System.out.println("시간표ID	|강의명	|요일	|시작시간	|종료시간	|교수이름");
			while (rs.next()) {
				int schdeuleID = rs.getInt("schedule_id");
				String courseName = rs.getString("name");
				String dayOfWeek = rs.getString("day_of_week");
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");
				String professorName = rs.getString("professorname");
				System.out.println(schdeuleID + "\t" + "|" + courseName + "\t" + "|" + dayOfWeek + "\t" + "|"
						+ startTime + "\t" + "|" + endTime + "\t" + "|" + professorName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt, rs);
		}
	}

	// 시간표 데이터 저장.
	public void saveClassScheduleInfo(Scanner sc) {
		ClassSchedule classShedule = new ClassSchedule(0, 0, null, null, null);
		System.out.print("강의ID>>");
		classShedule.setCourseId(sc.nextInt());
		System.out.print("요일>>");
		classShedule.setDayOfWeek(sc.next());
		System.out.print("시작시간>>");
		classShedule.setStartTime(sc.next());
		System.out.print("종료시간>>");
		classShedule.setEndTime(sc.next());
		System.out.println("1.등록 2.취소");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			if (insertClassScheduleInfo(classShedule) == null) {
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

	// 시간표 삭제
	public void deleteClassSchedule(Scanner sc) {
		System.out.println("삭제하시겠습니까? 1. 삭제 2. 취소");
		System.out.print("선택>>");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			Connection conn = null;
			PreparedStatement pstmt = null;
			System.out.print("시간표 ID>> ");
			int inputClassScheduleID = sc.nextInt();
			try {
				conn = DBConnector.getConnection();
				String DeleteclassSchedule = "DELETE FROM class_schedules WHERE schedule_id = ?";
				pstmt = conn.prepareStatement(DeleteclassSchedule);
				pstmt.setInt(1, inputClassScheduleID);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("삭제되었습니다");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnector.close(conn, pstmt);
			}
			break;
		case 2:
			System.out.println("취소했습니다");
			break;
		}
	}

	public ClassSchedule insertClassScheduleInfo(ClassSchedule newClassSchedule) {
		return classScheduleDao.insertClassSchedule(newClassSchedule) ? newClassSchedule : null;
	}

	// 강의실 조회 메소드
	public void searchUnitInfo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnector.getConnection();
			String searchUnitInofo = "SELECT units.building_id,name,unit " + "FROM units,buildings "
					+ "WHERE units.building_id=buildings.building_id ";
			pstmt = conn.prepareStatement(searchUnitInofo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					System.out.println("건물번호	|건물명	|강의실번호	");
					int buildingId = rs.getInt("building_id");
					String buildingName = rs.getString("name");
					String unitName = rs.getString("unit");
					System.out.println(buildingId + "\t" + "|" + buildingName + "\t" + "|" + unitName + "\t" + "|");
				} while (rs.next());
			} else {
				System.out.println("강의실을 등록해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt, rs);
		}
	}

	// 강의실 추가 메소드
	public void insertUnit(Scanner sc) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String searchUnitInofo = "SELECT * FROM buildings ";
		
		try {
			conn = DBConnector.getConnection();
			pstmt = conn.prepareStatement(searchUnitInofo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("---------------------------------강 의 실 등 록---------------------------------------");
				String insertUnit = "insert into units values(?, ?))";
				System.out.print("건물번호>>");
				int buildingId = sc.nextInt();
				System.out.print("강의실ID>>");
				String unitName = buildingId + "-" + sc.next();
				sc.nextLine();
				conn = DBConnector.getConnection();
				pstmt = conn.prepareStatement(insertUnit);
				pstmt.setInt(1, buildingId);
				pstmt.setString(2, unitName);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("등록되었습니다");
				} else {
					System.out.println("등록이 안되었습니다");
				}
			} else {
				System.out.println("건물을 등록해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt, rs);
		}
	}

	// 강의실 삭제 메소드
	public void deleteUnit(Scanner sc) {
		System.out.println("삭제하시겠습니까? 1. 삭제 2. 취소");
		System.out.print("선택>>");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			Connection conn = null;
			PreparedStatement pstmt = null;
			System.out.print("강의실ID>> ");
			int inputUnit = sc.nextInt();
			try {
				conn = DBConnector.getConnection();
				String DeleteUnit = "DELETE FROM units WHERE unit = ?";
				pstmt = conn.prepareStatement(DeleteUnit);
				pstmt.setInt(1, inputUnit);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("삭제되었습니다");
				} else {
					System.out.println("삭제가 안되었습니다");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBConnector.close(conn, pstmt);
			}
			break;
		case 2:
			System.out.println("취소했습니다");
			break;
		}
	}

	// 학생정보조회 학생정보등록 나가기 화면을 보여주면서 선택할 수 있는 메소드
	public void selectStudentMenu(Scanner sc) {
		while (true) {
			System.out.println("1.학생정보조회 2.학생정보등록 3. 메뉴로 돌아가기");
			System.out.print("선택>>");
			int input = sc.next().charAt(0) - '0';
			sc.nextLine();
			switch (input) {
			case 1:
				searchStudentMain(sc);
				break;
			case 2:
				insertStudentInfo(sc);
				break;
			case 3:
				System.out.println("학사관리시스템 메인화면으로 돌아갑니다.");
				return;
			default:
				System.out.println("올바른 메뉴를 선택해주세요.");
			}
		}
	}

	// 학생정보조회 메뉴
	public void searchStudentMain(Scanner sc) {
		while (true) {
			System.out.println("1.학생정보전체조회 2.학생학번조회 3.학생정보등록 메뉴로 돌아가기");
			System.out.print("메뉴>> ");
			int input = sc.next().charAt(0) - '0';
			sc.nextLine();
			switch (input) {
			case 1:
				printAllStudentInfo();
				break;
			case 2:
				searchStudentInfo(sc);
				break;
			case 3:
				return;
			default:
				System.out.println("올바른 메뉴를 선택해주세요.");
			}
		}
	}

	// 특정학생을 찾는 기능
	public void searchStudentInfo(Scanner sc) {
		System.out.println("학번을 입력해주세요");
		System.out.print("메뉴>> ");
		int studentID = sc.nextInt();
		sc.nextLine();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnector.getConnection();
			String studentInofo = "SELECT student_id , name , phone , to_char(birth_date,'yyyy-mm-dd'), email , password , enrollment_year  "
					+ "FROM students " + "WHERE student_id = ? ";
			pstmt = conn.prepareStatement(studentInofo);
			pstmt.setInt(1, studentID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					String studentName = rs.getString("name");
					String phone = rs.getString("phone");
					String birthDate = rs.getString("to_char(birth_date,'yyyy-mm-dd')");
					String email = rs.getString("email");
					String password = rs.getString("password");
					String enrollmentYear = rs.getString("enrollment_year");
					System.out.println(studentID + "|" + studentName + "|" + phone + "|" + birthDate + "|" + email + "|"
							+ password + "|" + enrollmentYear);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt, rs);
		}
	}

	// 전체학생 출력 메서드
	public void printAllStudentInfo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection();
			String studentInofo = "SELECT student_id , name , phone , to_char(birth_date,'yyyy-mm-dd') , email , password , enrollment_year  "
					+ "FROM students ";
			pstmt = conn.prepareStatement(studentInofo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					int studentID = rs.getInt("student_id");
					String studentName = rs.getString("name");
					String phone = rs.getString("phone");
					String birthDate = rs.getString("to_char(birth_date,'yyyy-mm-dd')");
					String email = rs.getString("email");
					String password = rs.getString("password");
					String enrollmentYear = rs.getString("enrollment_year");
					System.out.println(studentID + "|" + studentName + "|" + phone + "|" + birthDate + "|" + email + "|"
							+ password + "|" + enrollmentYear);
				} while (rs.next());
			} else {
				System.out.println("학생정보가 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt, rs);
		}
	}

	// 학생등록하는 메서드
	public void insertStudentInfo(Scanner sc) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("---------------------------------학 생 등 록---------------------------------------");
		String insertStudentInfo = "insert into students (student_id, name, phone, birth_date, email, password, department_id, enrollment_year)"
				+ "values(to_char(sysdate,'yyyy')||LPAD(seq_student_num.nextval,6,'0'), ?, ?, ?, ?, to_char(to_date(?,'yyyy/mm/dd'),'yyyymmdd') , ?, to_char(sysdate,'yyyy'))";
		System.out.print("학생이름>>");
		String studentName = sc.next();
		System.out.print("전화번호>>");
		String phone = sc.next();
		System.out.print("생년월일>>");
		String birthDate = sc.next();
		System.out.print("이메일>>");
		String email = sc.next();
		System.out.print("학과번호>>");
		String department_id = sc.next();
		sc.nextLine();
		try {
			conn = DBConnector.getConnection();
			pstmt = conn.prepareStatement(insertStudentInfo);
			pstmt.setString(1, studentName);
			pstmt.setString(2, phone);
			pstmt.setString(3, birthDate);
			pstmt.setString(4, email);
			pstmt.setString(5, birthDate);
			pstmt.setString(6, department_id);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("등록되었습니다");
			} else {
				System.out.println("등록이 안되었습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt);
		}
	}
}
