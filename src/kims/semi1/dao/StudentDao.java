package kims.semi1.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Student;

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

}
