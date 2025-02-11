package kims.semi1.view;

import java.awt.*;
import javax.swing.*;

import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;

import kims.semi1.controller.ManagerController;

// 1. �Ŵ������� Ȩȭ�� 
public class ManagerFrame extends JFrame {

	ManagerController managerController;

	public ManagerFrame() {
		setTitle("�л�����ý���(������)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400); // Ȩ ȭ�� ũ�� ����
		setLocationRelativeTo(null);

		JPanel homePanel = new JPanel(new BorderLayout());

		// ��� �г� (���� + �α׾ƿ� ��ư)
		JPanel topPanel = new JPanel(new BorderLayout());
		JLabel titleLabel = new JLabel("�л�����ý���(������)", JLabel.LEFT);
		titleLabel.setFont(new Font("���� ���", Font.BOLD, 15));
		topPanel.setBackground(Color.BLACK);
		topPanel.setPreferredSize(new Dimension(400, 50));
		titleLabel.setForeground(Color.WHITE);

		JButton btnLogout = new JButton("�α׾ƿ�");
		btnLogout.setPreferredSize(new Dimension(100, 30)); // ��ư ũ�� ����
		btnLogout.setFont(new Font("���� ���", Font.BOLD, 13));

		topPanel.add(titleLabel, BorderLayout.WEST);
		topPanel.add(btnLogout, BorderLayout.EAST);
		homePanel.add(topPanel, BorderLayout.NORTH);

		// �޴� ��ư �г�
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JButton btnSchedule = new JButton("�ð�ǥ ����");
		JButton btnProfessor = new JButton("���� ���� ����");
		JButton btnStudent = new JButton("�л� ���� ����");
		JButton btnCourse = new JButton("���� ���� ��ȸ");

		Font buttonFont = new Font("���� ���", Font.BOLD, 14);
		btnSchedule.setFont(buttonFont);
		btnProfessor.setFont(buttonFont);
		btnStudent.setFont(buttonFont);
		btnCourse.setFont(buttonFont);

		buttonPanel.add(btnSchedule);
		buttonPanel.add(btnProfessor);
		buttonPanel.add(btnStudent);
		buttonPanel.add(btnCourse);

		homePanel.add(buttonPanel, BorderLayout.CENTER);

		// ��ư �̺�Ʈ (�ð�ǥ ���� ȭ������ �̵�)
		btnSchedule.addActionListener(e -> {
			new ManagerScheduleFrame();
			dispose();
		});
		btnProfessor.addActionListener(e -> {// ���� ���� ȭ������ �̵�
			new ManagerProfessorFrame();
			dispose();
		});
		btnStudent.addActionListener(e -> {// �л� ���� ȭ������ �̵�
			new ManagerStudentFrame();
			dispose();
		});

		btnCourse.addActionListener(e -> {// ���� ���� ȭ������ �̵�
			new ManagerCourseFrame();
			dispose();
		});

		add(homePanel);
		setVisible(true);
	}

	/// 2.�Ŵ��� �ð�ǥ����
	public class ManagerScheduleFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable scheduleTable;
		private JTextField txtLectureID, txtDay, txtStartTime, txtEndTime;

		public ManagerScheduleFrame() {
			setTitle("�л�����ý���(������)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // ũ�� ����
			setLocationRelativeTo(null);

			JPanel schedulePanel = new JPanel(new BorderLayout());

			// ��� �г� (�� �޴� + �α׾ƿ� ��ư)
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("�ð�ǥ����");
			JButton btnProfessor = new JButton("������������");
			JButton btnStudent = new JButton("�л���������");
			JButton btnCourse = new JButton("��������");
			JButton btnLogout = new JButton("�α׾ƿ�");

			// �α׾ƿ� ��ư ũ�� ����
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("���� ���", Font.BOLD, 13);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// ���� ������ ǥ�� (�ð�ǥ���� ��ư ���� ����)
			btnSchedule.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			schedulePanel.add(topPanel, BorderLayout.NORTH);

			// �Է� �� �г�
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("���� �ð�ǥ ���"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // ũ�� Ȯ��
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // ���� ����
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("���� ���", Font.BOLD, 13);
			Font fieldFont = new Font("���� ���", Font.BOLD, 12);

			txtLectureID = new JTextField(15); // ũ�� Ȯ��
			txtDay = new JTextField(15);
			txtStartTime = new JTextField(15);
			txtEndTime = new JTextField(15);
			JButton btnRegister = new JButton("���");

			txtLectureID.setFont(fieldFont);
			txtDay.setFont(fieldFont);
			txtStartTime.setFont(fieldFont);
			txtEndTime.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("���� ID:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtLectureID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("����:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtDay, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			formPanel.add(new JLabel("���۽ð�:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtStartTime, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			formPanel.add(new JLabel("����ð�:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtEndTime, gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			formPanel.add(btnRegister, gbc);

			// ���̺� �� ��ư �߰�
			String[] columnNames = { "�ð�ǥID", "���Ǹ�", "����", "���۽ð�", "����ð�", "�����̸�" };
			tableModel = new DefaultTableModel(columnNames, 0);
			scheduleTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(scheduleTable);

			// ��ü��ȸ & ���� ��ư
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("��ü�ð�ǥ��ȸ");
			JButton btnDelete = new JButton("����");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// ���� ����
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			schedulePanel.add(centerPanel, BorderLayout.CENTER);
			schedulePanel.add(buttonPanel, BorderLayout.SOUTH);

			// ��ư �̺�Ʈ �߰�
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

			btnCourse.addActionListener(e -> {// ���� ���� ȭ������ �̵�
				new ManagerCourseFrame();
				dispose();
			});

			add(schedulePanel);
			setVisible(true);
		}
	}

	/// 3.������������(��ȸ/���)
	public class ManagerProfessorFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable professorTable;
		private JTextField txtName, txtPhone, txtBirthday, txtEmail, txtDepartmentID;

		public ManagerProfessorFrame() { // ��ܰ���
			setTitle("�л�����ý���(������)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // ũ�� ����
			setLocationRelativeTo(null);

			JPanel professorPanel = new JPanel(new BorderLayout());

			// ��� �г� (�� �޴� + �α׾ƿ� ��ư) - ����
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("�ð�ǥ����");
			JButton btnProfessor = new JButton("������������");
			JButton btnStudent = new JButton("�л���������");
			JButton btnCourse = new JButton("��������");
			JButton btnLogout = new JButton("�α׾ƿ�");

			// �α׾ƿ� ��ư ũ�� ���� - ����
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("���� ���", Font.BOLD, 12);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// ���� ������ ǥ�� (��ư ���� ����) - ����
			btnProfessor.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			professorPanel.add(topPanel, BorderLayout.NORTH);

			// �Է� �� �г�
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("���� ���"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // ũ�� Ȯ��
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // ���� ����
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("���� ���", Font.BOLD, 13);
			Font fieldFont = new Font("���� ���", Font.BOLD, 12);

			txtName = new JTextField(15); // ũ�� Ȯ��
			txtPhone = new JTextField(15);
			txtBirthday = new JTextField(15);
			txtEmail = new JTextField(15);
			txtDepartmentID = new JTextField(15);
			JButton btnRegister = new JButton("����ϱ�");

			txtName.setFont(fieldFont);
			txtPhone.setFont(fieldFont);
			txtBirthday.setFont(fieldFont);
			txtEmail.setFont(fieldFont);
			txtDepartmentID.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("���� �̸�:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("��ȭ��ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtPhone, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			formPanel.add(new JLabel("�������:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBirthday, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			formPanel.add(new JLabel("�̸���:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtEmail, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(new JLabel("�а���ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtDepartmentID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// ���̺� �� ��ư �߰�
			String[] columnNames = { "����ID", "�����̸�", "��ȭ��ȣ", "�������", "�̸���", "ä������" };
			tableModel = new DefaultTableModel(columnNames, 0);
			professorTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(professorTable);

			// ��ü��ȸ & ���� ��ư
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("��ü������ȸ");
			JButton btnDelete = new JButton("����");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// ���� ����
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			professorPanel.add(centerPanel, BorderLayout.CENTER);
			professorPanel.add(buttonPanel, BorderLayout.SOUTH);

			// ��ư �̺�Ʈ �߰�
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

			btnCourse.addActionListener(e -> {// ���� ���� ȭ������ �̵�
				new ManagerCourseFrame();
				dispose();
			});

			add(professorPanel);
			setVisible(true);
		}
	}

	/// 4.�л���������(��ȸ/���)
	public class ManagerStudentFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable studentTable;
		private JTextField txtName, txtPhone, txtBirthDay, txtEmail, txtDepartmentID;

		public ManagerStudentFrame() {// ��ܰ���
			setTitle("�л�����ý���(������)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // ũ�� ����
			setLocationRelativeTo(null);

			JPanel studentPanel = new JPanel(new BorderLayout());

			// ��� �г� (�� �޴� + �α׾ƿ� ��ư) - ����
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("�ð�ǥ����");
			JButton btnProfessor = new JButton("������������");
			JButton btnStudent = new JButton("�л���������");
			JButton btnCourse = new JButton("��������");
			JButton btnLogout = new JButton("�α׾ƿ�");

			// �α׾ƿ� ��ư ũ�� ���� - ����
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("���� ���", Font.BOLD, 12);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// ���� ������ ǥ�� (��ư ���� ����) - ����
			btnStudent.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			studentPanel.add(topPanel, BorderLayout.NORTH);

			// �Է� �� �г�
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("�л� ���"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // ũ�� Ȯ��
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // ���� ����
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("���� ���", Font.BOLD, 13);
			Font fieldFont = new Font("���� ���", Font.PLAIN, 12);

			txtName = new JTextField(15); // ũ�� Ȯ��
			txtPhone = new JTextField(15);
			txtBirthDay = new JTextField(15);
			txtEmail = new JTextField(15);
			txtDepartmentID = new JTextField(15);
			JButton btnRegister = new JButton("����ϱ�");

			txtName.setFont(fieldFont);
			txtPhone.setFont(fieldFont);
			txtBirthDay.setFont(fieldFont);
			txtEmail.setFont(fieldFont);
			txtDepartmentID.setFont(fieldFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("�л� �̸�:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("��ȭ��ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtPhone, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			formPanel.add(new JLabel("�������:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBirthDay, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			formPanel.add(new JLabel("�̸���:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtEmail, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(new JLabel("�а���ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtDepartmentID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// ���̺� �� ��ư �߰�
			String[] columnNames = { "�й�", "�л��̸�", "��ȭ��ȣ", "�������", "�̸���", "���г⵵" };
			tableModel = new DefaultTableModel(columnNames, 0);
			studentTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(studentTable);

			// ��ü��ȸ & ���� ��ư
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("��ü�л���ȸ");
			JButton btnDelete = new JButton("����");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);

			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// ���� ����
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			studentPanel.add(centerPanel, BorderLayout.CENTER);
			studentPanel.add(buttonPanel, BorderLayout.SOUTH);

			// ��ư �̺�Ʈ �߰�
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

			btnCourse.addActionListener(e -> {// ���� ���� ȭ������ �̵�
				new ManagerCourseFrame();
				dispose();
			});

			add(studentPanel);
			setVisible(true);
		}
	}

	/// 5.������������
	public class ManagerCourseFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable CourseTable;
		private JTextField txtLectureID, txtDay, txtStartTime, txtEndTime;

		public ManagerCourseFrame() {// ��ܰ���
			setTitle("�л�����ý���(������)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // ũ�� ����
			setLocationRelativeTo(null);

			JPanel coursePanel = new JPanel(new BorderLayout());

			// ��� �г� (�� �޴� + �α׾ƿ� ��ư) - ����
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnHome = new JButton("HOME");
			JButton btnSchedule = new JButton("�ð�ǥ����");
			JButton btnProfessor = new JButton("������������");
			JButton btnStudent = new JButton("�л���������");
			JButton btnCourse = new JButton("��������");
			JButton btnLogout = new JButton("�α׾ƿ�");

			// �α׾ƿ� ��ư ũ�� ���� - ����
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("���� ���", Font.BOLD, 12);
			btnHome.setFont(tabFont);
			btnSchedule.setFont(tabFont);
			btnProfessor.setFont(tabFont);
			btnStudent.setFont(tabFont);
			btnCourse.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// ���� ������ ǥ�� (��ư ���� ����) - ����
			btnCourse.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnHome);
			tabPanel.add(btnSchedule);
			tabPanel.add(btnProfessor);
			tabPanel.add(btnStudent);
			tabPanel.add(btnCourse);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			coursePanel.add(topPanel, BorderLayout.NORTH);

			// �Է� �� �г�
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("�������� ��ȸ"));
			formPanel.setPreferredSize(new Dimension(380, 400)); // ũ�� Ȯ��
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 20); // ���� ����
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("���� ���", Font.BOLD, 13);
			Font fieldFont = new Font("���� ���", Font.PLAIN, 12);

			txtLectureID = new JTextField(15); // ũ�� Ȯ��
			txtDay = new JTextField(15);
			txtStartTime = new JTextField(15);
			txtEndTime = new JTextField(15);
			JButton btnSearch = new JButton("��ȸ�ϱ�");

			txtLectureID.setFont(fieldFont);
			txtDay.setFont(fieldFont);
			txtStartTime.setFont(fieldFont);
			txtEndTime.setFont(fieldFont);
			btnSearch.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("���ǽ� ��ȣ:", JLabel.LEFT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtLectureID, gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			formPanel.add(btnSearch, gbc);

			// ���̺� �� ��ư �߰�
			String[] columnNames = { "���ǽ� ��ȣ", "���Ǹ�", "��米��", "�а�" };
			tableModel = new DefaultTableModel(columnNames, 0);
			CourseTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(CourseTable);

			// ��ü�ð�ǥ��ȸ & ���� ��ư
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("��ü������ȸ");

			// -- <���ǽǰ��� ��ư ������ �� ��������>
			JButton btnBuildingManage = new JButton("���ǽǰ���");

			btnViewAll.setFont(labelFont);
			btnBuildingManage.setFont(labelFont);
			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnBuildingManage);

			// ���� ����
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			coursePanel.add(centerPanel, BorderLayout.CENTER);
			coursePanel.add(buttonPanel, BorderLayout.SOUTH);

			// ��ư �̺�Ʈ �߰�
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

	/// 5-2.������������ ��ư ������ ---- ���ǽ� ������ �̵�(�ǹ� ��ȸ / ���)
	public class ManagerBuildingFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable BuildingTable;
		private JTextField txtBuildingID, txtBuildingName;

		public ManagerBuildingFrame() { // ��ܰ���
			setTitle("�л�����ý���(������)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // ũ�� ����
			setLocationRelativeTo(null);

			JPanel BuildingPanel = new JPanel(new BorderLayout());

			// ��� �г� (�� �޴� + �α׾ƿ� ��ư) - ����
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnPrevios = new JButton("����ȭ������ ���ư���");
			JButton btnBuilding = new JButton("�ǹ� ����");
			JButton btnUnit = new JButton("���ǽ� ����");
			JButton btnLogout = new JButton("�α׾ƿ�");

			// �α׾ƿ� ��ư ũ�� ���� - ����
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("���� ���", Font.BOLD, 12);
			btnPrevios.setFont(tabFont);
			btnBuilding.setFont(tabFont);
			btnUnit.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// ���� ������ ǥ�� (��ư ���� ����) - ����
			btnBuilding.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnPrevios);
			tabPanel.add(btnBuilding);
			tabPanel.add(btnUnit);
			tabPanel.add(btnLogout);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			BuildingPanel.add(topPanel, BorderLayout.NORTH);

			// �Է� �� �г�
			JPanel formPanel = new JPanel(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createTitledBorder("�ǹ� ��� �� ��ȸ"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // ũ�� Ȯ��
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // ���� ����
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("���� ���", Font.BOLD, 13);
			Font fieldFont = new Font("���� ���", Font.PLAIN, 12);

			txtBuildingID = new JTextField(10); // ũ�� Ȯ��
			txtBuildingName = new JTextField(10);

			JButton btnSearch = new JButton("��ȸ�ϱ�");
			JButton btnRegister = new JButton("����ϱ�");

			txtBuildingID.setFont(fieldFont);
			txtBuildingName.setFont(fieldFont);

			btnSearch.setFont(labelFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("�ǹ� ��ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("�ǹ� �̸�:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingName, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			formPanel.add(btnSearch, gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			formPanel.add(btnRegister, gbc);

			// ���̺� �� ��ư �߰�
			String[] columnNames = { "�ǹ� ��ȣ", "�ǹ� �̸�" };
			tableModel = new DefaultTableModel(columnNames, 0);
			BuildingTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(BuildingTable);

			// ��ü�ð�ǥ��ȸ & ���� ��ư
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JButton btnViewAll = new JButton("��ü���ǽ� ��ȸ");

			btnViewAll.setFont(labelFont);
			buttonPanel.add(btnViewAll);

			// ���� ����
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			BuildingPanel.add(centerPanel, BorderLayout.CENTER);
			BuildingPanel.add(buttonPanel, BorderLayout.SOUTH);

			// ��ư �̺�Ʈ �߰�
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

	/// 5-3.���ǽ� ���� �ȿ��� (���ǽ� ��ȸ / ��� / ����)
	public class ManagerUnitFrame extends JFrame {
		private DefaultTableModel tableModel;
		private JTable UnitTable;
		private JTextField txtBuildingID, txtUnitID;

		public ManagerUnitFrame() {
			setTitle("�л�����ý���(������)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1200, 800); // ũ�� ����
			setLocationRelativeTo(null);

			JPanel BuildingPanel = new JPanel(new BorderLayout());

			// ��� �г� (�� �޴� + �α׾ƿ� ��ư) - ����
			JPanel topPanel = new JPanel(new BorderLayout());
			JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JButton btnPrevios = new JButton("����ȭ������ ���ư���");
			JButton btnBuilding = new JButton("�ǹ� ����");
			JButton btnUnit = new JButton("���ǽ� ����");
			JButton btnLogout = new JButton("�α׾ƿ�");

			// �α׾ƿ� ��ư ũ�� ���� - ����
			btnLogout.setPreferredSize(new Dimension(100, 30));
			Font tabFont = new Font("���� ���", Font.BOLD, 12);
			btnPrevios.setFont(tabFont);
			btnBuilding.setFont(tabFont);
			btnUnit.setFont(tabFont);
			btnLogout.setFont(tabFont);

			// ���� ������ ǥ�� (��ư ���� ����) - ����
			btnUnit.setBackground(Color.LIGHT_GRAY);

			tabPanel.add(btnPrevios);
			tabPanel.add(btnBuilding);
			tabPanel.add(btnUnit);
			tabPanel.add(btnLogout);

			topPanel.add(tabPanel, BorderLayout.WEST);
			topPanel.add(btnLogout, BorderLayout.EAST);
			BuildingPanel.add(topPanel, BorderLayout.NORTH);

			// �Է� �� �г�
			JPanel formPanel = new JPanel(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder("���ǽ� ��� �� ��ȸ"));
			formPanel.setPreferredSize(new Dimension(320, 300)); // ũ�� Ȯ��
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 10, 10, 30); // ���� ����
			gbc.anchor = GridBagConstraints.WEST;

			Font labelFont = new Font("���� ���", Font.BOLD, 13);
			Font fieldFont = new Font("���� ���", Font.BOLD, 12);

			txtBuildingID = new JTextField(10); // ũ�� Ȯ��
			txtUnitID = new JTextField(10);

			JButton btnSearch = new JButton("��ȸ�ϱ�");
			JButton btnRegister = new JButton("����ϱ�");

			txtBuildingID.setFont(fieldFont);
			txtUnitID.setFont(fieldFont);

			btnSearch.setFont(labelFont);
			btnRegister.setFont(labelFont);

			gbc.gridx = 0;
			gbc.gridy = 0;
			formPanel.add(new JLabel("�ǹ� ��ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtBuildingID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			formPanel.add(new JLabel("���ǽ� ��ȣ:", JLabel.RIGHT), gbc);
			gbc.gridx = 1;
			formPanel.add(txtUnitID, gbc);

			gbc.gridx = 0;
			gbc.gridy = 5;
			formPanel.add(btnSearch, gbc);

			gbc.gridx = 1;
			gbc.gridy = 5;
			formPanel.add(btnRegister, gbc);

			// ���̺� �� ��ư �߰�
			String[] columnNames = { "�ǹ� ��ȣ", "�ǹ���", "���ǽ� ��ȣ" };
			tableModel = new DefaultTableModel(columnNames, 0);
			UnitTable = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(UnitTable);

			// ��ü�ð�ǥ��ȸ & ���� ��ư
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			JButton btnViewAll = new JButton("��ü���ǽ� ��ȸ");
			JButton btnDelete = new JButton("����");

			btnViewAll.setFont(labelFont);
			btnDelete.setFont(labelFont);
			buttonPanel.add(btnViewAll);
			buttonPanel.add(btnDelete);

			// ���� ����
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(formPanel, BorderLayout.WEST);
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			BuildingPanel.add(centerPanel, BorderLayout.CENTER);
			BuildingPanel.add(buttonPanel, BorderLayout.SOUTH);

			// ��ư �̺�Ʈ �߰�
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

	//// ���� �޼ҵ�
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ManagerFrame::new);
	}

}