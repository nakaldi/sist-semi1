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
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kims.semi1.config.DBConnector;

public class ProfessorFrame extends Frame {

	private CardLayout cardLayout;
	private Panel mainPanel;
	private Panel myPagePanel, sugangPanel, gradePanel;
	private Button btnLogout, btnTimetable;
	private LoginFrame loginFrame;
	private Panel myPageViewPanel;
	TextField txtName, txtBirth, txtContact, txtMajor, txtEmail, txtHireDate, txtPw;
	private Button btnInfoEdit, btnSave, btnCancel;
	private Button btnMyPage, btnSugang, btnGrade;

	// 수강 관리
	private TextField txtCourseName, txtCourseBuilding, txtCourseCredit, txtCourseDescription, txtCourseSemester;
	private Button btnRegister, btnModify, btnSearch, btnDelete, btnSearchAll;
	List courseList;
	private List gradeListRegistered; // 성적 입력 완료된 학생 리스트
	private List gradeListUnregistered; // 성적 입력 안된 학생 리스트
	// 성적 관리
	private TextField txtCourseIDGrade, txtStudentName, txtStudentGrade;
	private Button btnGradeModify, btnGradeSearch, btnGradeSearchAll, btnGradeRegister, btnStudentModify;
	private List gradeList;

	// DB 연결 정보

	private int professorId;

	private String selectedCourseId;
	private ActionListener registerActionListener;
	private Panel timetablePanel;
	private GradeRegisterPopup gradeRegisterPopup;
	private List timetableList;

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
		timetablePanel = createTimetablePanel();

		mainPanel.add(myPagePanel, "myPage");
		mainPanel.add(sugangPanel, "sugang");
		mainPanel.add(gradePanel, "grade");
		mainPanel.add(timetablePanel, "timetable");

		add(mainPanel, BorderLayout.CENTER);
		loadProfessorInfo(); // 교수 정보 로딩

		setVisible(true);
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

	public Panel createMenuPanel() {
		Panel panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.LIGHT_GRAY);

		btnMyPage = new Button("마이페이지");
		btnSugang = new Button("수강관리");
		btnGrade = new Button("성적관리");
		btnTimetable = new Button("시간표 조회");

		btnLogout = new Button("로그아웃");

		btnMyPage.addActionListener(e -> cardLayout.show(mainPanel, "myPage"));
		btnSugang.addActionListener(e -> cardLayout.show(mainPanel, "sugang"));
		btnGrade.addActionListener(e -> cardLayout.show(mainPanel, "grade"));
		btnTimetable.addActionListener(e -> cardLayout.show(mainPanel, "timetable"));

