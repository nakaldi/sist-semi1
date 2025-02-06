package kims.semi1.controller;

import java.util.Scanner;

import kims.semi1.model.Student;
import kims.semi1.service.StudentService;

public class StudentController {
	private final int currentUserId;
	private final StudentService studentService;

	public StudentController(int currentUserId) {
		this.currentUserId = currentUserId;
		this.studentService = new StudentService();
	}

	public void selectStudentMenu(Scanner sc) {
		
		Student student = studentService.getStudentInfo(currentUserId);
		studentService.printStudentInfo(student);
		
		//1개인정보 수정, 2수강관리, 3출결관리, 4성적관리, 5로그아웃 메뉴가 있음
		System.out.println("1.학생 정보 수정");
		System.out.println("2.수강 관리");
		System.out.println("3.성적 관리");
		System.out.println("4.로그아웃");
		System.out.print(">> ");

		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:

		}
	}

}
