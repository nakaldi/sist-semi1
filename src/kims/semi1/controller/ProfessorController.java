package kims.semi1.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import kims.semi1.config.DBConnector;

public class ProfessorController {

	Scanner sc;
	int currentUserId;

	public ProfessorController() {

	}

	public ProfessorController(int currentUserId) {
		this.currentUserId = currentUserId;
	}

	public void printProfessorMenu(Scanner sc) {

		printProfessorInfo();
		
		System.out.println("1.교수 정보 수정");
		System.out.println("2.강의 관리");
		System.out.println("3.학생 출결 관리");
		System.out.println("4.학생 성적 관리");
		System.out.println("5.로그아웃");
		System.out.println("메뉴 > ");

		int input = sc.next().charAt(0) - '0';
		sc.nextLine();

		switch (input) {
		case 1:
			
		}

	}

	private void printProfessorInfo() {
		System.out.println("==교수 정보==");

		String sql = "select name,phone,email,hire_date from professors where professor_id = ? ";
		try (Connection conn = DBConnector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, currentUserId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String professorName = rs.getString("name");
					String professorPhone = rs.getString("phone");
					String professorEmail = rs.getString("email");
					LocalDate professorHireDate = rs.getDate("hire_date").toLocalDate();

					System.out.println(professorName + " | " + professorPhone + " | " + professorEmail + " | "
							+ professorHireDate);
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