		btnSugang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				loadCourse();
			}
		});

		panel.add(btnMyPage);
		panel.add(btnSugang);
		panel.add(btnGrade);
		panel.add(btnTimetable);
		panel.add(btnLogout);
		// 액션 리스너 등록
		btnMyPage.addActionListener(e -> cardLayout.show(mainPanel, "myPage"));
		btnSugang.addActionListener(e -> {
			cardLayout.show(mainPanel, "sugang");
			loadCourse();
		});
		btnGrade.addActionListener(e -> {
			loadGradesData(); // 성적 데이터 로딩
			cardLayout.show(mainPanel, "grade");
		});
		btnTimetable.addActionListener(e -> {
			loadTimetableData(); // 시간표 데이터 로딩
			cardLayout.show(mainPanel, "timetable");
		});
		btnLogout.addActionListener(e -> {
			setVisible(false);
			loginFrame.setVisible(true);
		});

		return panel;
	}

	private Panel createTimetablePanel() {
		timetablePanel = new Panel(new BorderLayout());
		timetablePanel.setBackground(Color.WHITE);

		// 시간표 데이터를 표시할 List 생성
		timetableList = new List();
		timetableList.setFont(new Font("Monospaced", Font.PLAIN, 14));
		timetablePanel.add(new Label("강의 시간표:"), BorderLayout.NORTH); // 레이블 추가
		timetablePanel.add(timetableList, BorderLayout.CENTER);

		// 스크롤 기능 추가
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(timetableList);
		timetablePanel.add(scrollPane, BorderLayout.CENTER);

		loadTimetableData(); // 시간표 데이터 로딩

		// 시간표 리스트 더블클릭 이벤트 리스너 추가
		timetableList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 더블클릭
					// 1. 선택된 시간표 내역 가져오기
					int selectedIndex = timetableList.getSelectedIndex();
					if (selectedIndex == -1) {
						System.out.println("시간표를 선택해주세요.");
						return;
					}
					String selectedTimetable = timetableList.getItem(selectedIndex);

					// 2. 강의 ID 추출 (시간표 정보 문자열 파싱)
					String courseName = selectedTimetable.split("\\|")[0].trim(); // 강의 이름 추출
					String courseId = getCourseIdByName(courseName); // 강의 이름으로 강의 ID 조회

					// 3. 학생 목록 팝업창 열기
					if (courseId != null) {
						openStudentListPopup(courseId);
					} else {
						System.out.println("강의 ID를 찾을 수 없습니다.");
					}
				}
			}
		});

		return timetablePanel;
	}

	private Panel createGradePanel() {
		Panel panel = new Panel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		// 1. 입력 영역 제거

		// 2. 버튼 영역
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.setPreferredSize(new Dimension(220, 40));
		buttonPanel.setBackground(Color.WHITE);

		btnGradeRegister = new Button("성적 등록");
		btnGradeModify = new Button("성적 수정");
		btnGradeSearch = new Button("검색 조회");
		btnGradeSearchAll = new Button("성적순 조회");

		buttonPanel.add(btnGradeRegister);
		buttonPanel.add(btnGradeModify);

		// 4. 액션 리스너 등록
		btnGradeRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 등록 팝업창 생성 및 표시
				openGradeRegisterPopup();
			}
		});

		btnGradeModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 리스트에서 선택된 성적 정보 가져오기
				int selectedIndex = gradeListRegistered.getSelectedIndex();
				if (selectedIndex == -1) {
					System.out.println("수정할 성적을 선택해주세요.");
					return;
				}

				String selectedGrade = gradeListRegistered.getItem(selectedIndex);
				System.out.println(selectedGrade);

				String[] gradeInfo = selectedGrade.split("\\|");

				// gradeInfo 배열의 길이가 예상대로인지 확인
				if (gradeInfo.length < 5) {
					System.err.println("오류: 잘못된 성적 데이터 형식입니다.");
					return;
				}

				// 2. 팝업창 생성 및 표시
				String courseId = gradeInfo[0].trim();
				String studentId = gradeInfo[1].trim();
				String courseName = gradeInfo[2].trim();
				String studentName = gradeInfo[3].trim();
				String grade = gradeInfo[4].trim();

				openGradeModifyPopup(courseId, studentId, studentName, grade);
			}
		});

		btnGradeSearchAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGradesDataSorted(); // 성적순으로 조회
			}
		});

		btnGradeSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 검색 팝업창 생성 및 표시
				openGradeSearchPopup();
			}
		});

		// 리스트 영역 (표 형태)
		Panel listPanel = new Panel();
		listPanel.setLayout(new GridLayout(2, 1)); // 2개의 리스트를 세로로 배치
		listPanel.setBackground(Color.LIGHT_GRAY);

		gradeListRegistered = new List();
		gradeListUnregistered = new List();
		gradeList = new List();

		// 컬럼 헤더 패널 (성적 입력 완료)
		Panel columnHeaderPanelRegistered = new Panel(new FlowLayout(FlowLayout.LEFT));
		columnHeaderPanelRegistered.setBackground(Color.LIGHT_GRAY);
		Label lblCourseIdHeaderRegistered = new Label("강의 ID");
		lblCourseIdHeaderRegistered.setPreferredSize(new Dimension(50, 20));
		Label lblStudentIdHeaderRegistered = new Label("학생 ID"); // 학생 ID 컬럼
		lblStudentIdHeaderRegistered.setPreferredSize(new Dimension(70, 20));
		Label lblCourseNameHeaderRegistered = new Label("강의 이름");
		lblCourseNameHeaderRegistered.setPreferredSize(new Dimension(90, 20));
		Label lblStudentNameHeaderRegistered = new Label("학생 이름");
		lblStudentNameHeaderRegistered.setPreferredSize(new Dimension(70, 20));
		Label lblGradeHeaderRegistered = new Label("성적");
		lblGradeHeaderRegistered.setPreferredSize(new Dimension(750, 20));
		Label lblCompletionStatusRegistered = new Label("성적 입력 완료 목록");

		columnHeaderPanelRegistered.add(lblCourseIdHeaderRegistered);
		columnHeaderPanelRegistered.add(lblStudentIdHeaderRegistered); // 학생 ID 컬럼
		columnHeaderPanelRegistered.add(lblCourseNameHeaderRegistered);
		columnHeaderPanelRegistered.add(lblStudentNameHeaderRegistered);
		columnHeaderPanelRegistered.add(lblGradeHeaderRegistered);
		columnHeaderPanelRegistered.add(lblCompletionStatusRegistered);

		// 성적 입력 완료 리스트 패널
		Panel registeredPanel = new Panel(new BorderLayout());
		registeredPanel.add(columnHeaderPanelRegistered, BorderLayout.NORTH); // 컬럼 헤더 추가
		registeredPanel.add(gradeListRegistered, BorderLayout.CENTER);

		// 컬럼 헤더 패널 (성적 입력 미완료)
		Panel columnHeaderPanelUnregistered = new Panel(new FlowLayout(FlowLayout.LEFT));
		columnHeaderPanelUnregistered.setBackground(Color.LIGHT_GRAY);

		Label lblCourseIdHeaderUnregistered = new Label("강의 ID");
		lblCourseIdHeaderUnregistered.setPreferredSize(new Dimension(50, 20));
		Label lblStudentIdHeaderUnregistered = new Label("학생 ID"); // 학생 ID 컬럼
		lblStudentIdHeaderUnregistered.setPreferredSize(new Dimension(70, 20));
		Label lblCourseNameHeaderUnregistered = new Label("강의 이름");
		lblCourseNameHeaderUnregistered.setPreferredSize(new Dimension(90, 20));
		Label lblStudentNameHeaderUnregistered = new Label("학생 이름");
		lblStudentNameHeaderUnregistered.setPreferredSize(new Dimension(800, 20));
		Label lblGradeInputSuccess = new Label("성적 입력 미완료 목록");

		columnHeaderPanelUnregistered.add(lblCourseIdHeaderUnregistered);
		columnHeaderPanelUnregistered.add(lblStudentIdHeaderUnregistered); // 학생 ID 컬럼
		columnHeaderPanelUnregistered.add(lblCourseNameHeaderUnregistered);
		columnHeaderPanelUnregistered.add(lblStudentNameHeaderUnregistered);
		columnHeaderPanelUnregistered.add(lblGradeInputSuccess);

		// 성적 입력 미완료 리스트 패널
		Panel unregisteredPanel = new Panel(new BorderLayout());
		unregisteredPanel.add(columnHeaderPanelUnregistered, BorderLayout.NORTH); // 컬럼 헤더 추가
		unregisteredPanel.add(gradeListUnregistered, BorderLayout.CENTER);

		listPanel.add(registeredPanel);
		listPanel.add(unregisteredPanel);

		// grades 테이블 데이터 로딩
		loadGradesData(); // loadGradesData() 호출

		// 전체 패널에 각 영역 배치
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}

	private void loadTimetableData() {
		ResultSet rs = null;

		String sql = "SELECT cs.day_of_week, cs.start_time, cs.end_time, c.name AS course_name "
				+ "FROM class_schedules cs " + "JOIN courses c ON cs.course_id = c.course_id "
				+ "WHERE c.professor_id = ? " + "ORDER BY cs.day_of_week DESC, cs.start_time DESC"; // 일별, 시간별 내림차순

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// SQL 쿼리 실행 및 정렬 조건 추가
			// 정렬
			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			// 기존 내용 지우기
			timetableList.removeAll();

			while (rs.next()) {
				String dayOfWeek = rs.getString("day_of_week");
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");
				String courseName = rs.getString("course_name");

				// 시간표 정보 문자열 생성
				String timetableInfo = String.format("%s | %s, %s-%s", courseName, dayOfWeek, startTime, endTime);

				// List에 추가
				timetableList.add(timetableInfo);
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
		}
	}

	// 교수 정보 로딩 메서드
	private void loadProfessorInfo() {
		ResultSet rs = null;

		String sql = "SELECT name, birth_date, phone, department_id, email, hire_date FROM professors WHERE professor_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
		}
	}

	private void addCourse() {
		String sql = "INSERT INTO courses (course_id, name, credits, syllabus, professor_id, department_id, semester) VALUES (seq_courses.NEXTVAL, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 1. DB 연결

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

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());

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
		ResultSet rs = null;
		String departmentName = "";
		String sql = "SELECT name FROM departments WHERE department_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, departmentId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				departmentName = rs.getString("name");
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return departmentName;
	}

	private void openStudentListPopup(String courseId) {
		StudentListPopup popup = new StudentListPopup(this, "수강 학생 목록", true, courseId);
		popup.setVisible(true);
	}

	private String getCourseIdByName(String courseName) {
		ResultSet rs = null;

		String courseId = null;

		String sql = "SELECT course_id FROM courses WHERE name = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, courseName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				courseId = rs.getString("course_id");
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return courseId;
	}

