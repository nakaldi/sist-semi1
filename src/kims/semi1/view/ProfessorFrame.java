package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
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

public class ProfessorFrame extends Frame {

	private CardLayout cardLayout;
	private Panel mainPanel;
	private Panel myPagePanel, sugangPanel, gradePanel;
	private Button btnLogout;
	private LoginFrame loginFrame;
	private Panel myPageViewPanel;
	TextField txtName, txtBirth, txtContact, txtMajor, txtEmail, txtHireDate, txtPw;
	private Button btnInfoEdit, btnSave, btnCancel;
	private Button btnMyPage, btnSugang, btnGrade;

	// 수강 관리
	private TextField txtCourseName, txtCourseBuilding, txtCourseCredit, txtCourseDescription, txtCourseSemester;
	private Button btnRegister, btnModify, btnSearch, btnDelete, btnSearchAll;
	List courseList;

	// 성적 관리
	private TextField txtCourseIDGrade, txtStudentName, txtStudentGrade;
	private Button btnGradeModify, btnGradeSearch, btnGradeSearchAll, btnGradeRegister, btnStudentModify;
	private List gradeList;

	// DB 연결 정보
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver"; // JDBC 드라이버 클래스
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE"; // DB URL
	static final String USER = "hamster2"; // DB 계정
	static final String PASS = "1234"; // DB 비밀번호
	private int professorId;

	private String selectedCourseId;
	private ActionListener registerActionListener;
	
	

	public ProfessorFrame(LoginFrame loginFrame, int professorId) {
		this.loginFrame = loginFrame;
		this.professorId = professorId;

		setTitle("학사관리시스템 (교수)");
		setSize(1200, 800);
		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		cardLayout = new CardLayout();
		mainPanel = new Panel(cardLayout);

		Panel menuPanel = createMenuPanel();
		add(menuPanel, BorderLayout.NORTH);

		myPagePanel = createMyPagePanel();
		sugangPanel = createSugangPanel();
		gradePanel = createGradePanel();

		mainPanel.add(myPagePanel, "myPage");
		mainPanel.add(sugangPanel, "sugang");
		mainPanel.add(gradePanel, "grade");

		add(mainPanel, BorderLayout.CENTER);
		loadProfessorInfo(); // 교수 정보 로딩

		setVisible(true);
	}


	// 교수 정보 로딩 메서드
	private void loadProfessorInfo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "SELECT name, birth_date, phone, department_id, email, hire_date FROM professors WHERE professor_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				txtName.setText(rs.getString("name"));
				txtBirth.setText(rs.getString("birth_date"));
				txtContact.setText(rs.getString("phone"));
				txtEmail.setText(rs.getString("email"));
				txtHireDate.setText(rs.getString("hire_date"));

				// 학과 정보는 department_id를 이용하여 departments 테이블에서 조회해야 함
				int departmentId = rs.getInt("department_id");
				String major = getDepartmentName(departmentId);
				txtMajor.setText(major);
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

	private void addCourse() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. DB 연결
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 2. 텍스트 필드에서 입력된 값 가져오기
			String courseName = txtCourseName.getText();
			String courseCredit = txtCourseCredit.getText();
			String courseDescription = txtCourseDescription.getText();
			String courseBuilding = txtCourseBuilding.getText();
			String courseSemester = txtCourseSemester.getText();

			// 입력값 검증
			if (courseName.isEmpty() || courseCredit.isEmpty() || courseDescription.isEmpty()
					|| courseBuilding.isEmpty() || courseSemester.isEmpty()) {
				System.out.println("모든 필드를 채워주세요.");
				return;
			}

			String sql = "INSERT INTO courses (course_id, name, credits, syllabus, professor_id, department_id, semester) VALUES (seq_courses.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseName);
			pstmt.setString(2, courseCredit);
			pstmt.setString(3, courseDescription);
			pstmt.setInt(4, professorId);
			pstmt.setString(5, courseBuilding);
			pstmt.setString(6, courseSemester);

