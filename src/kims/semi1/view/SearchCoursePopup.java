package kims.semi1.view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCoursePopup extends Dialog implements ActionListener {
	private TextField txtCourseID, txtCourseName, txtDepartmentID;
	private Button btnSearch, btnCancel;
	private ProfessorFrame parentFrame; // 부모 프레임 참조
	private List courseList; // courseList에 접근하기 위한 필드

	public SearchCoursePopup(ProfessorFrame parentFrame, String title, boolean modal) {
		super(parentFrame, title, modal);
		this.parentFrame = parentFrame;
		this.courseList = parentFrame.courseList; // 부모 프레임의 courseList 참조

		setSize(400, 200);
		setLayout(new GridLayout(4, 2));

		Label lblCourseID = new Label("강의 ID:");
		txtCourseID = new TextField(5);
		Label lblCourseName = new Label("강의명:");
		txtCourseName = new TextField(15);
		Label lblDepartmentID = new Label("건물 이름:");
		txtDepartmentID = new TextField();

		btnSearch = new Button("검색");
		btnCancel = new Button("취소");

		add(lblCourseID);
		add(txtCourseID);
		add(lblCourseName);
		add(txtCourseName);
		add(lblDepartmentID);
		add(txtDepartmentID);
		add(btnSearch);
		add(btnCancel);

		btnSearch.addActionListener(this);
		btnCancel.addActionListener(this);

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

		setVisible(false); // 초기에는 보이지 않도록 설정
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			// 검색 버튼 클릭 시 동작
			String courseID = txtCourseID.getText();
			String courseName = txtCourseName.getText();
			String departmentID = txtDepartmentID.getText();

			// 검색 로직 구현 (이 부분은 데이터베이스 연동 및 검색 쿼리 필요)
			performSearch(courseID, courseName, departmentID);

			dispose(); // 팝업 닫기
		} else if (e.getSource() == btnCancel) {
			// 취소 버튼 클릭 시 동작
			dispose(); // 팝업 닫기
		}
	}

	private void performSearch(String courseID, String courseName, String departmentID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(ProfessorFrame.JDBC_DRIVER);
			conn = DriverManager.getConnection(ProfessorFrame.DB_URL, ProfessorFrame.USER, ProfessorFrame.PASS);

			// SQL 쿼리 생성 (검색 조건에 따라 동적으로 쿼리 생성)
			StringBuilder sql = new StringBuilder(
					"SELECT c.course_id, c.name, c.credits, c.syllabus, c.department_id, c.semester FROM courses c WHERE c.professor_id = ?");

			// 강의 ID 조건 추가
			if (courseID != null && !courseID.isEmpty()) {
				sql.append(" AND c.course_id LIKE ?");
			}

			// 강의 이름 조건 추가
			if (courseName != null && !courseName.isEmpty()) {
				sql.append(" AND c.name LIKE ?");
			}

			// 학과 ID 조건 추가
			if (departmentID != null && !departmentID.isEmpty()) {
				sql.append(" AND c.department_id = ?");
			}

			pstmt = conn.prepareStatement(sql.toString());

			int parameterIndex = 1;
			pstmt.setInt(parameterIndex++, parentFrame.getProfessorId()); // 교수 ID 설정

			// 강의 ID 조건 추가
			if (courseID != null && !courseID.isEmpty()) {
				pstmt.setString(parameterIndex++, "%" + courseID + "%");
			}

			// 강의 이름 조건 추가
			if (courseName != null && !courseName.isEmpty()) {
				pstmt.setString(parameterIndex++, "%" + courseName + "%");
			}

			// 학과 ID 조건 추가
			if (departmentID != null && !departmentID.isEmpty()) {
				pstmt.setString(parameterIndex++, departmentID);
			}

			rs = pstmt.executeQuery();

			// 기존 리스트 초기화
			courseList.removeAll();

			while (rs.next()) {
				String courseId = rs.getString("course_id");
				String cName = rs.getString("name");
				String credits = rs.getString("credits");
				String syllabus = rs.getString("syllabus");
				String building = rs.getString("department_id");
				String semester = rs.getString("semester");

				// Syllabus 내용이 너무 길어지면 ...으로 줄여서 표시
				if (syllabus.length() > 27) {
					syllabus = syllabus.substring(0, 27) + "...";
				}

				// 폭을 맞추기 위해 String.format() 사용
				String courseInfo = String.format("%-4s | %-18s | %-4s | %-6s | %-4s | %-32s", courseId, cName, credits,
						building, semester, syllabus);

				courseList.add(courseInfo);
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

	public void setVisible(boolean b) {
		super.setVisible(b);
	}
}