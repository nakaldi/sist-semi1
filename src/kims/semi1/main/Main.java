package kims.semi1.main;

import java.util.Scanner;

import kims.semi1.controller.LoginController;
import kims.semi1.view.LoginFrame;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		LoginFrame lf = new LoginFrame();
		LoginController lc = new LoginController(lf);
		lf.setLoginController(lc);

		lc.handleUserInput(sc);
	}
}
