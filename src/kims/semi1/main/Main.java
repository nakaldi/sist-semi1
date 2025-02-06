package kims.semi1.main;

import java.util.Scanner;

import kims.semi1.controller.LoginController;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		LoginController lc = new LoginController();
		
		lc.handleUserInput(sc);
	}

}
