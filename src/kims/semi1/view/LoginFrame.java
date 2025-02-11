package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kims.semi1.controller.LoginController;

public class LoginFrame extends Frame {
	private LoginController loginController;
	private TextField tfId;
	private TextField tfPassword;
	private Label lblMessage;
	private Button btnLogin;
	private Button btnFindId;
	private Panel panel;

	public LoginFrame() {
		setTitle("학사관리시스템 로그인");
		setSize(1200, 800);
		setLayout(new BorderLayout());

		// 배경 이미지 추가
		Image backgroundImage = Toolkit.getDefaultToolkit().getImage("resources/background_sky.jpg");
		BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);

		add(backgroundPanel);
		backgroundPanel.setLayout(new GridBagLayout());
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;

			panel = new Panel();
			panel.setLayout(new GridLayout(5, 2, 10, 10)); // 5행 2열의 그리드 레이아웃{
			panel.setBackground(new Color(200, 200, 200, 255)); // 투명 배경
			{

				lblMessage = new Label();
				tfId = new TextField(15);
				tfPassword = new TextField(15);
				tfPassword.setEchoChar('*');
				btnLogin = new Button("로그인");
				btnFindId = new Button("아이디 찾기");

				panel.add(new Label("아이디:"));
				panel.add(tfId);

				panel.add(new Label("비밀번호:"));
				panel.add(tfPassword);

				panel.add(btnLogin);
				panel.add(btnFindId);

				panel.add(lblMessage);

				backgroundPanel.add(panel, gbc);
			}
		}

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		btnLogin.addActionListener(e -> {
			String id = tfId.getText();
			String password = tfPassword.getText();

			loginController.handleLoginButtonClick(id, password);
		});
		setVisible(true);
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public void showMessage(String message) {
		lblMessage.setText(message);
	}
}

class BackgroundPanel extends Panel {
	private Image backgroundImage;

	public BackgroundPanel(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void paint(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}