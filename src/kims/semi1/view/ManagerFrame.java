package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import kims.semi1.controller.ManagerController;
import kims.semi1.dao.ClassScheduleDao;
import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Course;
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Enrollment;
import kims.semi1.model.Professor;

// 1. 매니저관리 홈화면 
public class ManagerFrame extends JFrame {
	ManagerController managerController;

	public ManagerFrame() {
		setTitle("학사관리시스템(교직원)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400); // 홈 화면 크기 줄임
		setLocationRelativeTo(null);

		JPanel homePanel = new JPanel(new BorderLayout());

		// 상단 패널 (제목 + 로그아웃 버튼)
		JPanel topPanel = new JPanel(new BorderLayout());
		JLabel titleLabel = new JLabel("학사관리시스템(교직원)", JLabel.LEFT);
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		topPanel.setBackground(Color.BLACK);
		topPanel.setPreferredSize(new Dimension(400, 50));
		titleLabel.setForeground(Color.WHITE);

		JButton btnLogout = new JButton("로그아웃");
		btnLogout.setPreferredSize(new Dimension(100, 30)); // 버튼 크기 조정
		btnLogout.setFont(new Font("맑은 고딕", Font.BOLD, 13));

		topPanel.add(titleLabel, BorderLayout.WEST);
		topPanel.add(btnLogout, BorderLayout.EAST);
		homePanel.add(topPanel, BorderLayout.NORTH);

		// 메뉴 버튼 패널
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JButton btnSchedule = new JButton("시간표 관리");
		JButton btnProfessor = new JButton("교수 정보 관리");
		JButton btnStudent = new JButton("학생 정보 관리");
		JButton btnCourse = new JButton("강의 정보 조회");

		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 14);
		btnSchedule.setFont(buttonFont);
		btnProfessor.setFont(buttonFont);
		btnStudent.setFont(buttonFont);
		btnCourse.setFont(buttonFont);

		buttonPanel.add(btnSchedule);
		buttonPanel.add(btnProfessor);
		buttonPanel.add(btnStudent);
		buttonPanel.add(btnCourse);

		homePanel.add(buttonPanel, BorderLayout.CENTER);

		// 버튼 이벤트 (시간표 관리 화면으로 이동)
		btnSchedule.addActionListener(e -> {
			new ManagerScheduleFrame();
			dispose();
		});
		btnProfessor.addActionListener(e -> {// 교수 관리 화면으로 이동
			new ManagerProfessorFrame();
			dispose();
		});
		btnStudent.addActionListener(e -> {// 학생 관리 화면으로 이동
			new ManagerStudentFrame();
			dispose();
		});

		btnCourse.addActionListener(e -> {// 강의 관리 화면으로 이동
			new ManagerCourseFrame();
			dispose();
		});

		add(homePanel);
		setVisible(true);
	}

	/// 2.매니저 시간표관리
	public class ManagerScheduleFrame extends JFrame {
		private final ClassScheduleDao classScheduleDao;
		private DefaultTableModel tableModel;
		private JTable scheduleTable;
		private JTextField txtLectureID, txtDay, txtStartTime, txtEndTime, txtUnit;

