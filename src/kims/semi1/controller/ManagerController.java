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
	ClassScheduleDao classScheduledao;

	public ManagerController() {
		this.classScheduledao = new ClassScheduleDao();
	}

	public ManagerController(int currentUserId) {
		this.classScheduledao = new ClassScheduleDao();
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
			selectClassScheduleMenu(sc);
			break;
		case 2:
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

	public void selectClassScheduleMenu(Scanner sc) {
		while (true) {
			System.out.println("1.시간표 조회");
			System.out.println("2.시간표 등록");
			System.out.println("3.시간표 삭제");
			System.out.println("4.나가기");
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
				deleteClassSchedule(sc);
				break;
			case 4:
				selectManagertMenu(sc);
				break;
			}
		}
	}

	// 데이터베이스에 저장되어있는 전체시간표를 보여주는 메소드
	public void printClassScheduleInofo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnector.getConnection();
			String classScheduleInofo = "SELECT schedule_id,courses.name,day_of_week,start_time,end_time,professors.name AS professorname "
					  +"FROM class_schedules,courses,professors "
				      +"WHERE class_schedules.course_id = courses.course_id "
				      + "AND professors.professor_id = courses.professor_id";
			pstmt = conn.prepareStatement(classScheduleInofo);
			rs = pstmt.executeQuery();
			System.out.println("시간표ID	|강의명	|요일	|시작시간	|종료시간	|교수이름");
			while(rs.next()) {
				int schdeuleID = rs.getInt("schedule_id");
				String courseName = rs.getString("name");
				String dayOfWeek = rs.getString("day_of_week");
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");
				String professorName = rs.getString("professorname");
				System.out.println(schdeuleID +"\t"+"|"+courseName+"\t"+"|"+dayOfWeek+"\t"+"|"+startTime+"\t"+"|"+endTime+"\t"+"|"+professorName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		int input = sc.nextInt();
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
	//시간표 삭제
	public void deleteClassSchedule(Scanner sc) {
		System.out.println("삭제하시겠습니까? 1. 삭제 2. 취소");
		switch (sc.nextInt()){
		case 1: 
			Connection conn = null;
			PreparedStatement pstmt = null;
			System.out.print("시간표 ID>> ");
			int inputClassScheduleID = sc.next().charAt(0) - '0';
			try {
				conn = DBConnector.getConnection();
				String DeleteclassSchedule="DELETE FROM class_schedules WHERE schedule_id = ?";
				pstmt = conn.prepareStatement(DeleteclassSchedule);
				pstmt.setInt(1, inputClassScheduleID);
				int affectedRows = pstmt.executeUpdate();
				if(affectedRows > 0) {
					System.out.println("삭제되었습니다");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DBConnector.close(conn, pstmt);
			}
			break;
		case 2:
			System.out.println("취소했습니다");
			break;
		}
	}
	public ClassSchedule insertClassScheduleInfo(ClassSchedule newClassSchedule) {
		return classScheduledao.insertClassSchedule(newClassSchedule) ? newClassSchedule : null;

	}

}
