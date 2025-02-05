package kims.semi1.main;

import java.util.Scanner;

import kims.semi1.controller.LoginContoroller;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		LoginContoroller lc = new LoginContoroller();
		
		lc.handleUserInput(sc);
	}

}
