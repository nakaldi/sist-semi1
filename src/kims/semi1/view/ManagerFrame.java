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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kims.semi1.config.DBConnector;
import kims.semi1.controller.ManagerController;
import kims.semi1.dao.ClassScheduleDao;
import kims.semi1.dao.GenericDao;
import kims.semi1.model.Building;
import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Course;
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Department;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;
import kims.semi1.model.Unit;

// 1. 매니저관리 홈화면 
public class ManagerFrame extends JFrame {
	ManagerController managerController;
	GenericDao genericDao;

	public ManagerFrame() {
		managerController = new ManagerController();
		setTitle("학사관리시스템(교직원)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400); // 홈 화면 크기 줄임
		setLocationRelativeTo(null);

		JPanel homePanel = new JPanel(new BorderLayout());

		// 상단 패널 (제목 + 로그아웃 버튼)
//		JPanel topPanel = new JPanel(new BorderLayout());
//		JLabel titleLabel = new JLabel("학사관리시스템(교직원)", JLabel.LEFT);
//		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
//		topPanel.setBackground(Color.DARK_GRAY);
//		topPanel.setPreferredSize(new Dimension(400, 50));
//		titleLabel.setForeground(Color.WHITE);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setPreferredSize(new Dimension(400, 50));

		// 제목 라벨을 감싸는 패널 (가운데 정렬을 위해 추가)
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.setBackground(Color.DARK_GRAY);

		JLabel titleLabel = new JLabel("학사관리시스템(교직원)", JLabel.CENTER);
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		titleLabel.setForeground(Color.WHITE);

		titlePanel.add(titleLabel, BorderLayout.CENTER);
		topPanel.add(titlePanel, BorderLayout.CENTER);

		topPanel.add(titleLabel, BorderLayout.CENTER);
		homePanel.add(topPanel, BorderLayout.NORTH);

		/*
		 * 로그아웃 버튼 JButton btnLogout = new JButton("로그아웃");
		 * btnLogout.setPreferredSize(new Dimension(100, 30)); // 버튼 크기 조정
		 * btnLogout.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		 */

		// topPanel.add(btnLogout, BorderLayout.EAST);

		// 메뉴 버튼 패널
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JButton btnSchedule = new JButton("시간표 관리");
		JButton btnProfessor = new JButton("교수 정보 관리");
		JButton btnStudent = new JButton("학생 정보 관리");
		JButton btnCourse = new JButton("강의 정보 조회");

		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 16);
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
			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (시간표관리 버튼 색상 변경)
			btnSchedule.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			schedulePanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "강의 시간표 등록",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));

			formPanel.setPreferredSize(new Dimension(410, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 14);

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
			JLabel lblID = new JLabel("강의 ID:", JLabel.RIGHT);
			lblID.setFont(labelFont);
			formPanel.add(lblID, gbc);
			gbc.gridx = 1;
			formPanel.add(txtLectureID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			JLabel lblDay = new JLabel("요일:", JLabel.RIGHT);
			lblDay.setFont(labelFont);
			formPanel.add(lblDay, gbc);
			gbc.gridx = 1;
			formPanel.add(txtDay, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			JLabel lblTime = new JLabel("시작시간:", JLabel.RIGHT);
			lblTime.setFont(labelFont);
			formPanel.add(lblTime, gbc);
			gbc.gridx = 1;
			formPanel.add(txtStartTime, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			JLabel lblTimeend = new JLabel("종료시간:", JLabel.RIGHT);
			formPanel.add(lblTimeend, gbc);
			lblTimeend.setFont(labelFont);
			formPanel.add(lblTimeend, gbc);
			gbc.gridx = 1;
			formPanel.add(txtEndTime, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			JLabel lblUnitID = new JLabel("강의실번호:", JLabel.RIGHT);
			formPanel.add(lblUnitID, gbc);
			lblUnitID.setFont(labelFont);
			formPanel.add(lblUnitID, gbc);

			gbc.gridx = 1;
			formPanel.add(txtUnit, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "시간표ID", "강의ID", "강의명", "요일", "수업시간", "교수이름" };
			tableModel = new DefaultTableModel(columnNames, 0);
			scheduleTable = new JTable(tableModel);

			// 테이블 칼럼명 글씨 크기 및 스타일 설정
			scheduleTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			scheduleTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			scheduleTable.setRowHeight(25); // 행 높이 조정

			JScrollPane scrollPane = new JScrollPane(scheduleTable);

			// 전체조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체시간표조회");
			JButton btnCourseId = new JButton("강의조회");
			JButton btnDelete = new JButton("삭제");

			btnCourseId.setFont(labelFont);
			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnCourseId);
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

			btnCourseId.addActionListener(e -> {
				new CourseFrame();

			});
			btnDelete.addActionListener(e -> {
				int selectedRow = scheduleTable.getSelectedRow(); // 선택된 행 가져오기

				if (selectedRow != -1) { // 선택된 행이 있다면
					int classScheduleId = (int) tableModel.getValueAt(selectedRow, 0);
					managerController.deleteClassSchedule(classScheduleId);
					tableModel.removeRow(selectedRow); // 행 삭제
					// 성공 메시지 창 띄우기
					JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "삭제가 실패했습니다", "삭제 실패", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<CourseInfo> courseInfos = classScheduleDao.findCourseInfos();
				courseInfos.stream().forEach(t -> {
					ClassSchedule cs = t.getClassSchedule();
					Course c = t.getCourse();
					Professor p = t.getProfessor();
					Object[] row = { cs.getScheduleId(), c.getCourseId(), c.getName(), cs.getDayOfWeek() + "요일",
							cs.getStartTime() + "~" + cs.getEndTime(), p.getName() };
					tableModel.addRow(row);
				});
			});

			// '등록이 되었습니다' 창 추가 부분
			btnRegister.addActionListener(e -> {
				try {
					int courseID = Integer.parseInt(txtLectureID.getText());
					String dayOfWeek = txtDay.getText();
					String startTime = txtStartTime.getText();
					String endTime = txtEndTime.getText();
					String unit = txtUnit.getText();

					if (managerController.saveVeiwClassScheduleInfo(courseID, dayOfWeek, startTime, endTime, unit)) {
						// 성공 메시지 창 띄우기
						JOptionPane.showMessageDialog(this, "등록이 되었습니다.", "등록 완료", JOptionPane.INFORMATION_MESSAGE);
					} else {
						// 성공 메시지 창 띄우기
						JOptionPane.showMessageDialog(this, "등록이 실패했습니다", "등록 실패", JOptionPane.INFORMATION_MESSAGE);
					}
					btnViewAll.doClick();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "입력 오류", "입력 오류", JOptionPane.ERROR_MESSAGE);
				}
			});

			add(schedulePanel);
			btnViewAll.doClick();
			setVisible(true);
		}
	}

	/// 2-1.강의번호
	public class CourseFrame extends JFrame {
		private final ClassScheduleDao classScheduleDao;
		private final GenericDao genericdao;
		private DefaultTableModel tableModel;
		private JTable scheduleTable;
		private JTextField txtCouseName, txtProfessor, txtStartTime, txtEndTime, txtUnit;

		public CourseFrame() {
			classScheduleDao = new ClassScheduleDao();
			managerController = new ManagerController();
			genericdao = new GenericDao();
			setTitle("학사관리시스템(교직원)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // 크기 유지
			setLocationRelativeTo(null);

			JPanel schedulePanel = new JPanel(new BorderLayout());

			// 상단 패널 (탭 메뉴 + 로그아웃 버튼)
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (시간표관리 버튼 색상 변경)

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			schedulePanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "강의 번호 조회",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));

			formPanel.setPreferredSize(new Dimension(410, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 14);

			txtCouseName = new JTextField(15); // 크기 확장
			txtProfessor = new JTextField(15);
			JButton btnRegister = new JButton("조회하기");

			txtCouseName.setFont(fieldFont);
			txtProfessor.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;

			JLabel lbCouseName = new JLabel("강의명:", JLabel.RIGHT);
			lbCouseName.setFont(labelFont);
			formPanel.add(lbCouseName, gbc);
			gbc.gridx = 1;
			formPanel.add(txtCouseName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			JLabel lbProfessor = new JLabel("담당교수:", JLabel.RIGHT);
			formPanel.add(lbProfessor, gbc);
			lbProfessor.setFont(labelFont);
			formPanel.add(lbProfessor, gbc);
			gbc.gridx = 1;
			formPanel.add(txtProfessor, gbc);

			gbc.gridx = 1;
			gbc.gridy = 3;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "강의ID", "강의명", "교수ID", "담당교수", "학과명" };
			tableModel = new DefaultTableModel(columnNames, 0);
			scheduleTable = new JTable(tableModel);

			// 테이블 칼럼명 글씨 크기 및 스타일 설정
			scheduleTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			scheduleTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			scheduleTable.setRowHeight(25); // 행 높이 조정

			JScrollPane scrollPane = new JScrollPane(scheduleTable);

			// 전체조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체강의조회");
			btnViewAll.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			schedulePanel.add(centerPanel, BorderLayout.CENTER);
			schedulePanel.add(buttonPanel, BorderLayout.SOUTH);

			// 버튼 이벤트 추가
			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<CourseInfo> courseInfos = classScheduleDao.findCousrseProfessorInfos();
				courseInfos.stream().forEach(t -> {
					ClassSchedule cs = t.getClassSchedule();
					Course c = t.getCourse();
					Professor p = t.getProfessor();
					Department d = t.getDepartment();
					Object[] row = { c.getCourseId(), c.getName(), p.getProfessorId(), p.getName(), d.getName() };
					tableModel.addRow(row);
				});
			});
			btnRegister.addActionListener(e -> {
				tableModel.setRowCount(0);
				if ("".equals(txtCouseName.getText()) && txtProfessor.getText() != null) {
					List<CourseInfo> courseInfos = classScheduleDao
							.findCousrseProfessorNameInfos("%" + txtProfessor.getText() + "%");
					courseInfos.stream().forEach(t -> {
						ClassSchedule cs = t.getClassSchedule();
						Course c = t.getCourse();
						Professor p = t.getProfessor();
						Department d = t.getDepartment();
						Object[] row = { c.getCourseId(), c.getName(), p.getProfessorId(), p.getName(), d.getName() };
						tableModel.addRow(row);
					});
				} else if ("".equals(txtProfessor.getText()) && txtCouseName.getText() != null) {
					List<CourseInfo> courseInfos = classScheduleDao
							.findCousrseProfessorInfos("%" + txtCouseName.getText() + "%");
					courseInfos.stream().forEach(t -> {
						ClassSchedule cs = t.getClassSchedule();
						Course c = t.getCourse();
						Professor p = t.getProfessor();
						Department d = t.getDepartment();
						Object[] row = { c.getCourseId(), c.getName(), p.getProfessorId(), p.getName(), d.getName() };
						tableModel.addRow(row);
					});
				} else {
					List<CourseInfo> courseInfos = classScheduleDao.findCousrseProfessorInfos(
							"%" + txtCouseName.getText() + "%", "%" + txtProfessor.getText() + "%");
					courseInfos.stream().forEach(t -> {
						ClassSchedule cs = t.getClassSchedule();
						Course c = t.getCourse();
						Professor p = t.getProfessor();
						Department d = t.getDepartment();
						Object[] row = { c.getCourseId(), c.getName(), p.getProfessorId(), p.getName(), d.getName() };
						tableModel.addRow(row);
					});
				}
			});
			add(schedulePanel);
			btnViewAll.doClick();
			setVisible(true);
		}
	}

	/// 3.교수정보관리(조회/등록)
	public class ManagerProfessorFrame extends JFrame {
		private final ClassScheduleDao classScheduleDao;
		private DefaultTableModel tableModel;
		private JTable professorTable;
		private JTextField txtName, txtPhone, txtBirthday, txtEmail, txtDepartmentID;

		public ManagerProfessorFrame() { // 상단고정
			classScheduleDao = new ClassScheduleDao();
			managerController = new ManagerController();
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
			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnProfessor.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			professorPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "교수 등록 관리",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));
			formPanel.setPreferredSize(new Dimension(400, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14); // JLabel 폰트 크기 설정
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 14); // 입력 필드 폰트 크기 설정

			txtName = new JTextField(15);
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
			JLabel lblName = new JLabel("교수 이름:", JLabel.RIGHT);
			lblName.setFont(labelFont);
			formPanel.add(lblName, gbc);
			gbc.gridx = 1;
			formPanel.add(txtName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			JLabel lblPhone = new JLabel("전화번호:", JLabel.RIGHT);
			lblPhone.setFont(labelFont);
			formPanel.add(lblPhone, gbc);
			gbc.gridx = 1;
			formPanel.add(txtPhone, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			JLabel lblBirthday = new JLabel("생년월일:", JLabel.RIGHT);
			lblBirthday.setFont(labelFont);
			formPanel.add(lblBirthday, gbc);
			gbc.gridx = 1;
			formPanel.add(txtBirthday, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			JLabel lblEmail = new JLabel("이메일:", JLabel.RIGHT);
			lblEmail.setFont(labelFont);
			formPanel.add(lblEmail, gbc);
			gbc.gridx = 1;
			formPanel.add(txtEmail, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			JLabel lblDepartment = new JLabel("학과번호:", JLabel.RIGHT);
			lblDepartment.setFont(labelFont);
			formPanel.add(lblDepartment, gbc);
			gbc.gridx = 1;
			formPanel.add(txtDepartmentID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "교수ID", "교수이름", "전화번호", "생년월일", "이메일", "채용일자" };
			tableModel = new DefaultTableModel(columnNames, 0);
			professorTable = new JTable(tableModel);

			// 테이블 칼럼명 글씨 크기 및 스타일 설정
			professorTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			professorTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			professorTable.setRowHeight(25); // 행 높이 조정

			JScrollPane scrollPane = new JScrollPane(professorTable);

			// 전체조회 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체교수조회");
			btnViewAll.setFont(labelFont);
			buttonPanel.add(btnViewAll);

			// 정렬 조정
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			professorPanel.add(centerPanel, BorderLayout.CENTER);
			professorPanel.add(buttonPanel, BorderLayout.SOUTH);

			add(professorPanel);
			setVisible(true);

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
			// 전체조회
			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<Professor> professorInfos = classScheduleDao.findProfessorInfos();
				professorInfos.stream().forEach(t -> {
					Object[] row = { t.getProfessorId(), t.getName(), t.getPhone(), t.getBirthDate(), t.getEmail(),
							t.getHireDate() };
					tableModel.addRow(row);
				});
			});
			// 등록기능
			btnRegister.addActionListener(e -> {
				String professorName = txtName.getText();
				String phone = txtPhone.getText();
				String birthDate = txtBirthday.getText();
				String email = txtEmail.getText();
				int department_id = Integer.parseInt(txtDepartmentID.getText());
				if (managerController.saveViewProfessorInfo(department_id, professorName, phone, birthDate, email,
						department_id)) {
					JOptionPane.showMessageDialog(this, "등록이 되었습니다.", "등록 완료", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 성공 메시지 창 띄우기
					JOptionPane.showMessageDialog(this, "등록이 실패했습니다", "등록 실패", JOptionPane.INFORMATION_MESSAGE);
				}
				btnViewAll.doClick();
			});
			btnViewAll.doClick();
			add(professorPanel);
			setVisible(true);
		}
	}

	/// 4.학생정보관리(조회/등록)
	public class ManagerStudentFrame extends JFrame {
		private final ClassScheduleDao classScheduleDao;
		private DefaultTableModel tableModel;
		private JTable studentTable;
		private JTextField txtName, txtPhone, txtBirthDay, txtEmail, txtDepartmentID;

		public ManagerStudentFrame() {// 상단고정
			classScheduleDao = new ClassScheduleDao();
			managerController = new ManagerController();
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
			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnStudent.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			studentPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "학생 등록 관리",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));
			formPanel.setPreferredSize(new Dimension(400, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 14);

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
			JLabel lblName = new JLabel("학생 이름:", JLabel.RIGHT);
			lblName.setFont(labelFont);
			formPanel.add(lblName, gbc);
			gbc.gridx = 1;
			formPanel.add(txtName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			JLabel lblPhone = new JLabel("전화번호:", JLabel.RIGHT);
			lblPhone.setFont(labelFont);
			formPanel.add(lblPhone, gbc);
			gbc.gridx = 1;
			formPanel.add(txtPhone, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			JLabel lblBirthDay = new JLabel("생년월일:", JLabel.RIGHT);
			lblBirthDay.setFont(labelFont);
			formPanel.add(lblBirthDay, gbc);
			gbc.gridx = 1;
			formPanel.add(txtBirthDay, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			JLabel lblEmail = new JLabel("이메일:", JLabel.RIGHT);
			lblEmail.setFont(labelFont);
			formPanel.add(lblEmail, gbc);
			gbc.gridx = 1;
			formPanel.add(txtEmail, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			JLabel lblDepartmentID = new JLabel("학과번호:", JLabel.RIGHT);
			lblDepartmentID.setFont(labelFont);
			formPanel.add(lblDepartmentID, gbc);
			gbc.gridx = 1;
			formPanel.add(txtDepartmentID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 6;
			formPanel.add(btnRegister, gbc);

			// 테이블 및 버튼 추가
			String[] columnNames = { "학번", "학생이름", "전화번호", "생년월일", "이메일", "입학년도" };
			tableModel = new DefaultTableModel(columnNames, 0);
			studentTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(studentTable);

			// 테이블 칼럼명 글씨 크기 및 스타일 설정
			studentTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			studentTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			studentTable.setRowHeight(25); // 행 높이 조정

			// 전체조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체학생조회");
			// JButton btnDelete = new JButton("삭제");

			btnViewAll.setFont(labelFont);
			// btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			// buttonPanel.add(btnDelete);

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
			// 전체조회
			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<Student> studentInfos = classScheduleDao.findStudentInfos();
				studentInfos.stream().forEach(t -> {
					Object[] row = { t.getStudentId(), t.getName(), t.getPhone(), t.getBirthDate(), t.getEmail(),
							t.getEnrollmentYear() };
					tableModel.addRow(row);
				});
			});
			// 등록기능
			btnRegister.addActionListener(e -> {
				String studentName = txtName.getText();
				String phone = txtPhone.getText();
				String birthDate = txtBirthDay.getText();
				String email = txtEmail.getText();
				int department_id = Integer.parseInt(txtDepartmentID.getText());
				if (managerController.insertViewStudentInfo(studentName, phone, birthDate, email, department_id)) {
					JOptionPane.showMessageDialog(this, "등록이 되었습니다.", "등록 완료", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 성공 메시지 창 띄우기
					JOptionPane.showMessageDialog(this, "등록이 실패했습니다", "등록 실패", JOptionPane.INFORMATION_MESSAGE);
				}
				btnViewAll.doClick();
			});

			add(studentPanel);
			btnViewAll.doClick();
			setVisible(true);
		}
	}

	/// 5.강의정보관리
	public class ManagerCourseFrame extends JFrame {
		private final ClassScheduleDao classScheduleDao;
		private DefaultTableModel tableModel;
		private JTable CourseTable;
		private JTextField txtLectureID, txtDay, txtStartTime, txtEndTime;

		public ManagerCourseFrame() {// 상단고정
			classScheduleDao = new ClassScheduleDao();
			managerController = new ManagerController();
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
			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnCourse.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			coursePanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "강의정보 조회",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));
			formPanel.setPreferredSize(new Dimension(410, 400)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 20); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 14);

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
			JLabel lblID = new JLabel("강의실 번호:", JLabel.RIGHT);
			lblID.setFont(labelFont);
			formPanel.add(lblID, gbc);
			gbc.gridx = 1;
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

			// 테이블 칼럼명 글씨 크기 및 스타일 설정
			CourseTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			CourseTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			CourseTable.setRowHeight(25); // 행 높이 조정

			// 전체조회 & 삭제 버튼
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
			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<CourseInfo> courseInfos = classScheduleDao.findCourseInfos();
				courseInfos.stream().forEach(t -> {
					Unit u = t.getUnit();
					Department d = t.getDepartment();
					Professor p = t.getProfessor();
					Course c = t.getCourse();
					Object[] row = { u.getUnitId(), c.getName(), p.getName(), d.getName() };
					tableModel.addRow(row);
				});
			});
			btnSearch.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<CourseInfo> courseInfos = classScheduleDao.findClassRoomInfos("%" + txtLectureID.getText() + "%");
				courseInfos.stream().forEach(t -> {
					Unit u = t.getUnit();
					Department d = t.getDepartment();
					Professor p = t.getProfessor();
					Course c = t.getCourse();
					Object[] row = { u.getUnitId(), c.getName(), p.getName(), d.getName() };
					tableModel.addRow(row);
				});

			});
			add(coursePanel);
			btnViewAll.doClick();
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
			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnPrevios.setFont(tabFont);
			btnBuilding.setFont(tabFont);
			btnUnit.setFont(tabFont);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnBuilding.setBackground(Color.LIGHT_GRAY);
			tabPanel.add(btnPrevios);
			tabPanel.add(btnBuilding);
			tabPanel.add(btnUnit);
			// tabPanel.add(btnLogout);

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			BuildingPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "건물 등록 및 조회",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));
			formPanel.setPreferredSize(new Dimension(450, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14);
			Font fieldFont = new Font("맑은 고딕", Font.PLAIN, 14);

			txtBuildingID = new JTextField(15); // 크기 확장
			txtBuildingName = new JTextField(15);

			JButton btnSearch = new JButton("조회하기");
			JButton btnRegister = new JButton("등록하기");

			txtBuildingID.setFont(fieldFont);
			txtBuildingName.setFont(fieldFont);

			btnSearch.setFont(labelFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			JLabel lblBuildingID = new JLabel("건물 번호:", JLabel.RIGHT);
			lblBuildingID.setFont(labelFont);
			formPanel.add(lblBuildingID, gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			JLabel lblBuildingName = new JLabel("건물 이름:", JLabel.RIGHT);
			lblBuildingName.setFont(labelFont);
			formPanel.add(lblBuildingName, gbc);
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

			BuildingTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			BuildingTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			BuildingTable.setRowHeight(25); // 행 높이 조정

			// 조회 & 삭제 버튼
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("전체건물 조회");

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

			// 등록기능
			btnRegister.addActionListener(e -> {
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					int buildingId = Integer.parseInt(txtBuildingID.getText());
					String buildingName = txtBuildingName.getText();

					conn = DBConnector.getConnection();

					// 기존 건물번호가 있는지 확인
					String buildingIDSearchsql = "SELECT COUNT(*) FROM buildings WHERE building_id = ?";
					ps = conn.prepareStatement(buildingIDSearchsql);
					ps.setInt(1, buildingId);
					rs = ps.executeQuery();

					if (rs.next() && rs.getInt(1) > 0) {
						// 이미 존재하는 건물 번호일 경우
						JOptionPane.showMessageDialog(this, "이미 건물번호가 등록되어있습니다. 다른 번호를 입력해주세요.", "등록 실패",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// 새로운 건물 등록
						String buildingSavesql = "INSERT INTO buildings (building_id, name) VALUES (?, ?)";
						ps = conn.prepareStatement(buildingSavesql);
						ps.setInt(1, buildingId);
						ps.setString(2, buildingName);
						ps.executeUpdate();

						JOptionPane.showMessageDialog(this, "등록이 완료되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "건물 번호는 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {
					DBConnector.close(conn, ps, rs);
				}
			});

			// 전체 건물 조회
			btnViewAll.addActionListener(e -> {
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					conn = DBConnector.getConnection();
					String buildingSearchsql = "select * from buildings";

					ps = conn.prepareStatement(buildingSearchsql);
					rs = ps.executeQuery();

					tableModel.setRowCount(0);

					while (rs.next()) {
						int buildingId = rs.getInt("building_id");
						String buildingName = rs.getString("name");
						Object[] row = { buildingId, buildingName };
						tableModel.addRow(row);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {
					DBConnector.close(conn, ps, rs);
				}
			});

			// 특정 건물 조회 기능
			btnSearch.addActionListener(e -> {
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					int buildingId = Integer.parseInt(txtBuildingID.getText());
					tableModel.setRowCount(0); // 기존 테이블 초기화

					conn = DBConnector.getConnection();
					String buildingSearchsql = "select * from buildings where building_id = ?";

					ps = conn.prepareStatement(buildingSearchsql);
					ps.setInt(1, buildingId);
					rs = ps.executeQuery();

					if (rs.next()) {
						do {
							String buildingName = rs.getString("name");
							Object[] row = { buildingId, buildingName };
							tableModel.addRow(row);
						} while (rs.next());
					} else {
						// 검색된 건물이 없을 경우 팝업 표시
						JOptionPane.showMessageDialog(this, "검색된 건물이 없습니다.", "검색 결과", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "올바른 건물 번호를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {
					DBConnector.close(conn, ps, rs);
				}

			});

			add(BuildingPanel);
			btnViewAll.doClick();
			setVisible(true);
		}
	}

	/// 5-3.강의실 관리 안에서 (강의실 조회 / 등록 / 삭제)
	public class ManagerUnitFrame extends JFrame {
		private final ClassScheduleDao classScheduleDao;
		private DefaultTableModel tableModel;
		private JTable UnitTable;
		private JTextField txtBuildingID, txtUnitID;

		public ManagerUnitFrame() {
			classScheduleDao = new ClassScheduleDao();
			managerController = new ManagerController();
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
			// JButton btnLogout = new JButton("로그아웃");

			// 로그아웃 버튼 크기 통일 - 고정
			// btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("맑은 고딕", Font.BOLD, 13);
			btnPrevios.setFont(tabFont);
			btnBuilding.setFont(tabFont);
			btnUnit.setFont(tabFont);
			// btnLogout.setFont(tabFont);

			// 현재 페이지 표시 (버튼 색상 변경) - 고정
			btnUnit.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnPrevios);
			tabPanel.add(btnBuilding);
			tabPanel.add(btnUnit);
			// tabPanel.add(btnLogout);

			// topPanel.add(btnLogout, BorderLayout.EAST);
			topPanel.add(tabPanel, BorderLayout.WEST);
			BuildingPanel.add(topPanel, BorderLayout.NORTH);

			// 입력 폼 패널
			JPanel formPanel = new JPanel(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "강의실 등록 및 조회",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("맑은 고딕", Font.BOLD, 18) // formPanel
																														// 제목
																														// 글씨
																														// 크기
																														// 적용
			));
			formPanel.setPreferredSize(new Dimension(450, 300)); // 크기 확장
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // 간격 조정
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("맑은 고딕", Font.BOLD, 14);
			Font fieldFont = new Font("맑은 고딕", Font.BOLD, 14);

			txtBuildingID = new JTextField(15); // 크기 확장
			txtUnitID = new JTextField(15);

			JButton btnSearch = new JButton("조회하기");
			JButton btnRegister = new JButton("등록하기");

			txtBuildingID.setFont(fieldFont);
			txtUnitID.setFont(fieldFont);

			btnSearch.setFont(labelFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			JLabel lblBuildingID = new JLabel("건물 번호:", JLabel.RIGHT);
			lblBuildingID.setFont(labelFont);
			formPanel.add(lblBuildingID, gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			JLabel lblUnitID = new JLabel("강의실 번호:", JLabel.RIGHT);
			lblUnitID.setFont(labelFont);
			formPanel.add(lblUnitID, gbc);
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

			UnitTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
			// 테이블 내용 글씨 크기 설정
			UnitTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			UnitTable.setRowHeight(25); // 행 높이 조정

			// 전체조회 & 삭제 버튼
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

			btnViewAll.addActionListener(e -> {
				tableModel.setRowCount(0);
				List<CourseInfo> unitBuildingInfos = classScheduleDao.findUnitBuildingInfos();
				unitBuildingInfos.stream().forEach(t -> {
					Building b = t.getBuilding();
					Unit u = t.getUnit();
					Object[] row = { b.getBuildingId(), b.getName(), u.getUnitId() };
					tableModel.addRow(row);
				});
			});

			btnDelete.addActionListener(e -> {
				int selectedRow = UnitTable.getSelectedRow(); // 선택된 행 가져오기
				if (selectedRow != -1) { // 선택된 행이 있다면
					String UnitId = tableModel.getValueAt(selectedRow, 0) + "";
					managerController.deleteVeiwUnit(UnitId);
					tableModel.removeRow(selectedRow); // 행 삭제
				} else {
					JOptionPane.showMessageDialog(BuildingPanel, "삭제실패.", "확인", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			btnSearch.addActionListener(e -> {
				tableModel.setRowCount(0);
				if ("".equals(txtBuildingID.getText().trim()) && txtUnitID.getText() != null) {
					List<CourseInfo> unitBuildingInfos = classScheduleDao
							.findUnitInfos("%" + txtUnitID.getText() + "%");
					unitBuildingInfos.stream().forEach(t -> {
						Building b = t.getBuilding();
						Unit u = t.getUnit();
						Object[] row = { b.getBuildingId(), b.getName(), u.getUnitId() };
						tableModel.addRow(row);
					});

				} else if ("".equals(txtUnitID.getText().trim()) && txtBuildingID.getText() != null) {
					List<CourseInfo> unitBuildingInfos = classScheduleDao
							.findBuildingInfos(Integer.parseInt(txtBuildingID.getText()));
					unitBuildingInfos.stream().forEach(t -> {
						Building b = t.getBuilding();
						Unit u = t.getUnit();
						Object[] row = { b.getBuildingId(), b.getName(), u.getUnitId() };
						tableModel.addRow(row);
					});

				} else {
					List<CourseInfo> unitBuildingInfos = classScheduleDao
							.findUnitBuildingInfos(Integer.parseInt(txtBuildingID.getText()), txtUnitID.getText());
					unitBuildingInfos.stream().forEach(t -> {
						Building b = t.getBuilding();
						Unit u = t.getUnit();
						Object[] row = { b.getBuildingId(), b.getName(), u.getUnitId() };
						tableModel.addRow(row);
					});
				}
			});

			btnRegister.addActionListener(e -> {
				int buildingId = Integer.parseInt(txtBuildingID.getText());
				String unitId = txtUnitID.getText();
				if (managerController.insertVeiwUnit(buildingId, unitId)) {
					JOptionPane.showMessageDialog(BuildingPanel, "등록되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(BuildingPanel, "등록실패했습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			add(BuildingPanel);
			btnViewAll.doClick();
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