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

	public ProfessorDao() {

	}

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
}
