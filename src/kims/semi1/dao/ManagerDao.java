package kims.semi1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Manager;

public class ManagerDao {

	// manager_id 로 검색해서 하나라도 있으면 true 없으면 false 를 반환
	public boolean existsManagerId(int managerId) {
		String sql = "SELECT COUNT(*) FROM managers WHERE manager_id = ?";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, managerId);

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
		// rs.next가 없으면 아이디가 없는 것이므로 false 반환
		System.out.println("resultset in ManagerDao.existsManagerId() is empty");
		return false;
	}

	// Manager 객체 managers 테이블에 저장
	public void insertManager(Manager manager) {
		String sql = "INSERT INTO managers (manager_id, password) VALUES (?, ?)";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, manager.getManagerId());
			pstmt.setString(2, manager.getPassword());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// manager_id 로 검색해서 managers 테이블에서 Manager 객체 반환
	public Manager getManagerById(int managerId) {
		String sql = "SELECT * FROM managers WHERE manager_id = ?";
		Manager manager = null;

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, managerId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					String password = rs.getString("password");
					manager = new Manager(managerId, password);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manager;
	}
}
