package kims.semi1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Department;

public class DepartmentDao {

	// 아이디로 검색해서 departments 테이블에서 Department 객체 반환
	public Department getDepartmentById(int departmentId) {
		String sql = "SELECT * FROM departments WHERE department_id = ?";
		Department department = null;

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, departmentId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {

					String name = rs.getString("name");
					String phone = rs.getString("phone");
					int buildingId = rs.getInt("building_id");
					department = new Department(departmentId, name, phone, buildingId);
				} else {
					System.out.println("Department does not exist");
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return department;
	}

}