//	private void loadTimetableData(Panel displayPanel) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			Class.forName(JDBC_DRIVER);
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//			// SQL 쿼리 실행
//			String sql = "SELECT cs.day_of_week, cs.start_time, cs.end_time, c.name AS course_name "
//					+ "FROM class_schedules cs " + "JOIN courses c ON cs.course_id = c.course_id "
//					+ "WHERE c.professor_id = ? " + "ORDER BY cs.day_of_week, cs.start_time";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, professorId);
//			rs = pstmt.executeQuery();
//
//			// 기존 내용 지우기
//			displayPanel.removeAll();
//
//			while (rs.next()) {
//				String dayOfWeek = rs.getString("day_of_week");
//				String startTime = rs.getString("start_time");
//				String endTime = rs.getString("end_time");
//				String courseName = rs.getString("course_name");
//
//				// 시간표 정보 문자열 생성
//				String timetableInfo = String.format("%s: %s-%s, %s", dayOfWeek, startTime, endTime, courseName);
//
//				// Label 생성 및 Panel에 추가
//				Label label = new Label(timetableInfo);
//				displayPanel.add(label);
//			}
//
//			// UI 갱신
//			displayPanel.revalidate();
//			displayPanel.repaint();
//
//		} catch (ClassNotFoundException e) {
//			System.err.println("드라이버 로딩 실패: " + e.getMessage());
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.err.println("SQL 에러: " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

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

		// 2. 버튼 영역
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.setPreferredSize(new Dimension(200, 40));
		buttonPanel.setBackground(Color.WHITE);

		btnRegister = new Button("등록");
		btnModify = new Button("수정");
		btnSearch = new Button("검색");
		btnDelete = new Button("삭제");

		buttonPanel.add(btnRegister);
		buttonPanel.add(btnModify);
		buttonPanel.add(btnSearch);
		buttonPanel.add(btnDelete);

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
				String courseId = selectedCourse.split("\\|")[0].trim();

				// 2. 데이터베이스에서 해당 강의 삭제
				deleteCourse(courseId);

				// 3. 리스트 갱신
				loadCourse();
			}
		});

		// register 버튼 액션 리스너
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 등록 팝업창 생성 및 표시
				AddCoursePopup addCoursePopup = new AddCoursePopup(ProfessorFrame.this);
				addCoursePopup.setVisible(true);
			}
		});

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

				// 2. 팝업 창 생성 및 표시
				String courseId = courseInfo[0].trim();
				String name = courseInfo[1].trim();
				String credits = courseInfo[2].trim();
				String building = courseInfo[3].trim();
				String semester = courseInfo[4].trim();
				String syllabus = courseInfo[5].trim();

				CourseModifyPopup modifyPopup = new CourseModifyPopup(ProfessorFrame.this, courseId, name, credits,
						building, semester, syllabus);
				modifyPopup.setVisible(true);
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
		lblCourseIdHeader.setPreferredSize(new Dimension(100, 20));
		Label lblCourseNameHeader = new Label("강의명");
		lblCourseNameHeader.setPreferredSize(new Dimension(130, 20));
		Label lblCreditsHeader = new Label("학점");
		lblCreditsHeader.setPreferredSize(new Dimension(50, 20));
		Label lblBuildingHeader = new Label("건물 번호");
		lblBuildingHeader.setPreferredSize(new Dimension(60, 20));
		Label lblSemesterHeader = new Label("학기");
		lblSemesterHeader.setPreferredSize(new Dimension(50, 20));
		Label lblSyllabusHeader = new Label("강의계획");
		lblSyllabusHeader.setPreferredSize(new Dimension(240, 20));

		columnHeaderPanel.add(lblCourseIdHeader);
		columnHeaderPanel.add(lblCourseNameHeader);
		columnHeaderPanel.add(lblCreditsHeader);
		columnHeaderPanel.add(lblBuildingHeader);
		columnHeaderPanel.add(lblSemesterHeader);
		columnHeaderPanel.add(lblSyllabusHeader);

		listPanel.add(columnHeaderPanel, BorderLayout.NORTH);

		courseList = new List();
		courseList.setFont(new Font("Monospaced", Font.PLAIN, 14));

		Panel listButtonPanel = new Panel();
		listButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		listButtonPanel.setBackground(Color.LIGHT_GRAY);

		btnSearchAll = new Button("전체조회");
		listButtonPanel.add(btnSearchAll);

		btnSearchAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadCourse();
			}
		});

		courseList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selectedIndex = courseList.getSelectedIndex();
					if (selectedIndex != -1) {
						String selectedCourse = courseList.getItem(selectedIndex);
						String courseId = selectedCourse.split("\\|")[0].trim();
						openCourseReviewPopup(courseId);
					}
				}
			}
		});

		listPanel.add(courseList, BorderLayout.CENTER);
		listPanel.add(listButtonPanel, BorderLayout.SOUTH);

		// 전체 패널에 각 영역 배치
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
	}

	private void openCourseReviewPopup(String courseId) {
		CourseReviewPopup popup = new CourseReviewPopup(this, "강의평가", true, courseId);
		popup.setVisible(true);
	}

	public void addCourseFromPopup(String courseName, String courseBuilding, String courseSemester, String courseCredit,
			String courseDescription) {
		String sql = "INSERT INTO courses (course_id, name, credits, syllabus, professor_id, department_id, semester) VALUES (seq_courses.NEXTVAL, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 1. DB 연결

			// 2. 텍스트 필드에서 입력된 값 가져오기
			// 입력값 검증
			if (courseName.isEmpty() || courseCredit.isEmpty() || courseDescription.isEmpty()
					|| courseBuilding.isEmpty() || courseSemester.isEmpty()) {
				System.out.println("모든 필드를 채워주세요.");
				return;
			}

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
			} else {
				System.out.println("강의 추가에 실패했습니다.");
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
		} finally {

		}
	}

	public void registerGrade(String courseID, String studentID, String grade) {
		ResultSet rs = null;

		String sql = "INSERT INTO grades (grade_id, enrollment_id, grade, student_review) "
				+ "VALUES (seq_grades.nextval, ?, ?, '')";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 1. DB 연결

			// 2. 입력값 검증
			if (courseID.isEmpty() || studentID.isEmpty() || grade.isEmpty()) {
				System.out.println("모든 필드를 채워주세요.");
				return;
			}

			// 3. 강의 ID와 학생 ID가 데이터베이스에 존재하는지 확인
			if (!isValidCourseId(conn, courseID) || !isValidStudentId(conn, studentID)) {
				System.out.println("유효하지 않은 강의 ID 또는 학생 ID입니다.");
				return;
			}

			// 4. 해당 강의를 수강하는 학생인지 확인하고 enrollment_id 가져오기
			int enrollmentId = getEnrollmentId(conn, courseID, studentID);
			if (enrollmentId == -1) {
				System.out.println("해당 강의를 수강하는 학생이 아닙니다.");
				return;
			}

			// 5. 이미 등록된 성적인지 확인
			if (isGradeAlreadyRegistered(conn, enrollmentId)) {
				System.out.println("이미 등록된 성적입니다.");
				return;
			}

			// 6. SQL 쿼리 준비 (grade_id 컬럼 제거, student_review 컬럼 추가)

			pstmt.setInt(1, enrollmentId);
			pstmt.setString(2, grade);

			// 7. 쿼리 실행
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("성적이 성공적으로 등록되었습니다.");
				loadGradesData(); // 성적 목록 갱신
			} else {
				System.out.println("성적 등록에 실패했습니다.");
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
		} finally {

		}
	}

	private void updateCourse() {
		String sql = "UPDATE courses SET name = ?, credits = ?, syllabus = ?, department_id = ?, semester = ? WHERE course_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {

		}

		// 리스트 갱신 및 텍스트 필드 초기화
		loadCourse();
		clearInputFields();
		selectedCourseId = null; // courseId 초기화
	}

	private void deleteCourse(String courseId) {
		String sql = "DELETE FROM courses WHERE course_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, courseId);

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("강의가 성공적으로 삭제되었습니다.");
			} else {
				System.out.println("강의 삭제에 실패했습니다.");
			}

			loadCourse(); // 삭제 후 리스트 갱신

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {

		}
	}

	// 수강 목록 로딩 메서드
	public void loadCourse() {
		ResultSet rs = null;
		String sql = "SELECT course_id, name, credits, syllabus, department_id, semester FROM courses WHERE professor_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

				// 폭을 맞추기 위해 String.format() 사용
				String courseInfo = String.format("%-4s | %-18s | %-4s | %-6s | %-4s | %-32s", courseId, name, credits,
						building, semester, syllabus);

				courseList.add(courseInfo);
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {

		}
	}

	public void registerGradeFromPopup(String courseId, String studentName, String grade) {
		ResultSet rs = null;

		String sql = "INSERT INTO grades (grade_id,enrollment_id, grade, student_review) "
				+ "VALUES (seq_grades.nextval,?, ?, '')";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 1. DB 연결

			// 2. 입력값 검증
			if (courseId.isEmpty() || studentName.isEmpty() || grade.isEmpty()) {
				System.out.println("모든 필드를 채워주세요.");
				return;
			}

			// 3. 강의 ID와 학생 이름이 데이터베이스에 존재하는지 확인
			if (!isValidCourseId(conn, courseId) || !isValidStudentId(conn, studentName)) {
				System.out.println("유효하지 않은 강의 ID 또는 학생 이름입니다.");
				return;
			}

			// 4. 해당 강의를 수강하는 학생인지 확인하고 enrollment_id 가져오기
			int enrollmentId = getEnrollmentId(conn, courseId, studentName);
			if (enrollmentId == -1) {
				System.out.println("해당 강의를 수강하는 학생이 아닙니다.");
				return;
			}

			// 5. 이미 등록된 성적인지 확인
			if (isGradeAlreadyRegistered(conn, enrollmentId)) {
				System.out.println("이미 등록된 성적입니다.");
				return;
			}

			// 6. SQL 쿼리 준비 (grade_id 컬럼 제거, student_review 컬럼 추가)

			pstmt.setInt(1, enrollmentId);
			pstmt.setString(2, grade);

			// 7. 쿼리 실행
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("성적이 성공적으로 등록되었습니다.");
				loadGradesData(); // 성적 목록 갱신
			} else {
				System.out.println("성적 등록에 실패했습니다.");
			}

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
		} finally {

		}
	}

	private void loadGradesDataSorted() {
		ResultSet rs = null;

		String sql = "SELECT c.course_id, s.student_id, s.name AS student_name, g.grade, g.student_review "
				+ "FROM grades g, enrollments e, courses c, students s " + "WHERE c.professor_id = ? "
				+ "AND g.enrollment_id = e.enrollment_id " + "AND e.course_id = c.course_id "
				+ "AND e.student_id = s.student_id " + "ORDER BY g.grade DESC"; // 성적 내림차순 정렬

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// SQL 쿼리 수정: 성적을 기준으로 내림차순 정렬, 학생 ID 추가

			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			gradeListRegistered.removeAll(); // 기존 리스트 초기화

			while (rs.next()) {
				String courseId = rs.getString("course_id");
				String studentId = rs.getString("student_id"); // 학생 ID
				String studentName = rs.getString("student_name");
				String grade = rs.getString("grade");
				String student_review = rs.getString("student_review");

				// 리스트에 표시할 문자열 생성 (학생 ID 추가)
				String gradeInfo = String.format("%-10s | %-10s | %-15s | %-5s | %-10s", courseId, studentId,
						studentName, grade, student_review);
				gradeListRegistered.add(gradeInfo);
			}
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();
		} finally {
		}
	}

	private void openGradeSearchPopup() {
		GradeSearchPopup popup = new GradeSearchPopup(this, "성적 검색", true);
		popup.setVisible(true);
	}

	private void openGradeRegisterPopup() {
		if (gradeRegisterPopup == null) {
			gradeRegisterPopup = new GradeRegisterPopup(this, "성적 등록", true);
		}
		gradeRegisterPopup.setVisible(true);
	}

	// GradeSearchPopup 클래스 수정
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
				String courseID = txtSearchCourseID.getText().trim();
				String studentName = txtSearchStudentName.getText().trim();
				String studentID = txtSearchStudentID.getText().trim();

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
			ResultSet rs = null;

			StringBuilder sql = new StringBuilder("SELECT c.course_id, s.name AS student_name, g.grade "
					+ "FROM grades g, enrollments e, courses c, students s " + "WHERE c.professor_id = ? "
					+ "AND g.enrollment_id = e.enrollment_id " + "AND e.course_id = c.course_id "
					+ "AND e.student_id = s.student_id ");

			try (Connection conn = DBConnector.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

				// 1. SQL 쿼리 생성 (검색 조건에 따라 동적으로 쿼리 생성)

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
				int parameterIndex = 1; // 시작 인덱스
				pstmt.setInt(parameterIndex++, parentFrame.getProfessorId()); // 교수 ID 설정

				// 동적으로 추가된 파라미터 설정
				if (courseID != null && !courseID.isEmpty()) {
					pstmt.setString(parameterIndex++, "%" + courseID + "%"); // 와일드카드 문자 추가
				}

				if (studentName != null && !studentName.isEmpty()) {
					pstmt.setString(parameterIndex++, "%" + studentName + "%"); // 와일드카드 문자 추가
				}

				if (studentID != null && !studentID.isEmpty()) {
					pstmt.setString(parameterIndex++, "%" + studentID + "%"); // 와일드카드 문자 추가
				}

				// 3. 쿼리 실행 및 결과 처리
				rs = pstmt.executeQuery();
				parentFrame.gradeListRegistered.removeAll(); // ProfessorFrame의 리스트 초기화
				parentFrame.gradeListUnregistered.removeAll(); // ProfessorFrame의 리스트 초기화

				while (rs.next()) {
					String courseId = rs.getString("course_id");
					String student_Name = rs.getString("student_name");
					String grade = rs.getString("grade");

					// 리스트에 표시할 문자열 생성
					String gradeInfo = String.format("%-10s | %-15s | %-15s | %-5s", courseId, student_Name,
							grade != null ? grade : "");

					// 성적 null 여부에 따라 각각의 리스트에 추가
					if (grade != null && !grade.isEmpty()) {
						parentFrame.gradeListRegistered.add(gradeInfo);
					} else {
						parentFrame.gradeListUnregistered.add(gradeInfo);
					}
				}

			} catch (SQLException e) {
				System.err.println("SQL 에러: " + e.getMessage());
				e.printStackTrace();
			} finally {

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

	// 해당 강의를 수강하는 학생인지 확인하고 enrollment_id 가져오기
	private int getEnrollmentId(Connection conn, String courseID, String studentID) throws SQLException {
		String sql = "SELECT enrollment_id FROM enrollments WHERE course_id = ? AND student_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, courseID);
			pstmt.setString(2, studentID);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("enrollment_id");
				} else {
					return -1; // 해당 강의를 수강하는 학생이 아님
				}
			}
		}
	}

	private void openGradeModifyPopup(String courseId, String courseName, String studentName, String grade) {
		GradeModifyPopup popup = new GradeModifyPopup(this, "성적 수정", true, courseId, courseName, studentName, grade);
		popup.setVisible(true);
	}

	// 성적 수정 팝업 클래스
	class GradeModifyPopup extends Dialog implements ActionListener {
		private TextField txtNewGrade;
		private Button btnSave, btnCancel;
		private String courseId, courseName, studentName, grade;
		private ProfessorFrame parentFrame; // 부모 프레임 참조

		public GradeModifyPopup(ProfessorFrame parentFrame, String title, boolean modal, String courseId,
				String courseName, String studentName, String grade) {
			super(parentFrame, title, modal);
			this.parentFrame = parentFrame; // 부모 프레임 저장
			this.courseId = courseId;
			this.courseName = courseName;
			this.studentName = studentName;
			this.grade = grade;

			setSize(300, 150);
			setLayout(new GridLayout(4, 2));

			Label lblNewGrade = new Label("새로운 성적:");
			txtNewGrade = new TextField(10);
			txtNewGrade.setText(grade); // 기존 성적을 텍스트 필드에 표시

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
				updateGrade(courseId, courseName, newGrade,grade);

				// 3. 팝업 닫기
				dispose();
			} else if (e.getSource() == btnCancel) {
				// 취소 버튼 클릭 시 동작
				dispose(); // 팝업 닫기
			}
		}

		// 데이터베이스 업데이트 메서드
		private void updateGrade(String courseId, String studentName, String newGrade, String grade) {
			ResultSet rs = null;

			String sql = "UPDATE grades SET grade = ? "
					+ "WHERE enrollment_id = (SELECT enrollment_id FROM enrollments "
					+ "WHERE course_id = ? AND student_id = (SELECT student_id FROM students WHERE name = ?))";

			try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				pstmt.setDouble(1, Double.parseDouble(newGrade));
				pstmt.setInt(2, Integer.parseInt(courseId));
				pstmt.setString(3, studentName);

				int rowsAffected = pstmt.executeUpdate();

				System.out.println(rowsAffected);
				if (rowsAffected > 0) {
					System.out.println("성적이 성공적으로 수정되었습니다.");
					parentFrame.loadGradesData(); // ProfessorFrame의 loadGradesData() 호출
				} else {
					System.out.println("성적 수정에 실패했습니다.");
				}

			} catch (SQLException e) {
				System.err.println("SQL 에러: " + e.getMessage());
				e.printStackTrace();
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

	private boolean isValidStudentId(Connection conn, String studentId) throws SQLException {
		String sql = "SELECT student_id FROM students WHERE student_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	private void loadGradesData() {
		ResultSet rs = null;

		String sql = "SELECT c.course_id, c.name AS course_name, s.student_id, s.name AS student_name, g.grade "
				+ "FROM enrollments e " + "JOIN courses c ON e.course_id = c.course_id "
				+ "JOIN students s ON e.student_id = s.student_id "
				+ "LEFT JOIN grades g ON e.enrollment_id = g.enrollment_id " + "WHERE c.professor_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// grades 테이블에서 필요한 컬럼들을 선택 (강의 이름, 학생 ID 추가)

			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			gradeListRegistered.removeAll();
			gradeListUnregistered.removeAll();

			while (rs.next()) {
				String courseId = rs.getString("course_id");
				String courseName = rs.getString("course_name");
				String studentId = rs.getString("student_id"); // 학생 ID
				String studentName = rs.getString("student_name");
				String grade = rs.getString("grade");

				// 리스트에 표시할 문자열 생성 (학생 ID 추가)
				String gradeInfo = String.format("%-10s | %-10s | %-15s | %-15s | %-5s", courseId, studentId,
						courseName, studentName, grade != null ? grade : "");

				if (grade != null && !grade.isEmpty()) {
					gradeListRegistered.add(gradeInfo);
				} else {
					gradeListUnregistered.add(gradeInfo);
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();

		}
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
		String sql = "UPDATE professors SET name = ?, phone = ?, email = ?, password = ? WHERE professor_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// SQL 쿼리 준비
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

		} catch (SQLException e) {
			System.err.println("SQL 에러: " + e.getMessage());
			e.printStackTrace();

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

		ResultSet rs = null;
		String password = null;
		String sql = "SELECT password FROM professors WHERE professor_id = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				password = rs.getString("password");
			}

		} catch (SQLException ex) {
			System.err.println("SQL 에러: " + ex.getMessage());
			ex.printStackTrace();
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
