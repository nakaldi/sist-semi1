package kims.semi1.controller;

import java.util.List;
import java.util.Scanner;

import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Course;
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Department;
import kims.semi1.model.Enrollment;
import kims.semi1.model.Professor;
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

		while (true) {
			printStudentInfo();

			// 1개인정보 수정, 2수강관리, 3출결관리, 4성적관리, 5로그아웃 메뉴가 있음
			System.out.println("1.학생 정보 수정 \t 2.수강 관리 \t 3.성적 관리 \t 4.로그아웃");
			System.out.print(">> ");

			int input = sc.next().charAt(0) - '0';
			sc.nextLine();

			switch (input) {
			case 1:
				modifyStudentInfo(sc);
				break;
			case 2:
				manageEnrollment(sc);
				break;
			case 3:
				break;
			case 4:
				return;
			}
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
			sc.nextLine();

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
					return;
				}
			case 6:
				System.out.println("취소되었습니다.");
				return;
			default:
				System.out.println("다시 입력해주세요.");
				break;
			}
		}
	}

	public void manageEnrollment(Scanner sc) {

		while (true) {
			System.out.println("------------------수강 관리------------------");
			System.out.println("1. 수강신청 \t 2. 수강현황 \t 3. 수강취소 \t 4. 나가기");
			System.out.print(">>");

			int input = sc.next().charAt(0) - '0';
			sc.nextLine();

			switch (input) {
			case 1:
				searchCourses(sc);
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				return;
			}
		}
	}

	public void searchCourses(Scanner sc) {
		String input;
		while (true) {
			System.out.print("학기 선택 1 or 2 \n>>");
			input = sc.next();
			sc.nextLine();
			if ("1".equals(input) || "2".equals(input)) {
				break;
			}
		}
		System.out.println("----------------------- " + input + " 학기 강의 정보--------------------------");
		System.out.println("강의번호     강의명                                  교수명     학과            요일    시작교시  종료교시");
		List<CourseInfo> courseInfos = studentService.getCourseInfoBySemester(input);
		if (courseInfos.size() == 0) {
			System.out.println("강의 정보가 없습니다.");
		}
		courseInfos.stream().forEach(t -> {
			Course c = t.getCourse();
			Department d = t.getDepartment();
			Professor p = t.getProfessor();
			ClassSchedule s = t.getClassSchedule();
			System.out.println(c.getCourseId() + "\t"
					+ (c.getName().length() < 33 ? c.getName() + (" ").repeat(33 - c.getName().length()) : c.getName())
					+ "\t" + p.getName() + "\t"
					+ (d.getName().length() < 10 ? d.getName() + (" ").repeat(10 - d.getName().length()) : d.getName())
					+ "\t" + s.getDayOfWeek() + "\t" + s.getStartTime() + " - " + s.getEndTime());
		});
		searchEnrollments(student.getStudentId(), input);
		showSyllabus(sc, courseInfos);
	}

	private void showSyllabus(Scanner sc, List<CourseInfo> courseInfos) {
		System.out.print("자세히 볼 강의 번호 입력 >>");
		int input = sc.nextInt();
		sc.nextLine();

		CourseInfo courseInfo = courseInfos.stream().filter(t -> t.getCourse().getCourseId() == input).findFirst()
				.orElse(null);
		if (courseInfo == null) {
			System.out.println("입력한 강의가 없습니다.");
			return;
		}
		System.out.println("-------------------------강의계획서--------------------------");
		System.out.println("강의명 : " + courseInfo.getCourse().getName() + "\t개설학기 : "
				+ courseInfo.getCourse().getSemester() + "\t학점 : " + courseInfo.getCourse().getCredit() + "\t개설학과 : "
				+ courseInfo.getDepartment().getName());
		System.out.println("담당교수 : " + courseInfo.getProfessor().getName() + "\t이메일 : "
				+ courseInfo.getProfessor().getEmail() + "\t전화번호 : " + courseInfo.getProfessor().getPhone());
		System.out.println(
				"강의건물 : " + courseInfo.getBuilding().getName() + "\t강의실 : " + courseInfo.getUnit().getUnitId());
		System.out.println("강의내용 : " + courseInfo.getCourse().getSyllabus());

		System.out.print("1.등록 \t 2.취소\n>>");
		int input2 = sc.next().charAt(0) - '0';

		if (input2 == 1) {
			if (studentService.registerEnrollment(student.getStudentId(), courseInfo.getCourse().getCourseId())) {
				System.out.println("등록 완료");
			} else {
				System.out.println("등록 실패");
			}
		} else {
			showSyllabus(sc, courseInfos);
			return;
		}
	}
//TODO 중복 강의 수강 불가하게 해야함.
	private void searchEnrollments(int studentId, String input) {
		System.out.println("-----------------------" + input + "학기 수강 목록 --------------------------");
		List<Enrollment> enrollmentInfos = studentService.getEnrollmentInfosByStudentIdAndSemester(studentId, input);
		if (enrollmentInfos.size() == 0) {
			System.out.println("수강 정보가 없습니다.");
			return;
		}
		enrollmentInfos.stream().forEach(t -> System.out.println(t.getCourseInfo().getCourse().getName()));
		
	}
}
