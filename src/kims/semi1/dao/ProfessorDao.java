package kims.semi1.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Professor;

public class ProfessorDao {

	// professor_id 로 검색해서 하나라도 있으면 true 없으면 false 를 반환
	public boolean existsProfessorId(int professorId) {
		String sql = "SELECT COUNT(*) FROM professors WHERE professor_id = ?";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, professorId);

			// 자바 7부터 try-with-resources 문을 사용할 수 있음. 내부 try 에서 할당한 자원도 외부 catch 에서 반환됨.
			try (ResultSet rs = pstmt.executeQuery()) {
				// rs.next()가 존재하고, count 가 0보다 크면 아이디가 존재하므로 true 반환
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// rs.next가 없으면 문제가 있는것이기 때문에 false 반환
		System.out.println("resultset in ProfessorDao.isProfessorIdExist() is empty");
		return false;
	}

	// Professor 객체 professors 테이블에 저장
	public void insertProfessor(Professor professor) {
		String sql = "INSERT INTO "
				+ "professors (professor_id, name, phone, birth_date, email, password, department_id, hire_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, professor.getProfessorId());
			pstmt.setString(2, professor.getName());
			pstmt.setString(3, professor.getPhone());
			pstmt.setDate(4, Date.valueOf(professor.getBirthDate()));
			pstmt.setString(5, professor.getEmail());
			pstmt.setString(6, professor.getPassword());
			pstmt.setInt(7, professor.getDepartmentId());
			pstmt.setDate(8, Date.valueOf(professor.getHireDate()));

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 아이디로 검색해서 professors 테이블에서 Professor 객체 반환
	public Professor getProfessorById(int professorId) {
		String sql = "SELECT * FROM professors WHERE professor_id = ?";
		Professor professor = null;

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, professorId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {

					String name = rs.getString("name");
					String phone = rs.getString("phone");
					LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
					String email = rs.getString("email");
					String password = rs.getString("password");
					int departmentId = rs.getInt("department_id");
					LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
					professor = new Professor(professorId, name, phone, birthDate, email, password, departmentId,
							hireDate);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return professor;
	}

	//시간표 조회
	public int selectSchedule(int currentUserId) {
		System.out.println("==시간표 출력 메뉴==");
		String printSchedulesSql = "select c.name as course_name,p.name as professor_name,s.day_of_week,s.start_time,s.end_time "
				+ "from courses c, class_schedules s, professors p "
				+ "where c.professor_id = ? "
				+ "and c.course_id = s.course_id "
				+ "and c.professor_id = p.professor_id ";

		try (Connection conn = DBConnector.getConnection();
				PreparedStatement schedulsps = conn.prepareStatement(printSchedulesSql)) {
			schedulsps.setInt(1, currentUserId);
			
			try (ResultSet rs = schedulsps.executeQuery()) {
				while(rs.next()) {
					
					String courseName = rs.getString("course_name");
					String professorName = rs.getString("professor_name");
					String courseDay = rs.getString("day_of_week");
					String courseStart = rs.getString("start_time");
					String courseEnd = rs.getString("end_time");
					
					System.out.println("강의명" +"	|	"+"교수 이름"+"	|	"+"요일"+"	|	"+"시작 시간"+"	|	"+"종료시간");
					System.out.println(courseName +"	|	"+professorName+"	|	"+courseDay+"	|	"+courseStart+"	|	"+courseEnd);

				}
				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUserId;
		
		
		
	}  
	//학생 기본 정보 조회
	public void selectStudentInfo(int currentUserId) {
		
		
	}
	
	// 강의 등록
	public int registCourse(int currentUserId, Scanner sc) {

		
		//이거 무조건 고쳐라 시퀀스 들고와서 같은값으로 넣는거
		System.out.print("1.강의명 : ");
		String courseName = sc.nextLine();
		System.out.println("2. 진행할 강의 건물 : ");
		int departmentId = sc.nextInt();
		sc.nextLine();
		System.out.print("2.학기 : ");
		String semester = sc.nextLine();
		System.out.print("3.학점 : ");
		String credits = sc.nextLine();
		System.out.print("4.강의설명 : ");
		String coursePlan = sc.nextLine();
		System.out.println("5. 강의 요일 : ");
		String courseDay = sc.nextLine();
		System.out.println("6. 시작시간 : ");
		String courseStart = sc.nextLine();
		System.out.println("7. 종료시간 : ");
		String courseEnd = sc.nextLine();
		
		

		String insertCourseSql = "insert into courses values(seq_course_id.nextval,?,?,?,?,?,?)";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertCourseSql)) {
			pstmt.setString(1, courseName);
			pstmt.setInt(2, currentUserId);
			pstmt.setInt(3, departmentId);
			pstmt.setString(4, credits);
			pstmt.setString(5, semester);
			pstmt.setString(6, coursePlan);
			
			int result = pstmt.executeUpdate();

			if(result > 0) {
				System.out.println("등록 완료");
			}else {
				System.out.println("등록 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		registCourseTime(currentUserId,courseDay,courseStart,courseEnd);
		return currentUserId;
	}
	
		public int registCourseTime(int currentUserId, String courseDay, String courseStart, String courseEnd) {
			
		
		String insertScheduleSql = "INSERT INTO class_schedules VALUES(" +
			    "(SELECT MAX(course_id) FROM courses), " +
			    "seq_schedule_id.nextval, ?, ?, ?)";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertScheduleSql)) {
			pstmt.setString(1, courseDay);
			pstmt.setString(2, courseStart);
			pstmt.setString(3, courseEnd);
			
			int result = pstmt.executeUpdate();

			if(result > 0) {
				System.out.println("등록 완료");
			}else {
				System.out.println("등록 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUserId;
}

	
	//강의평가 조회
	public int printStudentRivew(int currentUserId) {
		
		String reviewSql = "SELECT c.name AS course_name, g.student_review "
				+ "FROM courses c, enrollments e, grades g "
				+ "WHERE c.professor_id = ? "
				+ "AND c.course_id = e.course_id "
				+ "AND e.enrollment_id = g.enrollment_id "
				+ "AND g.student_review IS NOT NULL ";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement reviewps = conn.prepareStatement(reviewSql)) {
			reviewps.setInt(1, currentUserId);
			
			try (ResultSet rs = reviewps.executeQuery()) {
				while(rs.next()) {
					System.out.println("rs 진입");
					String c_name = rs.getString("course_name");
					String studentRiview = rs.getString("student_review");
					
					System.out.println("강의명\t|\t학생리뷰");
					System.out.println(c_name +"	|	"+studentRiview);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUserId;
		
	}
	
	//교수 강의 현황 조회
	public int printCourseInfo(int currentUserId) {
		
		String selectSql = "SELECT name, department_id ,semester, credits ,courseplan  FROM courses WHERE professor_id = ?";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement selectps = conn.prepareStatement(selectSql)) {
			selectps.setInt(1, currentUserId);
			try (ResultSet rs = selectps.executeQuery()) {
				while (rs.next()) {

					String courseName = rs.getString("name");
					int departmentId = rs.getInt("department_id");
					String courseSemester = rs.getString("semester");
					String courseCredit = rs.getString("credits");
					String coursePlan = rs.getString("courseplan");
					
					System.out.println(courseName +"	|	"+departmentId+ "	|	" + courseSemester + "	|	" + courseCredit + "	|	" + coursePlan);

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUserId;
	}

	// 교수 강의 등록시 정보 출력
	public int printCourseInfoInRegistCourse(int currentUserId) {
		
		System.out.println("==강의 등록 메뉴==");
		System.out.println("강의명" +"	|	"+"강의 장소"+ "	|	" + "강의 학기" + "	|	" + "학점" + "	|	" + "강의 설명");
		String selectSql = "SELECT name, department_id ,semester, credits ,courseplan  FROM courses WHERE professor_id = ?";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement selectps = conn.prepareStatement(selectSql)) {
			selectps.setInt(1, currentUserId);
			try (ResultSet rs = selectps.executeQuery()) {
				while (rs.next()) {

					String courseName = rs.getString("name");
					int departmentId = rs.getInt("department_id");
					String courseSemester = rs.getString("semester");
					String courseCredit = rs.getString("credits");
					String coursePlan = rs.getString("courseplan");

					System.out.println(courseName +"	|	"+departmentId+"관"+ "	|	" + courseSemester + "	|	" + courseCredit + "	|	" + coursePlan);

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUserId;
	}
	
	// 교수 정보 변경
	public int modifyProfessorInfo(String typeInfo, String modifyInfo, int currentUserId, Scanner sc) {

		String modifyProfessorInfoSql = "UPDATE professors SET " + typeInfo + " = ? WHERE professor_id = ?";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement ps = conn.prepareStatement(modifyProfessorInfoSql)) {
			ps.setString(1, modifyInfo);
			ps.setInt(2, currentUserId);

			int resultOutput = ps.executeUpdate();
			if (resultOutput > 0) {
				System.out.println("수정이 완료되었습니다.");
				String selectSql = "SELECT name, phone, email, password FROM professors WHERE professor_id = ?";
				try (PreparedStatement selectps = conn.prepareStatement(selectSql)) {
					selectps.setInt(1, currentUserId);
					try (ResultSet rs = selectps.executeQuery()) {
						if (rs.next()) {

							String professorName = rs.getString("name");
							String professorPhone = rs.getString("phone");
							String professorEmail = rs.getString("email");
							String professorPw = rs.getString("password");

							System.out.println(professorName + " | " + professorPhone + " | " + professorEmail + " | "
									+ professorPw);

						}

					}

				}
			} else {
				System.out.println("수정에 실패했습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUserId;
	}

	// Professors 테이블에서 Professor 객체 반환
	public Professor findProfessorByNameAndBirthDateAndEmail(String targetName, LocalDate targetBirthDate,
			String targetEmail) {
		String sql = "SELECT * FROM Professors WHERE name = ? and birth_date = ? and email = ?";
		Professor Professor = null;

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, targetName);
			pstmt.setDate(2, Date.valueOf(targetBirthDate));
			pstmt.setString(3, targetEmail);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {

					int ProfessorId = rs.getInt("Professor_id");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
					String email = rs.getString("email");
					String password = rs.getString("password");
					int departmentId = rs.getInt("department_id");
					LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
					Professor = new Professor(ProfessorId, name, phone, birthDate, email, password, departmentId,
							hireDate);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Professor;
	}

	public boolean updateProfessor(Professor professor) {
		String sql = "UPDATE professors SET name = ?, phone = ?, birth_date = ?, email = ?, password = ?,"
				+ " department_id = ?, hire_date = ? WHERE professor_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, professor.getName());
			pstmt.setString(2, professor.getPhone());
			pstmt.setDate(3, Date.valueOf(professor.getBirthDate()));
			pstmt.setString(4, professor.getEmail());
			pstmt.setString(5, professor.getPassword());
			pstmt.setInt(6, professor.getDepartmentId());
			pstmt.setDate(7, Date.valueOf(professor.getBirthDate()));
			pstmt.setInt(8, professor.getProfessorId());

			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0; // 업데이트된 행이 있는 경우 true 반환
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
