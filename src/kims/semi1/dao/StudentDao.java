package kims.semi1.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Building;
import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Course;
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Department;
import kims.semi1.model.Enrollment;
import kims.semi1.model.Grade;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;
import kims.semi1.model.Unit;

public class StudentDao {

	// student_id 로 검색해서 하나라도 있으면 true 없으면 false 를 반환
	public boolean existsStudentId(int studentId) {
		String sql = "SELECT COUNT(*) FROM students WHERE student_id = ?";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, studentId);

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
		System.out.println("resultset in StudentDao.existsStudentId() is empty");
		return false;
	}

	// Student 객체 students 테이블에 저장
	public void insertStudent(Student student) {
		String sql = "INSERT INTO "
				+ "students (student_id, name, phone, birth_date, email, password, department_id, enrollment_year) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, student.getStudentId());
			pstmt.setString(2, student.getName());
			pstmt.setString(3, student.getPhone());
			pstmt.setDate(4, Date.valueOf(student.getBirthDate()));
			pstmt.setString(5, student.getEmail());
			pstmt.setString(6, student.getPassword());
			pstmt.setInt(7, student.getDepartmentId());
			pstmt.setInt(8, student.getEnrollmentYear());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 아이디로 검색해서 students 테이블에서 Student 객체 반환
	public Student getStudentById(int studentId) {
		String sql = "SELECT * FROM students WHERE student_id = ?";
		Student student = null;

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, studentId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {

					String name = rs.getString("name");
					String phone = rs.getString("phone");
					LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
					String email = rs.getString("email");
					String password = rs.getString("password");
					int departmentId = rs.getInt("department_id");
					int enrollmentYear = rs.getInt("enrollment_year");
					student = new Student(studentId, name, phone, birthDate, email, password, departmentId,
							enrollmentYear);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	// students 테이블에서 Student 객체 반환
	public Student findStudentByNameAndBirthDateAndEmail(String targetName, LocalDate targetBirthDate,
			String targetEmail) {
		String sql = "SELECT * FROM students WHERE name = ? and birth_date = ? and email = ?";
		Student student = null;

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, targetName);
			pstmt.setDate(2, Date.valueOf(targetBirthDate));
			pstmt.setString(3, targetEmail);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {

					int studentId = rs.getInt("student_id");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
					String email = rs.getString("email");
					String password = rs.getString("password");
					int departmentId = rs.getInt("department_id");
					int enrollmentYear = rs.getInt("enrollment_year");
					student = new Student(studentId, name, phone, birthDate, email, password, departmentId,
							enrollmentYear);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	// 학생 정보를 업데이트하는 메서드
	public boolean updateStudent(Student student) {
		String sql = "UPDATE students SET name = ?, phone = ?, birth_date = ?, email = ?, password = ?,"
				+ " department_id = ?, enrollment_year = ? WHERE student_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getPhone());
			pstmt.setDate(3, Date.valueOf(student.getBirthDate()));
			pstmt.setString(4, student.getEmail());
			pstmt.setString(5, student.getPassword());
			pstmt.setInt(6, student.getDepartmentId());
			pstmt.setInt(7, student.getEnrollmentYear());
			pstmt.setInt(8, student.getStudentId());

			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0; // 업데이트된 행이 있는 경우 true 반환
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<CourseInfo> findCourseInfosBySemester(String semester) {
		String sql = "select *  from (((((courses c inner join professors p on c.professor_id = p.professor_id) \r\n"
				+ "   inner join departments d on c.department_id = d.department_id) \r\n"
				+ "   left outer join class_schedules s on c.course_id = s.course_id) \r\n"
				+ "   inner join buildings b on d.building_id = b.building_id) "
				+ "   left outer join units u on s.unit = u.unit)" + "   where c.semester = ?";

		List<CourseInfo> courseInfos = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Course c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getString(7));
					Professor p = new Professor(rs.getInt(8), rs.getString(9), rs.getString(10),
							rs.getDate(11).toLocalDate(), rs.getString(12), rs.getString(13), rs.getInt(14),
							rs.getDate(15).toLocalDate());
					Department d = new Department(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getInt(19));
					ClassSchedule s = new ClassSchedule(rs.getInt(20), rs.getInt(21), rs.getString(22),
							rs.getString(23), rs.getString(24), rs.getString(25));
					Building b = new Building(rs.getInt(26), rs.getString(27));
					Unit u = new Unit(rs.getString(28), rs.getInt(29));
					courseInfos.add(new CourseInfo(c, d, s, p, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfos;
	}

	public List<Enrollment> findEnrollmentInfosByStudentIdAndSemester(int studentId, String semester) {
		String sql = "select c.course_id, c.name as course_name, c.professor_id, c.department_id, c.credits, c.semester, c.syllabus, "
				+ "p.professor_id, p.name as professor_name, p.phone as professor_phone, p.birth_date, p.email, p.password, p.department_id as professor_department_id, p.hire_date, "
				+ "d.department_id, d.name as department_name, d.phone as department_phone, d.building_id, "
				+ "s.schedule_id, s.course_id as schedule_course_id, s.day_of_week, s.start_time, s.end_time, s.unit as schedule_unit, "
				+ "b.building_id, b.name as building_name, " + "u.unit, u.building_id as unit_building_id, "
				+ "g.grade_id, g.enrollment_id, g.grade, g.student_review, "
				+ "e.enrollment_id, e.student_id, e.course_id as enrollment_course_id "
				+ "from (((((((courses c inner join enrollments e on c.course_id = e.course_id) "
				+ "inner join professors p on c.professor_id = p.professor_id) "
				+ "inner join departments d on c.department_id = d.department_id) "
				+ "left outer join class_schedules s on c.course_id = s.course_id) "
				+ "left outer join buildings b on d.building_id = b.building_id) "
				+ "left outer join grades g on e.enrollment_id = g.enrollment_id) "
				+ "left outer join units u on u.unit = s.unit) " + "where e.student_id = ? and c.semester = ?";

		List<Enrollment> enrollmentInfos = new ArrayList<Enrollment>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, studentId);
			pstmt.setString(2, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Course c = new Course(rs.getInt("course_id"), rs.getString("course_name"),
							rs.getInt("professor_id"), rs.getInt("department_id"), rs.getString("credits"),
							rs.getString("semester"), rs.getString("syllabus"));
					Professor p = new Professor(rs.getInt("professor_id"), rs.getString("professor_name"),
							rs.getString("professor_phone"), rs.getDate("birth_date").toLocalDate(),
							rs.getString("email"), rs.getString("password"), rs.getInt("professor_department_id"),
							rs.getDate("hire_date").toLocalDate());
					Department d = new Department(rs.getInt("department_id"), rs.getString("department_name"),
							rs.getString("department_phone"), rs.getInt("building_id"));
					ClassSchedule s = new ClassSchedule(rs.getInt("schedule_id"), rs.getInt("schedule_course_id"),
							rs.getString("day_of_week"), rs.getString("start_time"), rs.getString("end_time"),
							rs.getString("schedule_unit"));
					Building b = new Building(rs.getInt("building_id"), rs.getString("building_name"));
					Unit u = new Unit(rs.getString("unit"), rs.getInt("unit_building_id"));
					Grade g = new Grade(rs.getInt("grade_id"), rs.getInt("enrollment_id"), rs.getDouble("grade"),
							rs.getString("student_review"));
					CourseInfo courseInfo = new CourseInfo(c, d, s, p, b, u);
					enrollmentInfos.add(new Enrollment(rs.getInt("enrollment_id"), rs.getInt("student_id"),
							rs.getInt("enrollment_course_id"), courseInfo, g));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrollmentInfos;
	}

	// Enrollments 테이블에 저장
	public boolean insultEnrollment(int studentId, int courseId) {
		String sql = "INSERT INTO " + "enrollments (enrollment_id, student_id, course_id)"
				+ "VALUES (seq_enrollments_num.nextval, ?, ?)";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, studentId);
			pstmt.setInt(2, courseId);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean existsEnrollmentByStudentIdAndCourseId(int studentId, int courseId) {
		String sql = "select * from enrollments where student_id = ? AND course_id = ?";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, studentId);
			pstmt.setInt(2, courseId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteEnrollmentByStudentIdAndCourseId(int studentId, int courseId) {
		String sql = "delete from enrollments where student_id = ? AND course_id = ?";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, studentId);
			pstmt.setInt(2, courseId);
			
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateReviewFromGrade(Grade grade) {
		String sql = "UPDATE grades SET enrollment_id = ?, grade = ?, student_review = ? WHERE grade_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, grade.getEnrollmentId());
			pstmt.setDouble(2, grade.getGrade());
			pstmt.setString(3, grade.getStudentReview());
			pstmt.setInt(4, grade.getGradeId());

			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0; // 업데이트된 행이 있는 경우 true 반환
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
