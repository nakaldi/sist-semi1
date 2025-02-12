package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import kims.semi1.controller.StudentController;

public class StudentFrame {
	private CardLayout cardLayout;
	private Panel mainPanel;
	private JFrame frame;
	private StudentController studentController;
	ImageIcon icon = new ImageIcon("/resources/button_white.png");
	ImageIcon selectedIcon = new ImageIcon("/resources/button_clicked.png");
	JPanel sidePanel;
	public StudentEnrollmentPanel studentEnrollmentPanel;
	public StudentMypagePanel studentMypagePanel;

	public StudentFrame(StudentController studentController) {
		this.studentController = studentController;
		createStudentFrame();
	}
	
	public void createStudentFrame() {
		frame = new JFrame();
		frame.setBounds(50, 50, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10, 0));
		frame.getContentPane().setBackground(new Color(245, 245, 245));

		sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(250, 800));
		sidePanel.setBackground(new Color(245, 245, 245));
		sidePanel.setLayout(null);
		frame.getContentPane().add(sidePanel, BorderLayout.WEST);

		JButton btnNewButton = new JButton("마이페이지                     ", icon);
		btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton.setVerticalTextPosition(SwingConstants.CENTER);
		btnNewButton.setBorderPainted(false); // 경계선 제거
		btnNewButton.setFocusPainted(false); // 포커스 표시 제거
		btnNewButton.setBounds(0, 10, 250, 45);
		sidePanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("수강 신청                       ", icon);
		btnNewButton_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_1.setVerticalTextPosition(SwingConstants.CENTER);
		btnNewButton_1.setBorderPainted(false); // 경계선 제거
		btnNewButton_1.setFocusPainted(false); // 포커스 표시 제거
		btnNewButton_1.setBounds(0, 55, 250, 45);
		sidePanel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("수강 현황                       ", icon);
		btnNewButton_2.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_2.setVerticalTextPosition(SwingConstants.CENTER);
		btnNewButton_2.setBorderPainted(false); // 경계선 제거
		btnNewButton_2.setFocusPainted(false); // 포커스 표시 제거
		btnNewButton_2.setBounds(0, 100, 250, 45);
		sidePanel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("성적 확인                       ", icon);
		btnNewButton_3.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_3.setVerticalTextPosition(SwingConstants.CENTER);
		btnNewButton_3.setBorderPainted(false); // 경계선 제거
		btnNewButton_3.setFocusPainted(false); // 포커스 표시 제거
		btnNewButton_3.setBounds(0, 145, 250, 45);
		sidePanel.add(btnNewButton_3);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(192, 192, 192));
		separator.setBounds(0, 210, 250, 2);
		sidePanel.add(separator);

		Font newFont = new Font("Malgun Gothic", Font.PLAIN, 15);
		setFontForAllComponents(sidePanel, newFont);

		studentMypagePanel = new StudentMypagePanel(studentController);
		studentEnrollmentPanel = new StudentEnrollmentPanel(frame, studentController);
		// 메인 패널 (CardLayout 사용)
		cardLayout = new CardLayout();
		mainPanel = new Panel(cardLayout);
		mainPanel.setBackground(new Color(245, 245, 245));
		mainPanel.add(studentMypagePanel.createMyPagePanel(), "마이페이지");
		mainPanel.add(studentEnrollmentPanel.createEnrollmentPanel(), "수강신청");
		mainPanel.add(new Label("출결관리 화면 (추후 구현)"), "수강현황");
		mainPanel.add(new Label("성적관리 화면 (추후 구현)"), "성적확인");
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		btnNewButton.setIcon(selectedIcon);

		// 버튼 클릭 시 화면 변경

		btnNewButton.addActionListener(e -> {
			cardLayout.show(mainPanel, "마이페이지");
			mainPanel.revalidate();
			mainPanel.repaint();
			btnNewButton.setIcon(selectedIcon);
			btnNewButton_1.setIcon(icon);
			btnNewButton_2.setIcon(icon);
			btnNewButton_3.setIcon(icon);
		});

		btnNewButton_1.addActionListener(e -> {
			cardLayout.show(mainPanel, "수강신청");
			btnNewButton_1.setIcon(selectedIcon);
			btnNewButton.setIcon(icon);
			btnNewButton_2.setIcon(icon);
			btnNewButton_3.setIcon(icon);
		});

		btnNewButton_2.addActionListener(e -> {
			cardLayout.show(mainPanel, "수강현황");
			btnNewButton_2.setIcon(selectedIcon);
			btnNewButton_1.setIcon(icon);
			btnNewButton.setIcon(icon);
			btnNewButton_3.setIcon(icon);
		});

		btnNewButton_3.addActionListener(e -> {
			cardLayout.show(mainPanel, "성적확인");
			btnNewButton_3.setIcon(selectedIcon);
			btnNewButton_1.setIcon(icon);
			btnNewButton_2.setIcon(icon);
			btnNewButton.setIcon(icon);
		});

		frame.setVisible(true);
	}

	public static void setBackgroundDisableForAllComponents(Container container) {
		for (Component component : container.getComponents()) {
			component.setBackground(null);
			if (component instanceof Container) {
				setBackgroundDisableForAllComponents((Container) component);
			}
		}
	}

	private void setFontForAllComponents(Container container, Font font) {
		for (Component component : container.getComponents()) {
			component.setFont(font);
			if (component instanceof Container) {
				setFontForAllComponents((Container) component, font);
			}
		}
	}

	private Panel createCell(String text, boolean highlight) {
		Panel cell = new Panel();
		cell.setBackground(highlight ? Color.DARK_GRAY : Color.LIGHT_GRAY);
		Label label = new Label(text, Label.CENTER);
		label.setForeground(Color.WHITE);
		cell.add(label);
		return cell;
	}

}