package kims.semi1.view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kims.semi1.config.DBConnector;

public class CourseModifyPopup extends Dialog implements ActionListener {
	private TextField txtCourseName, txtCourseBuilding, txtCourseSemester, txtCourseCredit, txtCourseDescription;
	private Button btnSave, btnCancel;
	private ProfessorFrame parentFrame;
	private String courseId, originalName, originalCredit, originalBuilding, originalSemester, originalDescription;

	public CourseModifyPopup(ProfessorFrame parentFrame, String courseId, String name, String credits, String building,
			String semester, String syllabus) {
		super(parentFrame, "강의 수정", true);
		this.parentFrame = parentFrame;
		this.courseId = courseId;
		this.originalName = name;
		this.originalCredit = credits;
		this.originalBuilding = building;
		this.originalSemester = semester;
		this.originalDescription = syllabus;

		setSize(400, 300);
		setLayout(new GridLayout(6, 2));

		Label lblCourseName = new Label("강의명:");
		txtCourseName = new TextField(20);
		txtCourseName.setText(name);
		add(lblCourseName);
		add(txtCourseName);

		Label lblCourseBuilding = new Label("강의건물:");
		txtCourseBuilding = new TextField(20);
		txtCourseBuilding.setText(building);
		add(lblCourseBuilding);
		add(txtCourseBuilding);

		Label lblCourseSemester = new Label("학기:");
		txtCourseSemester = new TextField(20);
		txtCourseSemester.setText(semester);
		add(lblCourseSemester);
		add(txtCourseSemester);

		Label lblCourseCredit = new Label("학점:");
		txtCourseCredit = new TextField(20);
		txtCourseCredit.setText(credits);
		add(lblCourseCredit);
		add(txtCourseCredit);

		Label lblCourseDescription = new Label("강의설명:");
		txtCourseDescription = new TextField(20);
		txtCourseDescription.setText(syllabus);
		add(lblCourseDescription);
		add(txtCourseDescription);

		btnSave = new Button("저장");
		btnSave.addActionListener(this);
		btnCancel = new Button("취소");
		btnCancel.addActionListener(this);
		add(btnSave);
		add(btnCancel);

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
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave) {
			// 1. 팝업 창에서 입력된 값 가져오기
			String courseName = txtCourseName.getText().trim();
			String courseBuilding = txtCourseBuilding.getText().trim();
			String courseSemester = txtCourseSemester.getText().trim();
			String courseCredit = txtCourseCredit.getText().trim();
			String courseDescription = txtCourseDescription.getText().trim();

			// 2. 값이 비어있을 경우 기존 값 유지
			if (courseName.isEmpty()) {
				courseName = originalName;
			}
			if (courseBuilding.isEmpty()) {
				courseBuilding = originalBuilding;
			}
			if (courseSemester.isEmpty()) {
				courseSemester = originalSemester;
			}
			if (courseCredit.isEmpty()) {
				courseCredit = originalCredit;
			}
			if (courseDescription.isEmpty()) {
				courseDescription = originalDescription;
			}

			// 3. 데이터베이스 업데이트
			updateCourse(courseId, courseName, courseCredit, courseDescription, courseBuilding, courseSemester);

			// 4. 팝업 창 닫기
			dispose();
		} else if (e.getSource() == btnCancel) {
			dispose();
		}
	}

	private void updateCourse(String courseId, String courseName, String courseCredit, String courseDescription,
			String courseBuilding, String courseSemester) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnector.getConnection();

			// SQL 쿼리 수정: WHERE 절을 course_id 기반으로 변경
			String sql = "UPDATE courses SET name = ?, credits = ?, syllabus = ?, department_id = ?, semester = ? WHERE course_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseName);
			pstmt.setString(2, courseCredit);
			pstmt.setString(3, courseDescription);
			pstmt.setString(4, courseBuilding); // 강의 건물 정보를 department_id에 넣음
			pstmt.setString(5, courseSemester);
			pstmt.setString(6, courseId); // 선택된 courseId 사용

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("강의가 성공적으로 수정되었습니다.");
				parentFrame.loadCourse(); // ProfessorFrame의 loadCourse() 호출하여 리스트 갱신
			} else {
				System.out.println("강의 수정에 실패했습니다.");
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnector.close(conn, pstmt);
		}
	}
}