package kims.semi1.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kims.semi1.controller.LoginController;

public class LoginFrame extends Frame {
	private TextField tfId, tfPassword;
	private Label lblMessage;
	Button btnLogin = new Button("로그인");

	public LoginFrame() {

		setTitle("학사관리시스템 로그인");
		setSize(400, 300);
		setLayout(null);

		Label lblTitle = new Label("학사관리시스템", Label.CENTER);
		lblTitle.setBounds(100, 50, 200, 30);
		add(lblTitle);

		Label lblId = new Label("아이디:");
		lblId.setBounds(80, 100, 50, 20);
		add(lblId);

		tfId = new TextField();
		tfId.setBounds(150, 100, 150, 20);
		add(tfId);

		Label lblPassword = new Label("비밀번호:");
		lblPassword.setBounds(80, 140, 60, 20);
		add(lblPassword);

		tfPassword = new TextField();
		tfPassword.setEchoChar('*');
		tfPassword.setBounds(150, 140, 150, 20);
		add(tfPassword);

		btnLogin.setBounds(150, 180, 80, 30);
		add(btnLogin);

		lblMessage = new Label("", Label.CENTER);
		lblMessage.setBounds(100, 220, 200, 20);
		lblMessage.setForeground(Color.RED);
		add(lblMessage);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setVisible(true);
	}

	public void setLoginController(LoginController loginController) {
		btnLogin.addActionListener(e -> {
			String id = tfId.getText();
			String password = tfPassword.getText();

			loginController.handleLoginButtonClick(id, password);
		});
	}

	public void showMessage(String message) {
		lblMessage.setText(message);
	}
}