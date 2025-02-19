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
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Department;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;
import kims.semi1.model.Unit;

public class ClassScheduleDao {

	public ClassScheduleDao() {

	}

	public List<CourseInfo> findCourseInfos() {
		String sql = "select *  from (((((courses c inner join professors p on c.professor_id = p.professor_id) \r\n"
				+ "   inner join departments d on c.department_id = d.department_id) \r\n"
				+ "   left outer join class_schedules s on c.course_id = s.course_id) \r\n"
				+ "   inner join buildings b on d.building_id = b.building_id) "
				+ "   left outer join units u on s.unit = u.unit)";
		List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
					courseInfo.add(new CourseInfo(c, d, s, p, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfo;
	}
	
	public List<CourseInfo> findClassRoomInfos(String unitId) {
		String sql = "select * " + "FROM courses c,professors p,departments d,class_schedules s, buildings b,units u "
				+ " WHERE s.course_id = c.course_id " + "AND p.professor_id = c.professor_id "
				+ "AND c.department_id = d.department_id " + "AND d.building_id = b.building_id " + "AND s.unit = u.unit "
				+ "AND u.unit like ? ";
		List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, unitId);
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
					courseInfo.add(new CourseInfo(c, d, s, p, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfo;
	}

	public List<Student> findStudentInfos() {
		String sql = "SELECT * FROM students ";
		List<Student> studnetInfo = new ArrayList<Student>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Student s = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
					studnetInfo.add(s);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studnetInfo;
	}
	public List<Professor> findProfessorInfos() {
		String sql = "SELECT * FROM professors ";
		List<Professor> professorInfo = new ArrayList<Professor>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Professor p = new Professor(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getInt(7),
							rs.getDate(8).toLocalDate());
					professorInfo.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return professorInfo;
	}
	
	public List<CourseInfo> findCousrseProfessorInfos() {
		String sql = "SELECT * FROM courses c,professors p,departments d where p.professor_id = c.professor_id and p.department_id = d.department_id ";
		List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Course c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getString(7));
					Professor p = new Professor(rs.getInt(8), rs.getString(9), rs.getString(10),
							rs.getDate(11).toLocalDate(), rs.getString(12), rs.getString(13), rs.getInt(14),
							rs.getDate(15).toLocalDate());
					Department d = new Department(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getInt(19));
					courseInfo.add(new CourseInfo(c, d, null, p));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfo;
	}
	public List<CourseInfo> findCousrseProfessorInfos(String courseName) {
		String sql = "SELECT * FROM courses c,professors p,departments d where p.professor_id = c.professor_id and p.department_id = d.department_id and c.name like ?";
		List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, courseName);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Course c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getString(7));
					Professor p = new Professor(rs.getInt(8), rs.getString(9), rs.getString(10),
							rs.getDate(11).toLocalDate(), rs.getString(12), rs.getString(13), rs.getInt(14),
							rs.getDate(15).toLocalDate());
					Department d = new Department(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getInt(19));
					courseInfo.add(new CourseInfo(c, d, null, p));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfo;
	}
	
	public List<CourseInfo> findCousrseProfessorNameInfos(String professorName) {
		String sql = "SELECT * FROM courses c,professors p,departments d where p.professor_id = c.professor_id and p.department_id = d.department_id and p.name like ? ";
		List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, professorName);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Course c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getString(7));
					Professor p = new Professor(rs.getInt(8), rs.getString(9), rs.getString(10),
							rs.getDate(11).toLocalDate(), rs.getString(12), rs.getString(13), rs.getInt(14),
							rs.getDate(15).toLocalDate());
					Department d = new Department(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getInt(19));
					courseInfo.add(new CourseInfo(c, d, null, p));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfo;
	}
	
	public List<CourseInfo> findUnitBuildingInfos() {
		String sql = "SELECT * FROM buildings b,units u where u.building_id = b.building_id ";
		List<CourseInfo> unitBuildingInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Building b = new Building(rs.getInt(1), rs.getString(2));
					Unit u = new Unit(rs.getString(3), rs.getInt(4));
					unitBuildingInfo.add(new CourseInfo(null, null, null, null, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unitBuildingInfo;
	}
	
	public List<CourseInfo> findCousrseProfessorInfos(String courseName,String professorName) {
		String sql = "SELECT * FROM courses c,professors p,departments d where p.professor_id = c.professor_id and p.department_id = d.department_id and c.name like ? and p.name like ? ";
		List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, courseName);
			pstmt.setString(2, professorName);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Course c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getString(6), rs.getString(7));
					Professor p = new Professor(rs.getInt(8), rs.getString(9), rs.getString(10),
							rs.getDate(11).toLocalDate(), rs.getString(12), rs.getString(13), rs.getInt(14),
							rs.getDate(15).toLocalDate());
					Department d = new Department(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getInt(19));
					courseInfo.add(new CourseInfo(c, d, null, p));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseInfo;
	}

	public List<CourseInfo> findUnitInfos(String unit) {
		String sql = "SELECT * FROM buildings b,units u where u.building_id = b.building_id and unit like ?";
		List<CourseInfo> unitBuildingInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, unit);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Building b = new Building(rs.getInt(1), rs.getString(2));
					Unit u = new Unit(rs.getString(3), rs.getInt(4));
					unitBuildingInfo.add(new CourseInfo(null, null, null, null, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unitBuildingInfo;
	}
	
	public List<CourseInfo> findBuildingInfos(int buildingId) {
		String sql = "select * FROM buildings b,units u where u.building_id = b.building_id and b.building_id = ?  ";
		List<CourseInfo> unitBuildingInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, buildingId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Building b = new Building(rs.getInt(1), rs.getString(2));
					Unit u = new Unit(rs.getString(3), rs.getInt(4));
					unitBuildingInfo.add(new CourseInfo(null, null, null, null, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unitBuildingInfo;
	}
	public List<CourseInfo> findUnitBuildingInfos(int buildingId , String unit) {
		String sql = " select * FROM buildings b,units u where u.building_id = b.building_id and unit = ? and b.building_id = ?   ";
		List<CourseInfo> unitBuildingInfo = new ArrayList<CourseInfo>();
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1,buildingId+"-"+ unit);
			pstmt.setInt(2, buildingId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Building b = new Building(rs.getInt(1), rs.getString(2));
					Unit u = new Unit(rs.getString(3), rs.getInt(4));
					unitBuildingInfo.add(new CourseInfo(null, null, null, null, b, u));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unitBuildingInfo;
	}

	public boolean insertClassSchedule(ClassSchedule classSchedule) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO " + "class_schedules (schedule_id,course_id,day_of_week,start_time,end_time,unit)"
				+ "VALUES (seq_class_schedules_num.nextval, ?, ?, ?, ?,?)";
		try {
			conn = DBConnector.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classSchedule.getCourseId());
			pstmt.setString(2, classSchedule.getDayOfWeek());
			pstmt.setString(3, classSchedule.getStartTime());
			pstmt.setString(4, classSchedule.getEndTime());
			pstmt.setString(5, classSchedule.getUnit());

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
