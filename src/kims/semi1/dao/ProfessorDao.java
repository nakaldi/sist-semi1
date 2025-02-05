package kims.semi1.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Professor;

public class ProfessorDao {

	// professor_id 로 검색해서 하나라도 있으면 true 없으면 false 를 반환
	public boolean isProfessorIdExist(int professorId) {
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
	public void insertUser(Professor professor) {
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
}
