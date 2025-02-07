package kims.semi1.controller;
import java.util.*;

public class ManagerController {
	
	int currentUserId;
	
	public ManagerController(){
		
	}
	
	public ManagerController(int currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	public void selectManagerMenu(Scanner sc) {
		
		
		System.out.println("1.시간표 관리");
		System.out.println("2.교수 정보 관리");
		System.out.println("3.학생 정보 관리");
		System.out.println("4.강의 정보 조회");
		System.out.println("5.로그아웃");
		System.out.print(">> ");
		
		
		
	}
	
}
