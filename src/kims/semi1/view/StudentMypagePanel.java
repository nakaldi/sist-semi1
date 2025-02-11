package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import kims.semi1.controller.StudentController;

public class StudentMypagePanel {
	private StudentController studentController;
	
	private JTextField nameField;
	private JTextField birthDateField;
	private JTextField studentIdField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField departmentNameField;
	private JTextField buildingNameField;
	private JPasswordField passwordField;


	public StudentMypagePanel(StudentController studentController) {
		this.studentController = studentController;
	}

	
	public Panel createMyPagePanel() {
		Panel innerPanel = new Panel();
		GridBagLayout gbl_innerPanel = new GridBagLayout();
		gbl_innerPanel.columnWidths = new int[] { 850 };
		gbl_innerPanel.rowHeights = new int[] { 100, 500, 130 };
		gbl_innerPanel.columnWeights = new double[] { 0.0 };
		gbl_innerPanel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		innerPanel.setLayout(gbl_innerPanel);

		JPanel headPanel = new JPanel();
		
			GridBagConstraints gbc_headPanel = new GridBagConstraints();
			gbc_headPanel.anchor = GridBagConstraints.NORTH;
			gbc_headPanel.insets = new Insets(0, 0, 5, 0);
			gbc_headPanel.fill = GridBagConstraints.BOTH;
			gbc_headPanel.gridx = 0;
			gbc_headPanel.gridy = 0;
			innerPanel.add(headPanel, gbc_headPanel);
			GridBagLayout gbl_headPanel = new GridBagLayout();
			gbl_headPanel.columnWidths = new int[] { 300, 550 };
			gbl_headPanel.rowHeights = new int[] { 100, 20 };
			gbl_headPanel.columnWeights = new double[] { 0.0 };
			gbl_headPanel.rowWeights = new double[] { 0.0, 0.0 };
			headPanel.setLayout(gbl_headPanel);

			JLabel headTitleLabel = new JLabel("학생 개인 정보");
			headTitleLabel.setBackground(new Color(0, 0, 0));
			headTitleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 28));
			GridBagConstraints gbc_headTitleLabel = new GridBagConstraints();
			gbc_headTitleLabel.fill = GridBagConstraints.BOTH;
			gbc_headTitleLabel.anchor = GridBagConstraints.WEST;
			gbc_headTitleLabel.insets = new Insets(30, 10, 5, 0);
			gbc_headTitleLabel.gridx = 0;
			gbc_headTitleLabel.gridy = 0;
			headPanel.add(headTitleLabel, gbc_headTitleLabel);

			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.anchor = GridBagConstraints.NORTH;
			gbc_separator.fill = GridBagConstraints.HORIZONTAL;

			separator.setForeground(new Color(128, 128, 128));
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 1;
			headPanel.add(separator, gbc_separator);
		

		JPanel centerPanel = new JPanel();
		
			GridBagConstraints gbc_centerPanel = new GridBagConstraints();
			gbc_centerPanel.anchor = GridBagConstraints.NORTH;
			gbc_centerPanel.fill = GridBagConstraints.BOTH;
			gbc_centerPanel.gridx = 0;
			gbc_centerPanel.gridy = 1;
			innerPanel.add(centerPanel, gbc_centerPanel);
			GridBagLayout gbl_centerPanel = new GridBagLayout();
			gbl_centerPanel.columnWidths = new int[] { 250, 300, 300 };
			gbl_centerPanel.rowHeights = new int[] { 50, 40, 40, 40, 30, 50, 40, 40, 30, 50, 40, 40, 30 };
			gbl_centerPanel.columnWeights = new double[] { 0.0, 1.0 };
			gbl_centerPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
			centerPanel.setLayout(gbl_centerPanel);

			JLabel subTitleLabel1 = new JLabel("기본 정보");
			subTitleLabel1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			GridBagConstraints gbc_subTitleLabel1 = new GridBagConstraints();
			gbc_subTitleLabel1.anchor = GridBagConstraints.WEST;
			gbc_subTitleLabel1.insets = new Insets(0, 0, 5, 5);
			gbc_subTitleLabel1.gridx = 0;
			gbc_subTitleLabel1.gridy = 0;
			centerPanel.add(subTitleLabel1, gbc_subTitleLabel1);

			nameField = new JTextField();
			nameField.setText("김범희");
			nameField.setBorder(null);
			nameField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			nameField.setEditable(false);
			GridBagConstraints gbc_nameField = new GridBagConstraints();
			gbc_nameField.insets = new Insets(0, 0, 0, 0);
			gbc_nameField.anchor = GridBagConstraints.WEST;
			gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
			gbc_nameField.gridx = 1;
			gbc_nameField.gridy = 1;
			centerPanel.add(nameField, gbc_nameField);
			nameField.setColumns(10);

			birthDateField = new JTextField();
			birthDateField.setText("1992년 11월 15일");
			birthDateField.setBorder(null);
			birthDateField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			birthDateField.setEditable(false);
			GridBagConstraints gbc_birthDateField = new GridBagConstraints();
			gbc_birthDateField.insets = new Insets(0, 0, 0, 0);
			gbc_birthDateField.anchor = GridBagConstraints.WEST;
			gbc_birthDateField.fill = GridBagConstraints.HORIZONTAL;
			gbc_birthDateField.gridy = 2;
			gbc_birthDateField.gridx = 1;
			centerPanel.add(birthDateField, gbc_birthDateField);
			birthDateField.setColumns(10);

			studentIdField = new JTextField();
			studentIdField.setText("2025100001");
			studentIdField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			studentIdField.setEditable(false);
			studentIdField.setBorder(null);
			GridBagConstraints gbc_studentIdField = new GridBagConstraints();
			gbc_studentIdField.anchor = GridBagConstraints.WEST;
			gbc_studentIdField.insets = new Insets(0, 0, 0, 0);
			gbc_studentIdField.fill = GridBagConstraints.HORIZONTAL;
			gbc_studentIdField.gridx = 1;
			gbc_studentIdField.gridy = 3;
			centerPanel.add(studentIdField, gbc_studentIdField);
			studentIdField.setColumns(10);

			phoneField = new JTextField();
			phoneField.setText("010-8831-3733");
			phoneField.setEditable(false);
			phoneField.setBorder(null);
			phoneField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			GridBagConstraints gbc_phoneField = new GridBagConstraints();
			gbc_phoneField.anchor = GridBagConstraints.WEST;
			gbc_phoneField.insets = new Insets(0, 0, 0, 0);
			gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
			gbc_phoneField.gridx = 1;
			gbc_phoneField.gridy = 6;
			centerPanel.add(phoneField, gbc_phoneField);
			phoneField.setColumns(10);

			emailField = new JTextField();
			emailField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			emailField.setText("nakaldi@hamster.com");
			emailField.setBorder(null);
			emailField.setEditable(false);
			GridBagConstraints gbc_emailField = new GridBagConstraints();
			gbc_emailField.anchor = GridBagConstraints.WEST;
			gbc_emailField.insets = new Insets(0, 0, 0, 0);
			gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
			gbc_emailField.gridx = 1;
			gbc_emailField.gridy = 7;
			centerPanel.add(emailField, gbc_emailField);
			emailField.setColumns(10);

			departmentNameField = new JTextField();
			departmentNameField.setText("컴퓨터 공학과");
			departmentNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			departmentNameField.setBorder(null);
			departmentNameField.setEditable(false);
			GridBagConstraints gbc_departmentNameField = new GridBagConstraints();
			gbc_departmentNameField.anchor = GridBagConstraints.WEST;
			gbc_departmentNameField.insets = new Insets(0, 0, 0, 0);
			gbc_departmentNameField.fill = GridBagConstraints.HORIZONTAL;
			gbc_departmentNameField.gridx = 1;
			gbc_departmentNameField.gridy = 10;
			centerPanel.add(departmentNameField, gbc_departmentNameField);
			departmentNameField.setColumns(10);

			buildingNameField = new JTextField();
			buildingNameField.setText("공학관");
			buildingNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			buildingNameField.setEditable(false);
			buildingNameField.setBorder(null);
			GridBagConstraints gbc_buildingNameField = new GridBagConstraints();
			gbc_buildingNameField.anchor = GridBagConstraints.WEST;
			gbc_buildingNameField.insets = new Insets(0, 0, 0, 0);
			gbc_buildingNameField.fill = GridBagConstraints.HORIZONTAL;
			gbc_buildingNameField.gridx = 1;
			gbc_buildingNameField.gridy = 11;
			centerPanel.add(buildingNameField, gbc_buildingNameField);
			buildingNameField.setColumns(10);

			JSeparator separator_1_1 = new JSeparator();
			separator_1_1.setForeground(new Color(192, 192, 192));
			GridBagConstraints gbc_separator_1_1 = new GridBagConstraints();
			gbc_separator_1_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_separator_1_1.gridwidth = 3;
			gbc_separator_1_1.gridx = 0;
			gbc_separator_1_1.gridy = 12;
			centerPanel.add(separator_1_1, gbc_separator_1_1);

			JLabel departmentNameLabel = new JLabel("전공 학과");
			departmentNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_departmentNameLabel = new GridBagConstraints();
			gbc_departmentNameLabel.anchor = GridBagConstraints.WEST;
			gbc_departmentNameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_departmentNameLabel.gridx = 0;
			gbc_departmentNameLabel.gridy = 10;
			centerPanel.add(departmentNameLabel, gbc_departmentNameLabel);

			JLabel buildingNameLabel = new JLabel("학과 위치");
			buildingNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_buildingNameLabel = new GridBagConstraints();
			gbc_buildingNameLabel.anchor = GridBagConstraints.WEST;
			gbc_buildingNameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_buildingNameLabel.gridx = 0;
			gbc_buildingNameLabel.gridy = 11;
			centerPanel.add(buildingNameLabel, gbc_buildingNameLabel);

			JSeparator separator_2 = new JSeparator();
			GridBagConstraints gbc_separator_2 = new GridBagConstraints();
			gbc_separator_2.insets = new Insets(0, 0, 5, 0);
			gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
			separator_2.setForeground(new Color(210, 210, 210));
			gbc_separator_2.gridwidth = 3;
			gbc_separator_2.gridx = 0;
			gbc_separator_2.gridy = 8;
			centerPanel.add(separator_2, gbc_separator_2);

			JLabel subTitleLabel3 = new JLabel("학과 정보");
			subTitleLabel3.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			GridBagConstraints gbc_subTitleLabel3 = new GridBagConstraints();
			gbc_subTitleLabel3.anchor = GridBagConstraints.WEST;
			gbc_subTitleLabel3.insets = new Insets(0, 0, 5, 5);
			gbc_subTitleLabel3.gridx = 0;
			gbc_subTitleLabel3.gridy = 9;
			centerPanel.add(subTitleLabel3, gbc_subTitleLabel3);

			JLabel emailLabel = new JLabel("이메일");
			emailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_emailLabel = new GridBagConstraints();
			gbc_emailLabel.anchor = GridBagConstraints.WEST;
			gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
			gbc_emailLabel.gridx = 0;
			gbc_emailLabel.gridy = 7;
			centerPanel.add(emailLabel, gbc_emailLabel);

			JLabel phoneLabel = new JLabel("전화번호");
			phoneLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			phoneLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_phoneLabel = new GridBagConstraints();
			gbc_phoneLabel.anchor = GridBagConstraints.WEST;
			gbc_phoneLabel.insets = new Insets(0, 0, 5, 5);
			gbc_phoneLabel.gridx = 0;
			gbc_phoneLabel.gridy = 6;
			centerPanel.add(phoneLabel, gbc_phoneLabel);

			JLabel subTitleLabel2 = new JLabel("연락처 정보");
			subTitleLabel2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			GridBagConstraints gbc_subTitleLabel2 = new GridBagConstraints();
			gbc_subTitleLabel2.anchor = GridBagConstraints.WEST;
			gbc_subTitleLabel2.insets = new Insets(0, 0, 5, 5);
			gbc_subTitleLabel2.gridx = 0;
			gbc_subTitleLabel2.gridy = 5;
			centerPanel.add(subTitleLabel2, gbc_subTitleLabel2);

			JSeparator separator_1 = new JSeparator();
			GridBagConstraints gbc_separator_1 = new GridBagConstraints();
			gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
			separator_1.setForeground(new Color(210, 210, 210));
			gbc_separator_1.gridwidth = 3;
			gbc_separator_1.insets = new Insets(0, 0, 5, 0);
			gbc_separator_1.gridx = 0;
			gbc_separator_1.gridy = 4;
			centerPanel.add(separator_1, gbc_separator_1);

			JLabel nameLabel = new JLabel("이름");
			nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_nameLabel = new GridBagConstraints();
			gbc_nameLabel.anchor = GridBagConstraints.WEST;
			gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_nameLabel.gridx = 0;
			gbc_nameLabel.gridy = 1;
			centerPanel.add(nameLabel, gbc_nameLabel);

			JLabel birthDateLabel = new JLabel("생년월일");
			birthDateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_birthDateLabel = new GridBagConstraints();
			gbc_birthDateLabel.anchor = GridBagConstraints.WEST;
			gbc_birthDateLabel.insets = new Insets(0, 0, 5, 5);
			gbc_birthDateLabel.gridx = 0;
			gbc_birthDateLabel.gridy = 2;
			centerPanel.add(birthDateLabel, gbc_birthDateLabel);

			JLabel studentIdLabel = new JLabel("학생번호");
			studentIdLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			GridBagConstraints gbc_studentIdLabel = new GridBagConstraints();
			gbc_studentIdLabel.anchor = GridBagConstraints.WEST;
			gbc_studentIdLabel.insets = new Insets(0, 0, 5, 5);
			gbc_studentIdLabel.gridx = 0;
			gbc_studentIdLabel.gridy = 3;
			centerPanel.add(studentIdLabel, gbc_studentIdLabel);
		
		Panel bottomPanel = new Panel();
		
			GridBagConstraints gbc_bottomPanel = new GridBagConstraints();
			gbc_bottomPanel.anchor = GridBagConstraints.NORTHEAST;
			gbc_bottomPanel.insets = new Insets(0, 0, 5, 0);
			gbc_bottomPanel.gridx = 0;
			gbc_bottomPanel.gridy = 2;
			innerPanel.add(bottomPanel, gbc_bottomPanel);
			bottomPanel.setLayout(new BorderLayout(0, 0));

			JPanel printInfoPanel = new JPanel();
			
				GridBagLayout gbl_printInfoPanel = new GridBagLayout();
				gbl_printInfoPanel.columnWidths = new int[] { 0, 100 };
				gbl_printInfoPanel.rowHeights = new int[] { 40 };
				gbl_printInfoPanel.columnWeights = new double[] { 1.0, 0.0 };
				gbl_printInfoPanel.rowWeights = new double[] { 0.0 };
				printInfoPanel.setLayout(gbl_printInfoPanel);
				bottomPanel.add(printInfoPanel, BorderLayout.NORTH);
				
				JTextField textField = new JTextField();
				textField.setEnabled(false);
				textField.setEditable(false);
				textField.setBorder(null);
				textField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(0, 0, 0, 5);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 0;
				gbc_textField.gridy = 0;
				printInfoPanel.add(textField, gbc_textField);
				textField.setColumns(10);
				
				JButton btnUpdateInfoModeButton = new JButton("정보 수정");
				btnUpdateInfoModeButton.setVerticalTextPosition(SwingConstants.CENTER);
				btnUpdateInfoModeButton.setHorizontalTextPosition(SwingConstants.CENTER);
				btnUpdateInfoModeButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				btnUpdateInfoModeButton.setFocusPainted(false);
				btnUpdateInfoModeButton.setBorderPainted(false);
				btnUpdateInfoModeButton.setForeground(new Color(120, 120, 245));
				GridBagConstraints gbc_btnUpdateInfoModeButton = new GridBagConstraints();
				gbc_btnUpdateInfoModeButton.anchor = GridBagConstraints.EAST;
				gbc_btnUpdateInfoModeButton.gridx = 1;
				gbc_btnUpdateInfoModeButton.gridy = 0;
				printInfoPanel.add(btnUpdateInfoModeButton, gbc_btnUpdateInfoModeButton);
			
			JPanel updateInfoPanel = new JPanel();
			
				updateInfoPanel.setBorder(new EmptyBorder(2, 0, 0, 0));
				GridBagLayout gbl_updateInfoPanel = new GridBagLayout();
				gbl_updateInfoPanel.columnWidths = new int[] { 250, 300, 300 };
				gbl_updateInfoPanel.rowHeights = new int[] { 40 };
				gbl_updateInfoPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
				gbl_updateInfoPanel.rowWeights = new double[] { 0.0 };
				updateInfoPanel.setLayout(gbl_updateInfoPanel);
				bottomPanel.add(updateInfoPanel, BorderLayout.CENTER);
				
				passwordField = new JPasswordField("1234");
				passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
				GridBagConstraints gbc_passwordField = new GridBagConstraints();
				gbc_passwordField.anchor = GridBagConstraints.WEST;
				gbc_passwordField.insets = new Insets(0, 0, 0, 0);
				gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
				gbc_passwordField.gridx = 1;
				gbc_passwordField.gridy = 0;
				updateInfoPanel.add(passwordField, gbc_passwordField);
				
				JButton btnSaveInfoButton = new JButton("정보 등록");
				btnSaveInfoButton.setVerticalTextPosition(SwingConstants.CENTER);
				btnSaveInfoButton.setHorizontalTextPosition(SwingConstants.CENTER);
				btnSaveInfoButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				btnSaveInfoButton.setFocusPainted(false);
				btnSaveInfoButton.setBorderPainted(false);
				btnSaveInfoButton.setForeground(new Color(120, 120, 245));
				GridBagConstraints gbc_btnSaveInfoButton = new GridBagConstraints();
				gbc_btnSaveInfoButton.insets = new Insets(0, 0, 5, 0);
				gbc_btnSaveInfoButton.anchor = GridBagConstraints.EAST;
				gbc_btnSaveInfoButton.gridx = 2;
				gbc_btnSaveInfoButton.gridy = 0;
				updateInfoPanel.add(btnSaveInfoButton, gbc_btnSaveInfoButton);
				
				JLabel passwordLabel = new JLabel("변경할 비밀번호");
				passwordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
				GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
				gbc_passwordLabel.anchor = GridBagConstraints.WEST;
				gbc_passwordLabel.insets = new Insets(0, 0, 0, 0);
				gbc_passwordLabel.gridx = 0;
				gbc_passwordLabel.gridy = 0;
				updateInfoPanel.add(passwordLabel, gbc_passwordLabel);

		printInfoPanel.setVisible(true);
		updateInfoPanel.setVisible(false);

		btnUpdateInfoModeButton.addActionListener(e -> {
			printInfoPanel.setVisible(false);
			updateInfoPanel.setVisible(true);

			nameField.setEditable(true);
			nameField.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 245), 2));
			birthDateField.setEditable(true);
			birthDateField.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 245), 2));
			phoneField.setEditable(true);
			phoneField.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 245), 2));
			emailField.setEditable(true);
			emailField.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 245), 2));
			passwordField.setEditable(true);
			passwordField.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 245), 2));

		});

		btnSaveInfoButton.addActionListener(e -> {
			printInfoPanel.setVisible(true);
			updateInfoPanel.setVisible(false);

			nameField.setEditable(false);
			nameField.setBorder(null);
			birthDateField.setEditable(false);
			birthDateField.setBorder(null);
			phoneField.setEditable(false);
			phoneField.setBorder(null);
			emailField.setEditable(false);
			emailField.setBorder(null);

		});

		StudentFrame.setBackgroundDisableForAllComponents(innerPanel);
		return innerPanel;
	}

}