package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class StudentListPopup extends Dialog {
	private List studentList;
	private String courseId;

	public StudentListPopup(Frame parent, String title, boolean modal, String courseId) {
		super(parent, title, modal);
		this.courseId = courseId;

		setSize(400, 300);
		setLayout(new BorderLayout());

		// 학생 리스트 생성
		studentList = new List();
		studentList.setFont(new Font("Monospaced", Font.PLAIN, 14));
		add(new Label("수강 학생 목록:"), BorderLayout.NORTH);
		add(studentList, BorderLayout.CENTER);

		// 스크롤 기능 추가
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(studentList);
		add(scrollPane, BorderLayout.CENTER);

		loadStudentListData(); // 학생 목록 데이터 로딩

		// 팝업 창 닫기
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// 프레임 가운데 배치
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	// 학생 목록 데이터 로딩 메서드
	private void loadStudentListData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(ProfessorFrame.JDBC_DRIVER);
			conn = DriverManager.getConnection(ProfessorFrame.DB_URL, ProfessorFrame.USER, ProfessorFrame.PASS);

			// SQL 쿼리 실행 (해당 강의를 수강하는 학생 정보 조회)
			String sql = "SELECT s.student_id, s.name, s.phone, s.email " + "FROM students s "
					+ "JOIN enrollments e ON s.student_id = e.student_id " + "WHERE e.course_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseId);
			rs = pstmt.executeQuery();

			// 기존 내용 지우기
			studentList.removeAll();

			while (rs.next()) {
				String studentId = rs.getString("student_id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");

				// 학생 정보 문자열 생성
				String studentInfo = String.format("%s | %s | %s | %s", studentId, name, phone, email);

				// List에 추가
				studentList.add(studentInfo);
			}

		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}