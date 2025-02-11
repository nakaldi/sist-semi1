package kims.semi1.controller;

import java.time.LocalDate;
import java.util.Scanner;

import kims.semi1.service.FindingIdService;
import kims.semi1.service.LoginService;
import kims.semi1.util.DateUtils;
import kims.semi1.view.LoginFrame;
import kims.semi1.view.ManagerFrame;
import kims.semi1.view.ProfessorFrame;
import kims.semi1.view.StudentFrame;

public class LoginController {

	private final LoginService loginService;
	private final FindingIdService findingIdService;
	private int currentUserId;
	Scanner sc;
	ProfessorController professorControlloer;
	private StudentController studentController;
	private ManagerController managerController;
	private LoginFrame loginFrame;

	public LoginController() {
		this.loginService = new LoginService();
		this.findingIdService = new FindingIdService();
	}

	public LoginController(LoginFrame loginFrame) {
		loginService = new LoginService();
		findingIdService = new FindingIdService();
		this.loginFrame = loginFrame;
	}

	public void handleLoginButtonClick(String userIdStr, String password) {
		int userId = 0;
		try {
			userId = Integer.parseInt(userIdStr);
		} catch (NumberFormatException e) {
			loginFrame.showMessage("아이디는 숫자만 입력 가능합니다.");
			return;
		}
		int loggedInUserId = loginService.loginUser(userId, password);

		if (loggedInUserId == -1) {
			System.out.println("아이디가 없습니다. 최초화면으로 돌아갑니다.");
			loginFrame.showMessage("아이디가 없습니다.");
		} else if (loggedInUserId == -2) {
			System.out.println("비밀번호가 일치하지 않습니다. 최초화면으로 돌아갑니다.");
			loginFrame.showMessage("비밀번호가 다릅니다.");
		} else {
			currentUserId = loggedInUserId;
			System.out.println("로그인 성공");

			if (Integer.toString(currentUserId).charAt(4) == '1') {
				loginFrame.dispose();
				StudentFrame sf = new StudentFrame();
				StudentController studentController = new StudentController(currentUserId, sf);
				sf.setStudentController(studentController);
			} else if (Integer.toString(currentUserId).charAt(4) == '2') {
				loginFrame.dispose();
				ProfessorFrame pf = new ProfessorFrame(loginFrame, currentUserId);
				ProfessorController professorController = new ProfessorController(currentUserId, pf);
				pf.setProfessorController(professorController);
			} else if (Integer.toString(currentUserId).charAt(4) == '3') {
				loginFrame.dispose();
				ManagerFrame mf = new ManagerFrame();
				ManagerController managerController = new ManagerController(currentUserId, mf);
				mf.setManagerController(managerController);
			}
		}
		return;
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
				System.out.println("로그인 성공");

				if (Integer.toString(currentUserId).charAt(4) == '1') {
					studentController = new StudentController(currentUserId);
					studentController.selectStudentMenu(sc);
				} else if (Integer.toString(currentUserId).charAt(4) == '2') {
					professorControlloer = new ProfessorController(currentUserId);
					professorControlloer.printProfessorMenu(sc);
				} else if (Integer.toString(currentUserId).charAt(4) == '3') {
					managerController = new ManagerController(currentUserId);
					managerController.selectManagertMenu(sc);
				}
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
