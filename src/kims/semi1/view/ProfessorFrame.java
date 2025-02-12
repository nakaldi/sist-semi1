package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
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

import kims.semi1.controller.ProfessorController;

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
	private List courseList;

	// 성적 관리
	private TextField txtCourseIDGrade, txtStudentName, txtStudentGrade;
	private Button btnGradeModify, btnGradeSearch, btnGradeSearchAll, btnStudentRegister, btnStudentModify;
	private List gradeList;

	// DB 연결 정보
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver"; // JDBC 드라이버 클래스
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE"; // DB URL
	static final String USER = "hamster2"; // DB 계정
	static final String PASS = "1234"; // DB 비밀번호
	private int professorId;
<<<<<<< HEAD
	private String selectedCourseId;
	private ActionListener registerActionListener;
	
	
=======

	// 프로페서컨트롤러 사용을 위한 필드
	ProfessorController professorController;

>>>>>>> c7dbf2f74df044b5878990016359d59856e581c9
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

	// 컨트롤러 사용을 위한 메서드
	public void setProfessorController(ProfessorController professorController) {
		this.professorController = professorController;
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
				loadCourses(); // GUI 업데이트
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
	    btnSearch = new Button("조회");
	    buttonPanel.add(btnRegister);
	    buttonPanel.add(btnModify);
	    buttonPanel.add(btnSearch);
	     
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
		        btnRegister.removeActionListener(btnRegister.getActionListeners()[0]); // 기존 리스너 제거

		        btnRegister.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                updateCourse(); // 강의 수정 메서드 호출
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
				loadCourses();
			}
		});

		btnSearchAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadCourses();
			}
		});

		listPanel.add(courseList, BorderLayout.CENTER);
		listPanel.add(listButtonPanel, BorderLayout.SOUTH);

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

	        String courseName = txtCourseName.getText();
	        String courseCredit = txtCourseCredit.getText();
	        String courseDescription = txtCourseDescription.getText();
	        String courseBuilding = txtCourseBuilding.getText();
	        String courseSemester = txtCourseSemester.getText();

	        String sql = "UPDATE courses SET name = ?, credits = ?, syllabus = ?, department_id = ?, semester = ? WHERE course_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, courseName);
	        pstmt.setString(2, courseCredit);
	        pstmt.setString(3, courseDescription);
	        pstmt.setString(4, courseBuilding);
	        pstmt.setString(5, courseSemester);
	        pstmt.setString(6, selectedCourseId);

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
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // 리스트 갱신 및 텍스트 필드 초기화
	    loadCourses();
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
	private void loadCourses() {
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
	            String courseInfo = String.format("%-4s | %-18s | %-4s | %-6s | %-4s | %-32s",
	                    courseId, name, credits, building, semester, syllabus);

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
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
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

		btnStudentRegister = new Button("수강생 등록");
		btnStudentModify = new Button("수정");
		btnGradeModify = new Button("성적 수정");
		btnGradeSearch = new Button("검색 조회");
		btnGradeSearchAll = new Button("성적순 조회");

		buttonPanel.add(btnStudentRegister);
		buttonPanel.add(btnStudentModify);
		buttonPanel.add(btnGradeModify);
		buttonPanel.add(btnGradeSearch);
		buttonPanel.add(btnGradeSearchAll);

		// 3. 리스트 영역 (표 형태)
		Panel listPanel = new Panel();
		listPanel.setLayout(new BorderLayout()); // 리스트와 버튼을 배치하기 위한 레이아웃
		listPanel.setBackground(Color.LIGHT_GRAY); // 보기 좋게 배경색 설정

		gradeList = new List();

		Panel listButtonPanel = new Panel();
		listButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		listButtonPanel.setBackground(Color.LIGHT_GRAY);

		listPanel.add(gradeList, BorderLayout.CENTER); // 리스트를 중앙에 배치
		listPanel.add(listButtonPanel, BorderLayout.SOUTH); // 버튼을 하단에 배치

		// 전체 패널에 각 영역 배치
		panel.add(inputPanel, BorderLayout.WEST);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(listPanel, BorderLayout.CENTER);

		return panel;
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
