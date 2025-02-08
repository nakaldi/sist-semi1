package kims.semi1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kims.semi1.config.DBConnector;
import kims.semi1.model.ClassSchedule;

public class ClassScheduleDao {

	public ClassScheduleDao() {

	}

	public boolean insertClassSchedule(ClassSchedule classSchedule) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO " + "class_schedules (schedule_id,course_id,day_of_week,start_time,end_time)"
				+ "VALUES (seq_class_schedules_num.nextval, ?, ?, ?, ?)";
		try {
			conn = DBConnector.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classSchedule.getCourseId());
			pstmt.setString(2, classSchedule.getDayOfWeek());
			pstmt.setString(3, classSchedule.getStartTime());
			pstmt.setString(4, classSchedule.getEndTime());

			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnector.close(conn, pstmt);
		}
	}

}
