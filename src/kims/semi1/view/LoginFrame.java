package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import kims.semi1.config.ResourceManager;
import kims.semi1.controller.LoginController;

public class LoginFrame extends JFrame {
	private LoginController loginController;
	private JPanel backGroundPanel;
	Image background_img;
	ImageIcon loginButton_img;
	private JTextField textField;
	private JPasswordField passwordField;

	public LoginFrame() {
		background_img = ResourceManager.getImage("background_sky.jpg");
		loginButton_img = ResourceManager.getIcon("button_login.png");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1200, 800);
		backGroundPanel = new BackgroundPanel(background_img);
		backGroundPanel.setBorder(new EmptyBorder(65, 40, 45, 40));

		setContentPane(backGroundPanel);
		backGroundPanel.setLayout(new BorderLayout(0, 0));

		RoundedPanel loginPanel = new RoundedPanel(10);
		loginPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		loginPanel.setBackground(new Color(245, 245, 245));
		backGroundPanel.add(loginPanel, BorderLayout.EAST);
		GridBagLayout gbl_loginPanel = new GridBagLayout();
		gbl_loginPanel.columnWidths = new int[] { 380 };
		gbl_loginPanel.rowHeights = new int[] { 60, 110, 140, 100, 60, 100 };
		gbl_loginPanel.columnWeights = new double[] { 1.0 };
		gbl_loginPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		loginPanel.setLayout(gbl_loginPanel);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		loginPanel.add(panel_1, gbc_panel_1);

		JPanel inputPanel = new JPanel();
		GridBagConstraints gbc_inputPanel = new GridBagConstraints();
		gbc_inputPanel.insets = new Insets(0, 30, 5, 30);
		gbc_inputPanel.fill = GridBagConstraints.BOTH;
		gbc_inputPanel.gridx = 0;
		gbc_inputPanel.gridy = 2;
		loginPanel.add(inputPanel, gbc_inputPanel);
		inputPanel.setLayout(new GridLayout(2, 1, 0, 10));

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		inputPanel.add(textField);
		textField.setColumns(15);
		textField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(77, 152, 255)));

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(77, 152, 255)));

		inputPanel.add(passwordField);

		JPanel FIndingIdPanel = new JPanel();
		GridBagConstraints gbc_FIndingIdPanel = new GridBagConstraints();
		gbc_FIndingIdPanel.insets = new Insets(0, 0, 5, 0);
		gbc_FIndingIdPanel.fill = GridBagConstraints.BOTH;
		gbc_FIndingIdPanel.gridx = 0;
		gbc_FIndingIdPanel.gridy = 4;
		loginPanel.add(FIndingIdPanel, gbc_FIndingIdPanel);
		GridBagLayout gbl_FIndingIdPanel = new GridBagLayout();
		gbl_FIndingIdPanel.columnWidths = new int[] { 100, 180, 100 };
		gbl_FIndingIdPanel.rowHeights = new int[] { 70 };
		gbl_FIndingIdPanel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_FIndingIdPanel.rowWeights = new double[] { 0.0 };
		FIndingIdPanel.setLayout(gbl_FIndingIdPanel);

		JButton findingIdButton = new JButton("아이디를 잊으셨나요?");
		findingIdButton.setForeground(new Color(77, 152, 255));
		findingIdButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		findingIdButton.setBorderPainted(false); // 경계선 제거
		findingIdButton.setFocusPainted(false); // 포커스 표시 제거
		GridBagConstraints gbc_findingIdButton = new GridBagConstraints();
		gbc_findingIdButton.anchor = GridBagConstraints.SOUTH;
		gbc_findingIdButton.insets = new Insets(0, 0, 0, 5);
		gbc_findingIdButton.gridx = 1;
		gbc_findingIdButton.gridy = 0;
		FIndingIdPanel.add(findingIdButton, gbc_findingIdButton);

		JPanel loginButtonPanel = new JPanel();
		loginButtonPanel.setBorder(null);
		GridBagConstraints gbc_loginButtonPanel = new GridBagConstraints();
		gbc_loginButtonPanel.fill = GridBagConstraints.BOTH;
		gbc_loginButtonPanel.gridx = 0;
		gbc_loginButtonPanel.gridy = 3;
		loginPanel.add(loginButtonPanel, gbc_loginButtonPanel);
		GridBagLayout gbl_loginButtonPanel = new GridBagLayout();
		gbl_loginButtonPanel.columnWidths = new int[] { 340 };
		gbl_loginButtonPanel.rowHeights = new int[] { 40, 55, 25 };
		gbl_loginButtonPanel.columnWeights = new double[] { 0.0 };
		gbl_loginButtonPanel.rowWeights = new double[] { 0.0, 0.0 };
		loginButtonPanel.setLayout(gbl_loginButtonPanel);

		JButton loginButton = new JButton("Login", loginButton_img);
		loginButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		loginButton.setForeground(new Color(245, 245, 245));
		loginButton.setHorizontalTextPosition(SwingConstants.CENTER);
		loginButton.setVerticalTextPosition(SwingConstants.CENTER);
		loginButton.setBorderPainted(false); // 경계선 제거
		loginButton.setFocusPainted(false); // 포커스 표시 제거
		GridBagConstraints gbc_loginButton = new GridBagConstraints();
		gbc_loginButton.fill = GridBagConstraints.BOTH;
		gbc_loginButton.insets = new Insets(0, 10, 0, 10);
		gbc_loginButton.gridx = 0;
		gbc_loginButton.gridy = 1;
		loginButtonPanel.add(loginButton, gbc_loginButton);

		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 5;
		loginPanel.add(panel_5, gbc_panel_5);

		JPanel titlePanel = new JPanel();
		GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.fill = GridBagConstraints.BOTH;
		gbc_titlePanel.gridx = 0;
		gbc_titlePanel.gridy = 1;
		loginPanel.add(titlePanel, gbc_titlePanel);

		JLabel titleLabel = new JLabel("Login");
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 36));
		titlePanel.add(titleLabel);

		loginButton.addActionListener(e -> {
			String id = textField.getText();
			String password = new String(passwordField.getPassword());

			loginController.handleLoginButtonClick(id, password);
		});

		setVisible(true);

		setBackgroundDisableForAllComponents(loginPanel);

	}

	public static void setBackgroundDisableForAllComponents(Container container) {
		for (Component component : container.getComponents()) {
			component.setBackground(null);
			if (component instanceof Container) {
				setBackgroundDisableForAllComponents((Container) component);
			}
		}
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}

class BackgroundPanel extends JPanel {
	private Image backgroundImage;

	public BackgroundPanel(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}

class RoundedPanel extends JPanel {
	private int cornerRadius;

	public RoundedPanel(int radius) {
		super();
		this.cornerRadius = radius;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = getWidth();
		int height = getHeight();
		int arcWidth = cornerRadius * 2;
		int arcHeight = cornerRadius * 2;

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);
	}
}