package kims.semi1.controller;

import java.util.Scanner;

public class ManagerController {
	public ManagerController() {

	}

	public ManagerController(int currentUserId) {

	}

	public void selectManagertMenu(Scanner sc) {
		System.out.println("1. 시간표 관리");
		System.out.println("2. 교수정보 관리");
		System.out.println("3. 학생 정보 관리");
		System.out.println("4. 강의 정보 관리");
		System.out.println("5. 로그아웃");
		System.out.print("메뉴>");
		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:

		}

	}
}