			// 4. 쿼리 실행
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("강의가 성공적으로 추가되었습니다.");
				loadCourse(); // GUI 업데이트
				clearInputFields(); // 입력 필드 초기화
			} else {
				System.out.println("강의 추가에 실패했습니다.");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로딩 실패: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void clearInputFields() {
		txtCourseName.setText("");
		txtCourseCredit.setText("");
		txtCourseDescription.setText("");
		txtCourseBuilding.setText("");
		txtCourseSemester.setText("");
	}

	// 학과 이름 가져오는 메서드
	private String getDepartmentName(int departmentId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String departmentName = "";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "SELECT name FROM departments WHERE department_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, departmentId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				departmentName = rs.getString("name");
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

		return departmentName;
	}

	public Panel createMenuPanel() {
		Panel panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.LIGHT_GRAY);

		btnMyPage = new Button("마이페이지");
		btnSugang = new Button("수강관리");
		btnGrade = new Button("성적관리");

		btnLogout = new Button("로그아웃");

		btnMyPage.addActionListener(e -> cardLayout.show(mainPanel, "myPage"));
		btnSugang.addActionListener(e -> cardLayout.show(mainPanel, "sugang"));
		btnGrade.addActionListener(e -> cardLayout.show(mainPanel, "grade"));

		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				loginFrame.setVisible(true);
			}
		});

		btnSugang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				loadCourse();
			}
		});

		panel.add(btnMyPage);
		panel.add(btnSugang);
		panel.add(btnGrade);
		panel.add(btnLogout);

		return panel;
	}

	private Panel createMyPagePanel() {
		Panel panel = new Panel(new BorderLayout());

		// 마이페이지 조회 패널
		myPageViewPanel = new Panel(new BorderLayout());
		myPageViewPanel.setBackground(Color.WHITE);

		Panel viewGridPanel = new Panel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
		};
		viewGridPanel.setLayout(new GridLayout(6, 2, 5, 5)); // Reduced spacing
		viewGridPanel.setPreferredSize(new Dimension(400, 200)); // Adjusted height
		viewGridPanel.setBackground(Color.WHITE);

		txtName = new TextField(15);
		txtName.setEditable(false);
		addLabelAndTextField(viewGridPanel, "성명:", txtName);

		txtBirth = new TextField(15);
		txtBirth.setEditable(false);
		addLabelAndTextField(viewGridPanel, "생년월일:", txtBirth);

		txtContact = new TextField(15);
		txtContact.setEditable(false);
		addLabelAndTextField(viewGridPanel, "연락처:", txtContact);

		txtMajor = new TextField(15);
		txtMajor.setEditable(false);
		addLabelAndTextField(viewGridPanel, "학과:", txtMajor);

		txtEmail = new TextField(15);
		txtEmail.setEditable(false);
		addLabelAndTextField(viewGridPanel, "이메일:", txtEmail);

		txtHireDate = new TextField(15);
		txtHireDate.setEditable(false);
		addLabelAndTextField(viewGridPanel, "입사일자:", txtHireDate);

		myPageViewPanel.add(viewGridPanel, BorderLayout.NORTH);

		// 정보 수정 버튼
		btnInfoEdit = new Button("정보수정");
		Panel viewButtonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
		viewButtonPanel.add(btnInfoEdit);
		viewButtonPanel.setBackground(Color.WHITE);
		myPageViewPanel.add(viewButtonPanel, BorderLayout.SOUTH);

		// 정보 수정 버튼 액션 리스너
		btnInfoEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 수정 팝업창 생성
				EditMyPageFrame editMyPageFrame = new EditMyPageFrame(ProfessorFrame.this, txtName.getText(),
						txtContact.getText(), txtEmail.getText(), professorId); // ProfessorFrame의 인스턴스 넘겨줌
				editMyPageFrame.setVisible(true);
			}
		});

		panel.add(myPageViewPanel, BorderLayout.CENTER);

		return panel;
	}

	// Label과 TextField를 추가하는 메소드
	private void addLabelAndTextField(Panel panel, String labelText, TextField textField) {
		Panel linePanel = new Panel(new FlowLayout(FlowLayout.LEFT));
		linePanel.setBackground(Color.WHITE);

		Label label = new Label(labelText);
		label.setPreferredSize(new Dimension(70, 18)); // Reduced size
		linePanel.add(label);

		textField.setPreferredSize(new Dimension(100, 18)); // Adjusted size
		linePanel.add(textField);

		panel.add(linePanel);
	}

	private Panel createSugangPanel() {
		Panel panel = new Panel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		// 1. 입력 영역
		Panel inputPanel = new Panel();
		inputPanel.setLayout(new GridLayout(5, 1, 0, 15)); // 세로로 배치, 간격 조정
		inputPanel.setPreferredSize(new Dimension(220, 220)); // 폭을 줄임, 높이를 늘림
		inputPanel.setBackground(Color.WHITE);

		Label lblCourseName = new Label("강의명:");
		txtCourseName = new TextField(12); // 텍스트 필드 크기 줄임
		Panel courseNamePanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 간격 없앰
		courseNamePanel.setBackground(Color.WHITE);
		lblCourseName.setPreferredSize(new Dimension(60, 20)); // 라벨 크기 고정
		txtCourseName.setPreferredSize(new Dimension(120, 20)); // 텍스트 필드 크기 고정
		courseNamePanel.add(lblCourseName);
		courseNamePanel.add(txtCourseName);
		inputPanel.add(courseNamePanel);

		Label lblCourseBuilding = new Label("강의 건물:");
		txtCourseBuilding = new TextField(12);
		Panel courseBuildingPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		courseBuildingPanel.setBackground(Color.WHITE);
		lblCourseBuilding.setPreferredSize(new Dimension(60, 20));
		txtCourseBuilding.setPreferredSize(new Dimension(120, 20));
		courseBuildingPanel.add(lblCourseBuilding);
		courseBuildingPanel.add(txtCourseBuilding);
		inputPanel.add(courseBuildingPanel);

		Label lblCourseSemester = new Label("학기:");
		txtCourseSemester = new TextField(12);
		Panel courseSemesterPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		courseSemesterPanel.setBackground(Color.WHITE);
		lblCourseSemester.setPreferredSize(new Dimension(60, 20));
		txtCourseSemester.setPreferredSize(new Dimension(120, 20));
		courseSemesterPanel.add(lblCourseSemester);
		courseSemesterPanel.add(txtCourseSemester);
		inputPanel.add(courseSemesterPanel);

		Label lblCourseCredit = new Label("학점:");
		txtCourseCredit = new TextField(12); // 텍스트 필드 크기 줄임
		Panel courseCreditPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 간격 없앰
		courseCreditPanel.setBackground(Color.WHITE);
		lblCourseCredit.setPreferredSize(new Dimension(60, 20)); // 라벨 크기 고정
		txtCourseCredit.setPreferredSize(new Dimension(120, 20)); // 텍스트 필드 크기 고정
		courseCreditPanel.add(lblCourseCredit);
		courseCreditPanel.add(txtCourseCredit);
		inputPanel.add(courseCreditPanel);

		Label lblCourseDescription = new Label("강의 설명:");
		txtCourseDescription = new TextField(12); // 텍스트 필드 크기 줄임
		Panel courseDescriptionPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 간격 없앰
		courseDescriptionPanel.setBackground(Color.WHITE);
		lblCourseDescription.setPreferredSize(new Dimension(60, 20)); // 라벨 크기 고정
		txtCourseDescription.setPreferredSize(new Dimension(120, 20)); // 텍스트 필드 크기 고정
		courseDescriptionPanel.add(lblCourseDescription);
		courseDescriptionPanel.add(txtCourseDescription);
		inputPanel.add(courseDescriptionPanel);

		// 2. 버튼 영역
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.setPreferredSize(new Dimension(200, 40)); // 적절한 크기 설정
		buttonPanel.setBackground(Color.WHITE);

		btnRegister = new Button("등록");
		btnModify = new Button("수정");
		btnSearch = new Button("검색");
		buttonPanel.add(btnRegister);
		buttonPanel.add(btnModify);
		buttonPanel.add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 검색 팝업창 생성 및 표시
				SearchCoursePopup searchPopup = new SearchCoursePopup(ProfessorFrame.this, "강의 검색", true);
				searchPopup.setVisible(true);
			}
		});

		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 리스트에서 선택된 강의 정보 가져오기
				int selectedIndex = courseList.getSelectedIndex();
				if (selectedIndex == -1) {
					System.out.println("수정할 강의를 선택해주세요.");
					return;
				}

				String selectedCourse = courseList.getItem(selectedIndex);
				String[] courseInfo = selectedCourse.split("\\|");

				// courseInfo 배열의 길이가 예상대로인지 확인
				if (courseInfo.length < 6) {
					System.err.println("오류: 잘못된 강좌 데이터 형식입니다.");
					return;
				}

				// 2. 텍스트 필드에 강의 정보 표시
				String courseId = courseInfo[0].trim();
				String name = courseInfo[1].trim();
				String credits = courseInfo[2].trim();
				String building = courseInfo[3].trim();
				String semester = courseInfo[4].trim();
				String syllabus = courseInfo[5].trim();

				txtCourseName.setText(name);
				txtCourseCredit.setText(credits);
				txtCourseBuilding.setText(building);
				txtCourseSemester.setText(semester);
				txtCourseDescription.setText(syllabus);

				// 3. 수정할 courseId 저장 (updateCourse() 메서드에서 사용)
				selectedCourseId = courseId;
				btnRegister.setLabel("수정");

				// 기존 액션 리스너 제거 후 새로운 액션 리스너 추가
				ActionListener[] listeners = btnRegister.getActionListeners();
				for (ActionListener listener : listeners) {
					btnRegister.removeActionListener(listener);
				}
				btnRegister.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (btnRegister.getLabel().equals("등록")) {
							addCourse(); // 강의 추가 메서드 호출
						} else if (btnRegister.getLabel().equals("수정")) {
							updateCourse(); // 강의 수정 메서드 호출
						}
						clearInputFields();
					}
				});
			}
		});

		// register 버튼 액션 리스너
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCourse(); // 강의 추가 메서드 호출
				clearInputFields();
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCourse(); // 강의 추가 메서드 호출
			}
		});

		// 3. 리스트 영역 (표 형태)
		Panel listPanel = new Panel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBackground(Color.LIGHT_GRAY);

		// **컬럼명을 표시할 Panel 생성**
		Panel columnHeaderPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
		columnHeaderPanel.setBackground(Color.LIGHT_GRAY);

		Label lblCourseIdHeader = new Label("강좌 ID");
		lblCourseIdHeader.setPreferredSize(new Dimension(60, 20)); // 폭 조정
		Label lblCourseNameHeader = new Label("강의명");
		lblCourseNameHeader.setPreferredSize(new Dimension(220, 20)); // 폭 조정
		Label lblCreditsHeader = new Label("학점");
		lblCreditsHeader.setPreferredSize(new Dimension(50, 20)); // 폭 조정
		Label lblBuildingHeader = new Label("강의 건물");
		lblBuildingHeader.setPreferredSize(new Dimension(100, 20)); // 폭 조정
		Label lblSemesterHeader = new Label("학기");
		lblSemesterHeader.setPreferredSize(new Dimension(50, 20)); // 폭 조정
		Label lblSyllabusHeader = new Label("강의계획");
		lblSyllabusHeader.setPreferredSize(new Dimension(240, 20)); // 폭 조정

		columnHeaderPanel.add(lblCourseIdHeader);
		columnHeaderPanel.add(lblCourseNameHeader);
		columnHeaderPanel.add(lblCreditsHeader);
		columnHeaderPanel.add(lblBuildingHeader);
		columnHeaderPanel.add(lblSemesterHeader);
		columnHeaderPanel.add(lblSyllabusHeader);

		listPanel.add(columnHeaderPanel, BorderLayout.NORTH);

		courseList = new List();
		courseList.setFont(new Font("Monospaced", Font.PLAIN, 14)); // 고정 폭 폰트 설정

		Panel listButtonPanel = new Panel();
		listButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		listButtonPanel.setBackground(Color.LIGHT_GRAY);

		btnDelete = new Button("삭제");
		btnSearchAll = new Button("전체조회");
		listButtonPanel.add(btnDelete);
		listButtonPanel.add(btnSearchAll);

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 리스트에서 선택된 강의 정보 가져오기
				int selectedIndex = courseList.getSelectedIndex();
				if (selectedIndex == -1) {
					System.out.println("삭제할 강의를 선택해주세요.");
					return;
				}

				String selectedCourse = courseList.getItem(selectedIndex);
				String courseId = selectedCourse.split("\\|")[0].trim(); // courseId 추출

				// 2. 데이터베이스에서 해당 강의 삭제
				deleteCourse(courseId);

				// 3. 리스트 갱신
				loadCourse();
			}
		});

		btnSearchAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadCourse();
			}
		});

		listPanel.add(courseList, BorderLayout.CENTER);
		listPanel.add(listButtonPanel, BorderLayout.SOUTH);

		// loadCourse();

		// 전체 패널에 각 영역 배치
		panel.add(inputPanel, BorderLayout.WEST);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}

	private void updateCourse() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String courseName = txtCourseName.getText().trim();
			String courseCredit = txtCourseCredit.getText().trim();
			String courseDescription = txtCourseDescription.getText().trim();
			String courseBuilding = txtCourseBuilding.getText().trim(); // 강의 건물 정보
			String courseSemester = txtCourseSemester.getText().trim();

			// 빈 텍스트 필드 확인
			if (courseName.isEmpty()) {
				System.out.println("강의명을 입력해주세요.");
				return; // 업데이트 중단
			}

			// SQL 쿼리 수정: WHERE 절을 course_id 기반으로 변경
			String sql = "UPDATE courses SET name = ?, credits = ?, syllabus = ?, department_id = ?, semester = ? WHERE course_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseName);
			pstmt.setString(2, courseCredit);
			pstmt.setString(3, courseDescription);
			pstmt.setString(4, courseBuilding); // 강의 건물 정보를 department_id에 넣음
			pstmt.setString(5, courseSemester);
			pstmt.setString(6, selectedCourseId); // 선택된 courseId 사용

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("강의가 성공적으로 수정되었습니다.");
			} else {
				System.out.println("강의 수정에 실패했습니다.");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 리스트 갱신 및 텍스트 필드 초기화
		loadCourse();
		clearInputFields();
		selectedCourseId = null; // courseId 초기화
	}

	private void deleteCourse(String courseId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "DELETE FROM courses WHERE course_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseId);

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("강의가 성공적으로 삭제되었습니다.");
			} else {
				System.out.println("강의 삭제에 실패했습니다.");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 수강 목록 로딩 메서드
	private void loadCourse() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = "SELECT course_id, name, credits, syllabus, department_id, semester FROM courses WHERE professor_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, professorId);

			rs = pstmt.executeQuery();
			courseList.removeAll();

			while (rs.next()) {
				String courseId = rs.getString("course_id");
				String name = rs.getString("name");
				String credits = rs.getString("credits");
				String syllabus = rs.getString("syllabus");
				String building = rs.getString("department_id");
				String semester = rs.getString("semester");

				// Syllabus 내용이 너무 길어지면 ...으로 줄여서 표시
				if (syllabus.length() > 27) {
					syllabus = syllabus.substring(0, 27) + "...";
				}

				// 폭을 맞추기 위해 String.format() 사용
		        String courseInfo = padRight(courseId, 8) 
                        + padRight(name, 20) 
                        + padRight(credits, 7) 
                        + padRight(building, 14) 
                        + padRight(semester, 7) 
                        + syllabus;

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

    public static String padRight(String str, int n) {
        // 한글과 영어를 고려하여 문자열 길이 맞추기
        int paddingLength = n - str.length();
        StringBuilder paddedString = new StringBuilder(str);
        for (int i = 0; i < paddingLength; i++) {
            paddedString.append(" ");
        }
        return paddedString.toString();
    }
    
	private Panel createGradePanel() {
		Panel panel = new Panel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		// 1. 입력 영역
		Panel inputPanel = new Panel();
		inputPanel.setLayout(new GridLayout(3, 1, 0, 15)); // 세로로 배치, 간격 조정
		inputPanel.setPreferredSize(new Dimension(220, 150)); // 적절한 크기 설정
		inputPanel.setBackground(Color.WHITE);

		Label lblCourseIDGrade = new Label("강의 ID:");
		txtCourseIDGrade = new TextField(12); // 텍스트 필드 크기 줄임
		Panel courseIDGradePanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 간격 없앰
		courseIDGradePanel.setBackground(Color.WHITE);
		lblCourseIDGrade.setPreferredSize(new Dimension(60, 20)); // 라벨 크기 고정
		txtCourseIDGrade.setPreferredSize(new Dimension(120, 20)); // 텍스트 필드 크기 고정
		courseIDGradePanel.add(lblCourseIDGrade);
		courseIDGradePanel.add(txtCourseIDGrade);
		inputPanel.add(courseIDGradePanel);

		Label lblStudentName = new Label("학생 이름:");
		txtStudentName = new TextField(12); // 텍스트 필드 크기 줄임
		Panel studentNamePanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 간격 없앰
		studentNamePanel.setBackground(Color.WHITE);
		lblStudentName.setPreferredSize(new Dimension(60, 20)); // 라벨 크기 고정
		txtStudentName.setPreferredSize(new Dimension(120, 20)); // 텍스트 필드 크기 고정
		studentNamePanel.add(lblStudentName);
		studentNamePanel.add(txtStudentName);
		inputPanel.add(studentNamePanel);

		Label lblStudentGrade = new Label("성적:");
		txtStudentGrade = new TextField(12); // 텍스트 필드 크기 줄임
		Panel studentGradePanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 간격 없앰
		studentGradePanel.setBackground(Color.WHITE);
		lblStudentGrade.setPreferredSize(new Dimension(60, 20)); // 라벨 크기 고정
		txtStudentGrade.setPreferredSize(new Dimension(120, 20)); // 텍스트 필드 크기 고정
		studentGradePanel.add(lblStudentGrade);
		studentGradePanel.add(txtStudentGrade);
		inputPanel.add(studentGradePanel);

		// 2. 버튼 영역
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.setPreferredSize(new Dimension(220, 40)); // 적절한 크기 설정
		buttonPanel.setBackground(Color.WHITE);

		btnGradeRegister = new Button("성적 등록");
		btnGradeModify = new Button("성적 수정");
		btnGradeSearch = new Button("검색 조회");
		btnGradeSearchAll = new Button("성적순 조회");

		buttonPanel.add(btnGradeRegister);
		buttonPanel.add(btnGradeModify);
		buttonPanel.add(btnGradeSearch);
		buttonPanel.add(btnGradeSearchAll);

		// 3. 리스트 영역 (표 형태)
		Panel listPanel = new Panel();
		listPanel.setLayout(new BorderLayout()); // 리스트와 버튼을 배치하기 위한 레이아웃
		listPanel.setBackground(Color.LIGHT_GRAY); // 보기 좋게 배경색 설정

		// **컬럼명을 표시할 Panel 생성**
		Panel columnHeaderPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
		columnHeaderPanel.setBackground(Color.LIGHT_GRAY);

		Label lblCourseIdHeader = new Label("강의 ID");
		lblCourseIdHeader.setPreferredSize(new Dimension(50, 20)); // 폭 조정
		Label lblStudentNameHeader = new Label("학생 이름");
		lblStudentNameHeader.setPreferredSize(new Dimension(70, 20)); // 폭 조정
		Label lblGradeHeader = new Label("성적");
		lblGradeHeader.setPreferredSize(new Dimension(40, 20)); // 폭 조정
		Label lblStudentReview = new Label("학생리뷰");
		lblGradeHeader.setPreferredSize(new Dimension(50, 20)); // 폭 조정

		columnHeaderPanel.add(lblCourseIdHeader);
		columnHeaderPanel.add(lblStudentNameHeader);
		columnHeaderPanel.add(lblGradeHeader);
		columnHeaderPanel.add(lblStudentReview);

		listPanel.add(columnHeaderPanel, BorderLayout.NORTH); // 컬럼 헤더를 리스트 상단에 추가

		gradeList = new List();

		Panel listButtonPanel = new Panel();
		listButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		listButtonPanel.setBackground(Color.LIGHT_GRAY);

		listPanel.add(gradeList, BorderLayout.CENTER); // 리스트를 중앙에 배치
		listPanel.add(listButtonPanel, BorderLayout.SOUTH); // 버튼을 하단에 배치
		
		// 4. 액션 리스너 등록
		btnGradeModify.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // 1. 리스트에서 선택된 성적 정보 가져오기
		        int selectedIndex = gradeList.getSelectedIndex();
		        if (selectedIndex == -1) {
		            System.out.println("수정할 성적을 선택해주세요.");
		            return;
		        }

		        String selectedGrade = gradeList.getItem(selectedIndex);
		        String[] gradeInfo = selectedGrade.split("\\|");

		        // gradeInfo 배열의 길이가 예상대로인지 확인
		        if (gradeInfo.length < 4) {
		            System.err.println("오류: 잘못된 성적 데이터 형식입니다.");
		            return;
		        }

		        // 2. 팝업창 생성 및 표시
		        String courseId = gradeInfo[0].trim();
		        String studentName = gradeInfo[1].trim();
		        String currentGrade = gradeInfo[2].trim();
		        String student_review = gradeInfo[3].trim();

		        openGradeModifyPopup(courseId, studentName, currentGrade, student_review);

		    }
		});
		btnGradeSearchAll.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        loadGradesDataSorted(); // 성적순으로 조회
		    }
		});
		btnGradeRegister.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        registerGrade(); // 성적 등록 메서드 호출
		    }
		});
		
		btnGradeSearch.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // 검색 팝업창 생성 및 표시
		        openGradeSearchPopup();
		    }
		});

		// grades 테이블 데이터 로딩
		loadGradesData();

		// 전체 패널에 각 영역 배치
		panel.add(inputPanel, BorderLayout.WEST);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}
	
	private void loadGradesDataSorted() {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        Class.forName(JDBC_DRIVER);
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);

	        // SQL 쿼리 수정: 성적을 기준으로 내림차순 정렬
	        String sql = "SELECT c.course_id, s.name AS student_name, g.grade, g.student_review "
	                + "FROM grades g, enrollments e, courses c, students s " + "WHERE " + "c.professor_id = ? "
	                + "AND g.enrollment_id = e.enrollment_id " + "AND e.course_id = c.course_id "
	                + "AND e.student_id = s.student_id " + "ORDER BY g.grade DESC"; // 성적 내림차순 정렬
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, professorId);
	        rs = pstmt.executeQuery();

	        gradeList.removeAll(); // 기존 리스트 초기화

	        while (rs.next()) {
	            String courseId = rs.getString("course_id");
	            String studentName = rs.getString("student_name");
	            String grade = rs.getString("grade");
	            String student_review = rs.getString("student_review");

	            // 리스트에 표시할 문자열 생성
	            String gradeInfo = String.format("%-10s | %-15s | %-5s | %-10s", courseId, studentName, grade,
	                    student_review);
	            gradeList.add(gradeInfo);
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
	
	private void openGradeSearchPopup() {
	    GradeSearchPopup popup = new GradeSearchPopup(this, "성적 검색", true);
	    popup.setVisible(true);
	}

	// GradeSearchPopup 클래스 정의
	class GradeSearchPopup extends Dialog implements ActionListener {
	    private TextField txtSearchCourseID, txtSearchStudentName, txtSearchStudentID;
	    private Button btnSearch, btnCancel;
	    private ProfessorFrame parentFrame;

	    public GradeSearchPopup(ProfessorFrame parentFrame, String title, boolean modal) {
	        super(parentFrame, title, modal);
	        this.parentFrame = parentFrame;

	        setSize(400, 200);
	        setLayout(new GridLayout(4, 2));

	        Label lblSearchCourseID = new Label("강의 ID:");
	        txtSearchCourseID = new TextField(20);
	        Label lblSearchStudentName = new Label("학생 이름:");
	        txtSearchStudentName = new TextField(20);
	        Label lblSearchStudentID = new Label("학생 ID:");
	        txtSearchStudentID = new TextField(20);

	        btnSearch = new Button("검색");
	        btnCancel = new Button("취소");

	        add(lblSearchCourseID);
	        add(txtSearchCourseID);
	        add(lblSearchStudentName);
	        add(txtSearchStudentName);
	        add(lblSearchStudentID);
	        add(txtSearchStudentID);
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

	        setVisible(true);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == btnSearch) {
	            // 1. 검색 조건 가져오기
	            String courseID = txtSearchCourseID.getText();
	            String studentName = txtSearchStudentName.getText();
	            String studentID = txtSearchStudentID.getText();

	            // 2. 검색 수행
	            performGradeSearch(courseID, studentName, studentID);

	            // 3. 팝업 닫기
	            dispose();
	        } else if (e.getSource() == btnCancel) {
	            // 취소 버튼 클릭 시 동작
	            dispose(); // 팝업 닫기
	        }
	    }
	    
	 // 검색 수행 메서드
	    private void performGradeSearch(String courseID, String studentName, String studentID) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);

	            // 1. SQL 쿼리 생성 (검색 조건에 따라 동적으로 쿼리 생성)
	            StringBuilder sql = new StringBuilder("SELECT c.course_id, s.name AS student_name, g.grade, g.student_review " +
	                    "FROM grades g, enrollments e, courses c, students s " +
	                    "WHERE c.professor_id = ? " + // 현재 로그인한 교수의 강의만 검색
	                    "AND g.enrollment_id = e.enrollment_id " +
	                    "AND e.course_id = c.course_id " +
	                    "AND e.student_id = s.student_id ");

	            // 강의 ID 조건 추가
	            if (courseID != null && !courseID.isEmpty()) {
	                sql.append("AND c.course_id LIKE ? ");
	            }

	            // 학생 이름 조건 추가
	            if (studentName != null && !studentName.isEmpty()) {
	                sql.append("AND s.name LIKE ? ");
	            }

	            // 학생 ID 조건 추가
	            if (studentID != null && !studentID.isEmpty()) {
	                sql.append("AND s.student_id LIKE ? ");
	            }

	            // 2. PreparedStatement 생성 및 파라미터 설정
	            pstmt = conn.prepareStatement(sql.toString());
	            int parameterIndex = 1; // 시작 인덱스
	            pstmt.setInt(parameterIndex++, parentFrame.getProfessorId()); // 교수 ID 설정

	            // 동적으로 추가된 파라미터 설정
	            if (courseID != null && !courseID.isEmpty()) {
	                pstmt.setString(parameterIndex++, "%" + courseID + "%");
	            }

	            if (studentName != null && !studentName.isEmpty()) {
	                pstmt.setString(parameterIndex++, "%" + studentName + "%");
	            }

	            if (studentID != null && !studentID.isEmpty()) {
	                pstmt.setString(parameterIndex++, "%" + studentID + "%");
	            }

	            // 3. 쿼리 실행 및 결과 처리
	            rs = pstmt.executeQuery();
	            gradeList.removeAll(); // 기존 리스트 초기화

	            while (rs.next()) {
	                String courseId = rs.getString("course_id");
	                String student_Name = rs.getString("student_name");
	                String grade = rs.getString("grade");
	                String student_review = rs.getString("student_review");

	                // 리스트에 표시할 문자열 생성
	                String gradeInfo = String.format("%-10s | %-15s | %-5s | %-10s", courseId, student_Name, grade, student_review);
	                gradeList.add(gradeInfo);
	            }

	        } catch (ClassNotFoundException e) {
	            System.err.println("드라이버 로딩 실패: " + e.getMessage());
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.err.println("SQL 에러: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (pstmt != null) pstmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    

	    public void setVisible(boolean b) {
	        super.setVisible(b);
	    }
	}

	private void clearGradeInputFields() {
		txtCourseIDGrade.setText("");
		txtStudentName.setText("");
		txtStudentGrade.setText("");
	}

	private void registerGrade() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. DB 연결
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 2. 텍스트 필드에서 입력된 값 가져오기
			String courseId = txtCourseIDGrade.getText();
			String studentName = txtStudentName.getText();
			String grade = txtStudentGrade.getText();

			// 3. 입력값 검증
			if (courseId.isEmpty() || studentName.isEmpty() || grade.isEmpty()) {
				System.out.println("모든 필드를 채워주세요.");
				return;
			}

			// 4. 강의 ID와 학생 이름이 데이터베이스에 존재하는지 확인
			if (!isValidCourseId(conn, courseId) || !isValidStudentName(conn, studentName)) {
				System.out.println("유효하지 않은 강의 ID 또는 학생 이름입니다.");
				return;
			}

			// 5. 해당 강의를 수강하는 학생인지 확인하고 enrollment_id 가져오기
			int enrollmentId = getEnrollmentId(conn, courseId, studentName);
			if (enrollmentId == -1) {
				System.out.println("해당 강의를 수강하는 학생이 아닙니다.");
				return;
			}

			// 6. 이미 등록된 성적인지 확인
			if (isGradeAlreadyRegistered(conn, enrollmentId)) {
				System.out.println("이미 등록된 성적입니다.");
				return;
			}

			// 7. SQL 쿼리 준비 (grade_id 컬럼 제거, student_review 컬럼 추가)
			String sql = "INSERT INTO grades (grade_id,enrollment_id, grade, student_review) "
					+ "VALUES (seq_grades.nextval,?, ?, '')";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, enrollmentId);
			pstmt.setString(2, grade);

			// 8. 쿼리 실행
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("성적이 성공적으로 등록되었습니다.");
				loadGradesData(); // 성적 목록 갱신
				clearGradeInputFields(); // 입력 필드 초기화
			} else {
				System.out.println("성적 등록에 실패했습니다.");
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

	// 해당 강의를 수강하는 학생인지 확인하고 enrollment_id 가져오기
	private int getEnrollmentId(Connection conn, String courseId, String studentName) throws SQLException {
		String sql = "SELECT e.enrollment_id " + "FROM enrollments e "
				+ "JOIN students s ON e.student_id = s.student_id " + "WHERE e.course_id = ? AND s.name = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, courseId);
			pstmt.setString(2, studentName);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("enrollment_id");
				} else {
					return -1; // 해당 강의를 수강하는 학생이 아님
				}
			}
		}
	}

	private void openGradeModifyPopup(String courseId, String studentName, String currentGrade, String student_review) {
		GradeModifyPopup popup = new GradeModifyPopup(this, "성적 수정", true, courseId, studentName, currentGrade,
				student_review);
		popup.setVisible(true);
	}

	// 성적 수정 팝업 클래스
	class GradeModifyPopup extends Dialog implements ActionListener {
		private TextField txtNewGrade;
		private Button btnSave, btnCancel;
		private String courseId, studentName, currentGrade, student_review;
		private ProfessorFrame parentFrame; // 부모 프레임 참조

		public GradeModifyPopup(ProfessorFrame parentFrame, String title, boolean modal, String courseId,
				String studentName, String currentGrade, String student_review) {
			super(parentFrame, title, modal);
			this.parentFrame = parentFrame; // 부모 프레임 저장
			this.courseId = courseId;
			this.studentName = studentName;
			this.currentGrade = currentGrade;
			this.student_review = student_review;

			setSize(300, 150);
			setLayout(new GridLayout(4, 2));

			Label lblNewGrade = new Label("새로운 성적:");
			txtNewGrade = new TextField(10);
			txtNewGrade.setText(currentGrade); // 기존 성적을 텍스트 필드에 표시

			btnSave = new Button("저장");
			btnCancel = new Button("취소");

			add(new Label("강의 ID:"));
			add(new Label(courseId));
			add(new Label("학생 이름:"));
			add(new Label(studentName));
			add(lblNewGrade);
			add(txtNewGrade);
			add(btnSave);
			add(btnCancel);

			btnSave.addActionListener(this);
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
			if (e.getSource() == btnSave) {
				// 1. 새로운 성적 가져오기
				String newGrade = txtNewGrade.getText();

				// 2. 데이터베이스 업데이트
				updateGrade(courseId, studentName, newGrade, student_review);

				// 3. 팝업 닫기
				dispose();
			} else if (e.getSource() == btnCancel) {
				// 취소 버튼 클릭 시 동작
				dispose(); // 팝업 닫기
			}
		}

		// 데이터베이스 업데이트 메서드
		private void updateGrade(String courseId, String studentName, String newGrade, String student_review) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				// 1. DB 연결
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);

				// 2. SQL 쿼리 준비
				String sql = "UPDATE grades SET grade = ? "
						+ "WHERE enrollment_id = (SELECT enrollment_id FROM enrollments "
						+ "WHERE course_id = ? AND student_id = (SELECT student_id FROM students WHERE name = ?))";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newGrade);
				pstmt.setString(2, courseId);
				pstmt.setString(3, studentName);

				// 3. 쿼리 실행
				int rowsAffected = pstmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("성적이 성공적으로 수정되었습니다.");
					parentFrame.loadGradesData(); // ProfessorFrame의 loadGradesData() 호출
				} else {
					System.out.println("성적 수정에 실패했습니다.");
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

	// 이미 등록된 성적인지 확인
	private boolean isGradeAlreadyRegistered(Connection conn, int enrollmentId) throws SQLException {
		String sql = "SELECT enrollment_id FROM grades WHERE enrollment_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, enrollmentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next(); // 이미 등록된 성적이 있다면 true 반환
			}
		}
	}

	private boolean isValidCourseId(Connection conn, String courseId) throws SQLException {
		String sql = "SELECT course_id FROM courses WHERE course_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, courseId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	private boolean isValidStudentName(Connection conn, String studentName) throws SQLException {
		String sql = "SELECT name FROM students WHERE name = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentName);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	private void loadGradesData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// grades 테이블에서 필요한 컬럼들을 선택
			String sql = "SELECT c.course_id, s.name AS student_name, g.grade, g.student_review "
					+ "FROM grades g, enrollments e, courses c, students s " + "WHERE " + "c.professor_id = ? "
					+ "AND g.enrollment_id = e.enrollment_id " + "AND e.course_id = c.course_id "
					+ "AND e.student_id = s.student_id ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			gradeList.removeAll(); // 기존 리스트 초기화

			while (rs.next()) {
				String courseId = rs.getString("course_id");
				String studentName = rs.getString("student_name");
				String grade = rs.getString("grade");
				String student_review = rs.getString("student_review");

				// 리스트에 표시할 문자열 생성
				String gradeInfo = String.format("%-10s | %-15s | %-5s | %-10s", courseId, studentName, grade,
						student_review);
				gradeList.add(gradeInfo);
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

	public static void main(String[] args) {
		// LoginFrame을 생성하고 ProfessorMainFrame에 전달
		LoginFrame loginFrame = new LoginFrame();
		// ProfessorFrame professorFrame = new ProfessorFrame(loginFrame); // professor
		// id를 넘겨줘야함
		loginFrame.setVisible(true);
	}

	// ** 텍스트 필드 값을 업데이트하는 메서드
	public void updateTextFields(String name, String contact, String email) {
		txtName.setText(name);
		txtContact.setText(contact);
		txtEmail.setText(email);
	}

	public int getProfessorId() {
		return professorId;
	}

	// DB 업데이트 메서드 (EditMyPageFrame에서 호출)
	public void updateProfessorInfo(String newName, String newContact, String newEmail, String newPw) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// SQL 쿼리 준비
			String sql = "UPDATE professors SET name = ?, phone = ?, email = ?, password = ? WHERE professor_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newName);
			pstmt.setString(2, newContact);
			pstmt.setString(3, newEmail);
			pstmt.setString(4, newPw);
			pstmt.setInt(5, professorId); // 현재 로그인한 교수의 ID 사용

			// 쿼리 실행
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("교수 정보가 업데이트되었습니다.");
			} else {
				System.out.println("교수 정보 업데이트에 실패했습니다.");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
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

// EditMyPageFrame 클래스
class EditMyPageFrame extends Frame {
	private TextField txtName, txtContact, txtEmail, txtPw;
	private Button btnSave, btnCancel;
	private ProfessorFrame professorFrame; // ProfessorFrame 인스턴스
	private int professorId;
	private String originalName, originalContact, originalEmail, originalPassword; // 기존 값 저장

	public EditMyPageFrame(ProfessorFrame professorFrame, String name, String contact, String email, int professorId) {
		super("정보 수정");
		this.professorFrame = professorFrame;
		this.professorId = professorId;
		this.originalName = name; // 기존 값 저장
		this.originalContact = contact;
		this.originalEmail = email;
		this.originalPassword = getOriginalPassword(professorId); // 기존 비밀번호 가져오기
		setSize(300, 200);
		setLayout(new BorderLayout());

		Panel editGridPanel = new Panel();
		editGridPanel.setLayout(new GridLayout(4, 2, 5, 5));
		editGridPanel.setBackground(Color.WHITE);

		// 수정 가능한 필드
		addLabelAndTextField(editGridPanel, "성명:", txtName = new TextField(name, 15));
		addLabelAndTextField(editGridPanel, "연락처:", txtContact = new TextField(contact, 15));
		addLabelAndTextField(editGridPanel, "이메일:", txtEmail = new TextField(email, 15));
		addLabelAndTextField(editGridPanel, "비밀번호:", txtPw = new TextField(15));

		add(editGridPanel, BorderLayout.NORTH);

		// 버튼
		Panel editButtonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
		btnSave = new Button("등록");
		btnCancel = new Button("취소");
		editButtonPanel.add(btnSave);
		editButtonPanel.add(btnCancel);
		editButtonPanel.setBackground(Color.WHITE);
		add(editButtonPanel, BorderLayout.SOUTH);

		// 취소버튼 클릭 시 이벤트 처리
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 팝업창 닫기
				dispose();
			}
		});

		// 등록 버튼 클릭 시 이벤트 처리
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 수정된 정보 가져오기
				String newName = txtName.getText();
				String newContact = txtContact.getText();
				String newEmail = txtEmail.getText();
				String newPw = txtPw.getText();

				// 2. 값이 비어있을 경우 기존 값 유지
				if (newName == null || newName.trim().isEmpty()) {
					newName = originalName;
				}
				if (newContact == null || newContact.trim().isEmpty()) {
					newContact = originalContact;
				}
				if (newEmail == null || newEmail.trim().isEmpty()) {
					newEmail = originalEmail;
				}
				if (newPw == null || newPw.trim().isEmpty()) {
					newPw = originalPassword; // 기존 비밀번호 유지
				}

				// 3. ProfessorFrame의 해당 텍스트 필드 업데이트 메서드 호출
				professorFrame.updateTextFields(newName, newContact, newEmail);

				// 4. ProfessorFrame에 데이터베이스 업데이트 로직 호출
				professorFrame.updateProfessorInfo(newName, newContact, newEmail, newPw);

				// 5. 팝업창 닫기
				dispose();
			}
		});

		// 프레임 가운데 배치
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		// 팝업 창이 열릴 때 기존 정보 표시
		txtName.setText(name);
		txtContact.setText(contact);
		txtEmail.setText(email);
	}

	// 기존 비밀번호 가져오는 메서드
	private String getOriginalPassword(int professorId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String password = null;

		try {
			Class.forName(ProfessorFrame.JDBC_DRIVER);
			conn = DriverManager.getConnection(ProfessorFrame.DB_URL, ProfessorFrame.USER, ProfessorFrame.PASS);

			String sql = "SELECT password FROM professors WHERE professor_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				password = rs.getString("password");
			}

		} catch (ClassNotFoundException ex) {
			System.err.println("드라이버 로딩 실패: " + ex.getMessage());
			ex.printStackTrace();
		} catch (SQLException ex) {
			System.err.println("SQL 에러: " + ex.getMessage());
			ex.printStackTrace();
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

		return password;
	}

	// Label과 TextField를 추가하는 메소드
	private void addLabelAndTextField(Panel panel, String labelText, TextField textField) {
		Panel linePanel = new Panel(new FlowLayout(FlowLayout.LEFT));
		linePanel.setBackground(Color.WHITE);

		Label label = new Label(labelText);
		label.setPreferredSize(new Dimension(70, 18)); // Reduced size
		linePanel.add(label);

		textField.setPreferredSize(new Dimension(100, 18)); // Adjusted size
		linePanel.add(textField);

		panel.add(linePanel);
	}

}
