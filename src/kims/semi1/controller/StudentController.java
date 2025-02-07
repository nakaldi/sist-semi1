package kims.semi1.controller;

import java.util.Scanner;

import kims.semi1.model.Department;
import kims.semi1.model.Student;
import kims.semi1.service.StudentService;

public class StudentController {
	private final StudentService studentService;
	private Student student;
	private Department department;

	public StudentController(int currentUserId) {
		this.studentService = new StudentService();
		Object[] temps = studentService.getStudentAndDepartmentInfo(currentUserId);
		student = (Student) temps[0];
		department = (Department) temps[1];
	}

	public void selectStudentMenu(Scanner sc) {

		printStudentInfo();

		// 1개인정보 수정, 2수강관리, 3출결관리, 4성적관리, 5로그아웃 메뉴가 있음
		System.out.println("1.학생 정보 수정");
		System.out.println("2.수강 관리");
		System.out.println("3.성적 관리");
		System.out.println("4.로그아웃");
		System.out.print(">> ");

		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			modifyStudentInfo(sc);
		}
	}

	public void printStudentInfo() {
		System.out.println("---------------------------학생 정보----------------------------");
		System.out.println("학번 : " + student.getStudentId() + " \t 이름 : " + student.getName());
		System.out.println("전화번호 : " + student.getPhone() + " \t 생년월일 : " + student.getBirthDate());
		System.out.println("email : " + student.getEmail());
		System.out.println("학과 : " + department.getName() + " \t 입학년도 : " + student.getEnrollmentYear());
		System.out.println("---------------------------------------------------------------");
	}

	public void modifyStudentInfo(Scanner sc) {
		// 원본 데이터를 받아와서, 수정하지 않은 정보는 다시 원본 데이터를 등록하도록 함.
		Student updatedStudent = new Student(student);

		while (true) {
			System.out.println("------------------학생 정보 수정------------------");
			System.out.println("1. 이름 \t 2. 전화번호 \t 3. email \t 4. 비밀번호");
			System.out.println("5. 등록 \t 6. 취소");
			System.out.print(">>");

			int input = sc.next().charAt(0) - '0';
			switch (input) {
			case 1:
				System.out.print("이름>>");
				updatedStudent.setName(sc.next());
				sc.nextLine();
				break;
			case 2:
				System.out.print("전화번호>>");
				updatedStudent.setPhone(sc.next());
				sc.nextLine();
				break;
			case 3:
				System.out.print("email>>");
				updatedStudent.setEmail(sc.next());
				sc.nextLine();
				break;
			case 4:
				System.out.print("비밀번호>>");
				updatedStudent.setPassword(sc.next());
				sc.nextLine();
				break;
			case 5:
				if (studentService.updateStudentInfo(updatedStudent) == null) {
					System.out.println("등록 실패.");
					break;
				} else {
					student = updatedStudent;
					System.out.println("등록 성공!");
					selectStudentMenu(sc);
					return;
				}
			case 6:
				System.out.println("취소되었습니다.");
				selectStudentMenu(sc);
				return;
			default:
				System.out.println("다시 입력해주세요.");
				break;
			}

		}
	}

}
