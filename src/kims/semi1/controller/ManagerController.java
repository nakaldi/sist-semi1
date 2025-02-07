package kims.semi1.controller;

import java.util.Scanner;

import kims.semi1.dao.ClassScheduleDao;
import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Student;

public class ManagerController {
	ClassScheduleDao classScheduledao;
	ClassSchedule classchedules;

	public ManagerController() {
		this.classScheduledao = new ClassScheduleDao();
		this.classchedules = new ClassSchedule();
	}

	public ManagerController(int currentUserId) {
		this.classScheduledao = new ClassScheduleDao();
		this.classchedules = new ClassSchedule();
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
			selectClassScheduleMenu(sc);
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		}

	}

	public void selectClassScheduleMenu(Scanner sc) {
		while (true) {
			System.out.println("1.시간표 조회");
			System.out.println("2.시간표 등록");
			System.out.println("3.시간표 삭제");
			System.out.print(">>");
			int input = sc.next().charAt(0) - '0';
			switch (input) {
			case 1:
				printClassScheduleInofo();
				break;
			case 2:
				saveClassScheduleInfo(sc);
				break;
			case 3:

				break;
			}
		}
	}

	// 데이터베이스에 저장되어있는 전체시간표를 보여주는 메소드
	public void printClassScheduleInofo() {

	}

	// 시간표 데이터 저장.
	public void saveClassScheduleInfo(Scanner sc) {
		System.out.print("강의ID>>");
		classchedules.setCourseId(sc.nextInt());
		System.out.print("요일>>");
		classchedules.setDayOfWeek(sc.next());
		System.out.print("시작시간>>");
		classchedules.setStartTime(sc.next());
		System.out.print("종료시간>>");
		classchedules.setEndTime(sc.next());
		System.out.println("1.등록 2.취소");
		int input = sc.nextInt();
		sc.nextLine();
		switch (input) {
		case 1:
			if (insertClassScheduleInfo(classchedules) == null) {
				System.out.println("등록실패");
			} else {
				System.out.println("등록성공");
			}
			break;
		case 2:
			System.out.println("취소되었습니다");
			break;
		}
	}

	public ClassSchedule insertClassScheduleInfo(ClassSchedule newClassSchedule) {
		return classScheduledao.insertClassSchedule(newClassSchedule) ? newClassSchedule : null;

	}

}
