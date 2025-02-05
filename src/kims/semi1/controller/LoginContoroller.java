package kims.semi1.controller;

import java.util.Scanner;

import kims.semi1.service.LoginService;

public class LoginContoroller {

	private final LoginService loginService;
	private int currentUserId;
	Scanner sc;

	public LoginContoroller() {
		loginService = new LoginService();
	}

	public void handleUserInput(Scanner sc) {

		System.out.println("1. 로그인");
		System.out.println("0. 프로그램 종료");
		System.out.print(">>");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			System.out.println("====로그인====");
			int loggedInUserId = loginService.loginUser(scanUserId(sc), scanUserPw(sc));

			if (loggedInUserId != -1) {
				currentUserId = loggedInUserId;
				System.out.println("로그인 성공");
			} else {
				System.out.println("로그인 실패, 최초화면으로 돌아갑니다.");
				handleUserInput(sc);
			}
			break;
		case 0:
			System.out.println("프로그램 종료");
			System.exit(0);
		default:
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			handleUserInput(sc);
			break;
		}
	}

	public int scanUserId(Scanner sc) {
		System.out.println("학번 입력");
		System.out.print(">>");
		int userId = sc.nextInt();
		sc.nextLine();
		return userId;
	}

	public String scanUserPw(Scanner sc) {
		System.out.println("비밀번호 입력 (최초 비밀번호 생년월일)");
		System.out.print(">>");
		String userPw = sc.next();
		sc.nextLine();
		return userPw;
	}

	public int getCurrentUserId() {
		return currentUserId;
	}

}