		public ManagerScheduleFrame() {
			classScheduleDao = new ClassScheduleDao();
			managerController = new ManagerController();
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel schedulePanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼)
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("시간표관리");
			JButton btnProfessor = new JButton("교수정보관리");
			JButton btnStudent = new JButton("학생정보관리");
			JButton btnCourse = new JButton("강의정보");
			JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (시간표관리 버튼 색상 변경)
			btnSchedule.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			schedulePanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("강의 시간표 등록"));
			formPanel.setPreferredSize(new Dimension(350, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 13);
			Font fieldFont = new Font("맑은 고딕", Font.BOLD, 12);

			txtLectureID = new JTextField(15); // 크기 확장
			txtDay = new JTextField(15);
			txtStartTime = new JTextField(15);
			txtEndTime = new JTextField(15);
			txtUnit = new JTextField(15);
			JButton btnRegister = new JButton("등록하기");

			txtLectureID.setFont(fieldFont);
			txtDay.setFont(fieldFont);
			txtStartTime.setFont(fieldFont);
			txtEndTime.setFont(fieldFont);
			txtUnit.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("강의 ID:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtLectureID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("요일:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtDay, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			formPanel.add(new JLabel("시작시간:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtStartTime, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			formPanel.add(new JLabel("종료시간:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtEndTime, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(new JLabel("강의실번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtUnit, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "시간표ID", "강의명", "요일", "시작시간", "종료시간", "교수이름" };
			tableModel = new DefaultTableModel(columnNames, 0);
			scheduleTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(scheduleTable);

			// 전체조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체시간표조회");
			JButton btnDelete = new JButton("삭제");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			schedulePanel.add(centerPanel, BorderLayout.CENTER);
			schedulePanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnHome.addActionListener(e -> {
				new ManagerFrame();
				dispose();
			});

			btnProfessor.addActionListener(e -> {
				new ManagerProfessorFrame();
				dispose();
			});

			btnStudent.addActionListener(e -> {
				new ManagerStudentFrame();
				dispose();
			});

			btnCourse.addActionListener(e -> {// 강의 관리 화면으로 이동
				new ManagerCourseFrame();
				dispose();
			});
			btnDelete.addActionListener(e->{
	            int selectedRow = scheduleTable.getSelectedRow(); // 선택된 행 가져오기

                if (selectedRow != -1) { // 선택된 행이 있다면
                	int classScheduleId =(int) tableModel.getValueAt(selectedRow, 0);
                	managerController.deleteClassSchedule(classScheduleId);
                    tableModel.removeRow(selectedRow); // 행 삭제
                }
			});
			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<CourseInfo> courseInfos = classScheduleDao.findCourseInfos();
				courseInfos.stream().forEach(t -> {
					ClassSchedule cs = t.getClassSchedule();
					Course c = t.getCourse();
					Professor p = t.getProfessor();
					Object[] row = { cs.getScheduleId(), c.getName(), cs.getDayOfWeek() + "요일", cs.getStartTime(),
							cs.getEndTime(), p.getName() };
					tableModel.addRow(row);
				});
			});
			btnRegister.addActionListener(e -> {
				int courseID = Integer.parseInt(txtLectureID.getText());
				String dayOfWeek = txtDay.getText();
				String startTime = txtStartTime.getText();
				String endTime = txtEndTime.getText();
				String unit = txtUnit.getText();
				managerController.saveClassScheduleInfo(courseID, dayOfWeek, startTime, endTime, unit);
			});
			add(schedulePanel);
			setVisible(true);
		}
	}

	/// 3.교수정보관리(조회/등록)
	public class ManagerProfessorFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable professorTable;
		private JTextField txtName, txtPhone, txtBirthday, txtEmail, txtDepartmentID;

		public ManagerProfessorFrame() { // 상단고정
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel professorPanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼) - 고정
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("시간표관리");
			JButton btnProfessor = new JButton("교수정보관리");
			JButton btnStudent = new JButton("학생정보관리");
			JButton btnCourse = new JButton("강의정보");
			JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 12);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnProfessor.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			professorPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("교수 등록"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 13);
			Font fieldFont = new Font("맑은 고딕", Font.BOLD, 12);

			txtName = new JTextField(15); // 크기 확장
			txtPhone = new JTextField(15);
			txtBirthday = new JTextField(15);
			txtEmail = new JTextField(15);
			txtDepartmentID = new JTextField(15);
			JButton btnRegister = new JButton("등록하기");

			txtName.setFont(fieldFont);
			txtPhone.setFont(fieldFont);
			txtBirthday.setFont(fieldFont);
			txtEmail.setFont(fieldFont);
			txtDepartmentID.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("교수 이름:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("전화번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtPhone, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			formPanel.add(new JLabel("생년월일:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBirthday, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			formPanel.add(new JLabel("이메일:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtEmail, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(new JLabel("학과번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtDepartmentID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "교수ID", "교수이름", "전화번호", "생년월일", "이메일", "채용일자" };
			tableModel = new DefaultTableModel(columnNames, 0);
			professorTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(professorTable);

			// 전체조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체교수조회");
			JButton btnDelete = new JButton("삭제");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			professorPanel.add(centerPanel, BorderLayout.CENTER);
			professorPanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnHome.addActionListener(e -> {
				new ManagerFrame();
				dispose();
			});

			btnSchedule.addActionListener(e -> {
				new ManagerScheduleFrame();
				dispose();
			});

			btnStudent.addActionListener(e -> {
				new ManagerStudentFrame();
				dispose();
			});

			btnCourse.addActionListener(e -> {// 강의 관리 화면으로 이동
				new ManagerCourseFrame();
				dispose();
			});

			add(professorPanel);
			setVisible(true);
		}
	}

	/// 4.학생정보관리(조회/등록)
	public class ManagerStudentFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable studentTable;
		private JTextField txtName, txtPhone, txtBirthDay, txtEmail, txtDepartmentID;

		public ManagerStudentFrame() {// 상단고정
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel studentPanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼) - 고정
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("시간표관리");
			JButton btnProfessor = new JButton("교수정보관리");
			JButton btnStudent = new JButton("학생정보관리");
			JButton btnCourse = new JButton("강의정보");
			JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 12);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnStudent.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			studentPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("학생 등록"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 13);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 12);

			txtName = new JTextField(15); // 크기 확장
			txtPhone = new JTextField(15);
			txtBirthDay = new JTextField(15);
			txtEmail = new JTextField(15);
			txtDepartmentID = new JTextField(15);
			JButton btnRegister = new JButton("등록하기");

			txtName.setFont(fieldFont);
			txtPhone.setFont(fieldFont);
			txtBirthDay.setFont(fieldFont);
			txtEmail.setFont(fieldFont);
			txtDepartmentID.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("학생 이름:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("전화번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtPhone, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			formPanel.add(new JLabel("생년월일:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBirthDay, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			formPanel.add(new JLabel("이메일:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtEmail, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(new JLabel("학과번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtDepartmentID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "학번", "학생이름", "전화번호", "생년월일", "이메일", "입학년도" };
			tableModel = new DefaultTableModel(columnNames, 0);
			studentTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(studentTable);

			// 전체조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체학생조회");
			JButton btnDelete = new JButton("삭제");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			studentPanel.add(centerPanel, BorderLayout.CENTER);
			studentPanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnHome.addActionListener(e -> {
				new ManagerFrame();
				dispose();
			});

			btnSchedule.addActionListener(e -> {
				new ManagerScheduleFrame();
				dispose();
			});

			btnProfessor.addActionListener(e -> {
				new ManagerProfessorFrame();
				dispose();
			});

			btnCourse.addActionListener(e -> {// 강의 관리 화면으로 이동
				new ManagerCourseFrame();
				dispose();
			});

			add(studentPanel);
			setVisible(true);
		}
	}

	/// 5.강의정보관리
	public class ManagerCourseFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable CourseTable;
		private JTextField txtLectureID, txtDay, txtStartTime, txtEndTime;

		public ManagerCourseFrame() {// 상단고정
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel coursePanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼) - 고정
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("시간표관리");
			JButton btnProfessor = new JButton("교수정보관리");
			JButton btnStudent = new JButton("학생정보관리");
			JButton btnCourse = new JButton("강의정보");
			JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 12);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnCourse.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			coursePanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("강의정보 조회"));
			formPanel.setPreferredSize(new Dimension(380, 400)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 20); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 13);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 12);

			txtLectureID = new JTextField(15); // 크기 확장
			txtDay = new JTextField(15);
			txtStartTime = new JTextField(15);
			txtEndTime = new JTextField(15);
			JButton btnSearch = new JButton("조회하기");

			txtLectureID.setFont(fieldFont);
			txtDay.setFont(fieldFont);
			txtStartTime.setFont(fieldFont);
			txtEndTime.setFont(fieldFont);
			btnSearch.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("강의실 번호:", JLabel.LEFT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtLectureID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			formPanel.add(btnSearch, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "강의실 번호", "강의명", "담당교수", "학과" };
			tableModel = new DefaultTableModel(columnNames, 0);
			CourseTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(CourseTable);

			// 전체시간표조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체강의조회");

			// -- <강의실관리 버튼 누르면 새 페이지로>
			JButton btnBuildingManage = new JButton("강의실관리");

			btnViewAll.setFont(labelFont);
			btnBuildingManage.setFont(labelFont);
			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnBuildingManage);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			coursePanel.add(centerPanel, BorderLayout.CENTER);
			coursePanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnHome.addActionListener(e -> {
				new ManagerFrame();
				dispose();
			});

			btnSchedule.addActionListener(e -> {
				new ManagerScheduleFrame();
				dispose();
			});

			btnProfessor.addActionListener(e -> {
				new ManagerProfessorFrame();
				dispose();
			});

			btnStudent.addActionListener(e -> {
				new ManagerStudentFrame();
				dispose();
			});

			btnBuildingManage.addActionListener(e -> {
				new ManagerBuildingFrame();
				dispose();
			});

			add(coursePanel);
			setVisible(true);
		}
	}

	/// 5-2.강의정보관리 버튼 누르면 ---- 강의실 관리로 이동(건물 조회 / 등록)
	public class ManagerBuildingFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable BuildingTable;
		private JTextField txtBuildingID, txtBuildingName;

		public ManagerBuildingFrame() { // 상단고정
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel BuildingPanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼) - 고정
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnPrevios = new JButton("이전화면으로 돌아가기");
			JButton btnBuilding = new JButton("건물 관리");
			JButton btnUnit = new JButton("강의실 관리");
			JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 12);
			btnPrevios.setFont(tabFont);
			btnBuilding.setFont(tabFont);
			btnUnit.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnBuilding.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnPrevios);
			tabPanel.add(btnBuilding);
			tabPanel.add(btnUnit);
			tabPanel.add(btnLogout);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			BuildingPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("건물 등록 및 조회"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 13);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 12);

			txtBuildingID = new JTextField(10); // 크기 확장
			txtBuildingName = new JTextField(10);

			JButton btnSearch = new JButton("조회하기");
			JButton btnRegister = new JButton("등록하기");

			txtBuildingID.setFont(fieldFont);
			txtBuildingName.setFont(fieldFont);

			btnSearch.setFont(labelFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("건물 번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("건물 이름:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(btnSearch, gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "건물 번호", "건물 이름" };
			tableModel = new DefaultTableModel(columnNames, 0);
			BuildingTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(BuildingTable);

			// 전체시간표조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체강의실 조회");

			btnViewAll.setFont(labelFont);
			buttonPanel.add(btnViewAll);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			BuildingPanel.add(centerPanel, BorderLayout.CENTER);
			BuildingPanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnPrevios.addActionListener(e -> {
				new ManagerCourseFrame();
				dispose();
			});

			btnUnit.addActionListener(e -> {
				new ManagerUnitFrame();
				dispose();
			});

			add(BuildingPanel);
			setVisible(true);
		}
	}

	/// 5-3.강의실 관리 안에서 (강의실 조회 / 등록 / 삭제)
	public class ManagerUnitFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable UnitTable;
		private JTextField txtBuildingID, txtUnitID;

		public ManagerUnitFrame() {
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel BuildingPanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼) - 고정
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnPrevios = new JButton("이전화면으로 돌아가기");
			JButton btnBuilding = new JButton("건물 관리");
			JButton btnUnit = new JButton("강의실 관리");
			JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 12);
			btnPrevios.setFont(tabFont);
			btnBuilding.setFont(tabFont);
			btnUnit.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnUnit.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnPrevios);
			tabPanel.add(btnBuilding);
			tabPanel.add(btnUnit);
			tabPanel.add(btnLogout);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			BuildingPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder("강의실 등록 및 조회"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 13);
			Font fieldFont = new Font("맑은 고딕", Font.BOLD, 12);

			txtBuildingID = new JTextField(10); // 크기 확장
			txtUnitID = new JTextField(10);

			JButton btnSearch = new JButton("조회하기");
			JButton btnRegister = new JButton("등록하기");

			txtBuildingID.setFont(fieldFont);
			txtUnitID.setFont(fieldFont);

			btnSearch.setFont(labelFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("건물 번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("강의실 번호:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtUnitID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 5;
			formPanel.add(btnSearch, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "건물 번호", "건물명", "강의실 번호" };
			tableModel = new DefaultTableModel(columnNames, 0);
			UnitTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(UnitTable);

			// 전체시간표조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			JButton btnViewAll = new JButton("전체강의실 조회");
			JButton btnDelete = new JButton("삭제");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);
			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			BuildingPanel.add(centerPanel, BorderLayout.CENTER);
			BuildingPanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnPrevios.addActionListener(e -> {
				new ManagerCourseFrame();
				dispose();
			});

			btnBuilding.addActionListener(e -> {
				new ManagerBuildingFrame();
				dispose();
			});

			add(BuildingPanel);
			setVisible(true);
		}
	}

	public void setManagerController(ManagerController managerController) {
		this.managerController = managerController;
	}

	//// 실행 메소드
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ManagerFrame::new);
	}

}