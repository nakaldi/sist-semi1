package kims.semi1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kims.semi1.config.DBConnector;
import kims.semi1.model.Building;
import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Course;
import kims.semi1.model.Department;
import kims.semi1.model.Enrollment;
import kims.semi1.model.Grade;
import kims.semi1.model.Manager;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;

public class GenericDao {

	/**
	 * targetModel 테이블에서 targetColumn = targetString 을 검색해서 List<> 반환 동적으로 테이블과 컬럼,
	 * 타겟을 지정해서 결과를 반환할 수 있음.
	 * 
	 * @return {@code List<targetModel>} if the results exist; {@code null} if the
	 *         result does not exists.
	 */	
	public <T> List<T> findModels(Class<T> targetModel, String targetColumn, String targetString) {

		String sql = "SELECT * FROM " + targetModel.getSimpleName() + "s WHERE " + targetColumn + " = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, targetString);
			try (ResultSet rs = pstmt.executeQuery()) {
				return convertResultSetToList(targetModel, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> List<T> findModels(Class<T> targetModel, String targetColumn, int targetInt) {

		String sql = "SELECT * FROM " + targetModel.getSimpleName() + "s WHERE " + targetColumn + " = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, targetInt);
			try (ResultSet rs = pstmt.executeQuery()) {
				return convertResultSetToList(targetModel, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> T findModel(Class<T> targetModel, String targetColumn, int targetInt) {
		return findModels(targetModel, targetColumn, targetInt).get(0);
	}

	public <T> T findModel(Class<T> targetModel, String targetColumn, String targetString) {
		return findModels(targetModel, targetColumn, targetString).get(0);
	}

	/**
	 * ResultSet 결과를 List<targetModel> 형식으로 반환함.
	 */
	private <T> List<T> convertResultSetToList(Class<T> targetModel, ResultSet rs) throws SQLException {
		List<T> models = new ArrayList<>();
		// ResultSet에 자료가 하나도 없는 경우 null 반환
		if (rs.isBeforeFirst() == false) {
			try {
				models.add(targetModel.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return models;
		}
		if (targetModel == Building.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new Building(rs.getInt(1), rs.getString(2))));
			}
		} else if (targetModel == ClassSchedule.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new ClassSchedule(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5))));
			}
		} else if (targetModel == Course.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getString(7))));
			}
		} else if (targetModel == Department.class) {
			while (rs.next()) {
				models.add(
						targetModel.cast(new Department(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4))));
			}
		} else if (targetModel == Enrollment.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new Enrollment(rs.getInt(1), rs.getInt(2), rs.getInt(3))));
			}
		} else if (targetModel == Grade.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new Grade(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4))));
			}
		} else if (targetModel == Manager.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new Manager(rs.getInt(1), rs.getString(2))));
			}
		} else if (targetModel == Professor.class) {
			while (rs.next()) {
				models.add(targetModel
						.cast(new Professor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(),
								rs.getString(5), rs.getString(6), rs.getInt(7), rs.getDate(8).toLocalDate())));
			}
		} else if (targetModel == Student.class) {
			while (rs.next()) {
				models.add(targetModel.cast(new Student(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8))));
			}
		} else {
			System.out.println("타겟 클래스와 매칭되는 모델이 없습니다.");
			return null;
		}
		return models;
	}

}
