package kims.semi1.controller;

import java.time.LocalDate;
import java.util.Scanner;

import kims.semi1.service.FindingIdService;
import kims.semi1.service.LoginService;
import kims.semi1.util.DateUtils;

public class LoginController {

	private final LoginService loginService;
	private final FindingIdService findingIdService;
	private int currentUserId;
	Scanner sc;
	ProfessorController professorControlloer;

	public LoginController() {
		loginService = new LoginService();
		findingIdService = new FindingIdService();
	}

	public void handleUserInput(Scanner sc) {

		System.out.println("1. 로그인");
		System.out.println("2. 아이디 찾기");
		System.out.println("0. 프로그램 종료");
		System.out.print(">>");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			System.out.println("====로그인====");
			int loggedInUserId = loginService.loginUser(scanUserId(sc), scanUserPw(sc));

			if (loggedInUserId == -1) {
				System.out.println("아이디가 없습니다. 최초화면으로 돌아갑니다.");
				handleUserInput(sc);
			} else if (loggedInUserId == -2) {
				System.out.println("비밀번호가 일치하지 않습니다. 최초화면으로 돌아갑니다.");
				handleUserInput(sc);
			} else {
				currentUserId = loggedInUserId;

				professorControlloer = new ProfessorController(currentUserId);
				System.out.println("로그인 성공");
				professorControlloer.printProfessorMenu(sc);
			}
			break;
		case 2:
			System.out.println("==아이디찾기==");
			System.out.print("이름>>");
			String name = sc.next();
			sc.nextLine();

			System.out.print("생년월일>>");
			LocalDate date = DateUtils.convertStringToLocalDate(sc.next());
			sc.nextLine();
			System.out.println(date);

			System.out.print("이메일>>");
			String email = sc.next();
			sc.nextLine();

			int foundId = findingIdService.findId(name, date, email);
			if (foundId == -1) {
				System.out.println("등록된 아이디가 없습니다.");
				handleUserInput(sc);
			} else {
				System.out.println("아이디는 " + foundId + "입니다.");
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
