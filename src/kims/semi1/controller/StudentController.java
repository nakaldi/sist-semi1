package kims.semi1.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kims.semi1.dao.GenericDao;
import kims.semi1.model.Building;
import kims.semi1.model.ClassSchedule;
import kims.semi1.model.Course;
import kims.semi1.model.CourseInfo;
import kims.semi1.model.Department;
import kims.semi1.model.Enrollment;
import kims.semi1.model.Professor;
import kims.semi1.model.Student;
import kims.semi1.service.StudentService;
import kims.semi1.util.DateUtils;

public class StudentController {
	private final StudentService studentService;
	private Student student;
	private Department department;
	private Building building;

	public StudentController(int currentUserId) {
		this.studentService = new StudentService();
		Object[] temps = studentService.getStudentAndDepartmentAndBuildingInfo(currentUserId);
		student = (Student) temps[0];
		department = (Department) temps[1];
		building = (Building) temps[2];
	}

	public String[] loadStudentMypageInfo() {
		List<String> infos = new ArrayList<String>();
		infos.add(student.getName());
		infos.add(student.getBirthDate().toString());
		infos.add(Integer.toString(student.getStudentId()));
		infos.add(student.getPhone());
		infos.add(student.getEmail());
		infos.add(student.getPassword());
		infos.add(department.getName());
		infos.add(building.getName());
		return infos.toArray(new String[0]);
	}

	public void updateStudentInfo(String name, String birthDate, String phone, String email, String password) {
		Student updatedStudent = new Student(student);
		updatedStudent.setName(name);
		updatedStudent.setBirthDate(DateUtils.convertStringToLocalDate(birthDate));
		updatedStudent.setPhone(phone);
		updatedStudent.setEmail(email);
		updatedStudent.setPassword(password);
		studentService.updateStudentInfo(updatedStudent);
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
				manageGrades(sc);
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
				statusCourses(student.getStudentId(), sc);
				break;
			case 3:

				break;
			case 4:

				return;
			}
		}
	}

	public void manageGrades(Scanner sc) {
		while (true) {
			System.out.println("------------------수강 관리------------------");
			System.out.println("1. 성적조회 \t 2. 석차  \t 3. 나가기 ");
			System.out.print(">>");

			int input = sc.next().charAt(0) - '0';
			sc.nextLine();

			switch (input) {
			case 1:
				searchGrades(sc);
				break;
			case 2:
				break;
			case 3:

				return;
			}
		}
	}

	private String selectSemester(Scanner sc) {
		String input = "";
		while (true) {
			System.out.print("학기 선택 1 or 2 \n>>");
			input = sc.next();
			sc.nextLine();
			if ("1".equals(input) || "2".equals(input)) {
				break;
			}
		}
		return input;
	}

	public Object[][] searchCourseInfosToArray(String semester) {
		List<CourseInfo> courseInfos = studentService.getCourseInfoBySemester(semester);
//		 "강의번호", "강의명", "교수명", "학과", "학점", "학기", "요일", "강의시간" 
		if (courseInfos.size() == 0) {
			return new Object[][] { { "", "", "등록된", "강의가", "없습니다", "", "", "" } };
		}

		return courseInfos.stream()
				.map(t -> new Object[] { t.getCourse().getCourseId(), t.getCourse().getName(),
						t.getProfessor().getName(), t.getDepartment().getName(), t.getCourse().getCredit(),
						t.getCourse().getSemester(), t.getClassSchedule().getDayOfWeek(),
						(t.getClassSchedule().getStartTime() + "-" + t.getClassSchedule().getEndTime()) })
				.toArray(size -> new Object[size][8]);
	}

	public Object[][] searchEnrollmentInfosToArray(String semester) {
		List<Enrollment> enrollmentInfos = studentService
				.getEnrollmentInfosByStudentIdAndSemester(student.getStudentId(), semester);

		if (enrollmentInfos.size() == 0) {
			return new Object[][] { { "", "아직", "신청된", "강의가", "없습니다", "", "", "" } };
		}
		return enrollmentInfos.stream()
				.map(t -> new Object[] { t.getCourseInfo().getCourse().getCourseId(),
						t.getCourseInfo().getCourse().getName(), t.getCourseInfo().getProfessor().getName(),
						t.getCourseInfo().getDepartment().getName(), t.getCourseInfo().getCourse().getCredit(),
						t.getCourseInfo().getCourse().getSemester(),
						t.getCourseInfo().getClassSchedule().getDayOfWeek(),
						(t.getCourseInfo().getClassSchedule().getStartTime() + "-"
								+ t.getCourseInfo().getClassSchedule().getEndTime()) })
				.toArray(size -> new Object[size][8]);
	}

	public String makeSyllabus(int courseId) {
		List<CourseInfo> courseInfos = studentService.getCourseInfoBySemester("1");
		courseInfos.addAll(studentService.getCourseInfoBySemester("2"));
		CourseInfo courseInfo = courseInfos.stream().filter(t -> t.getCourse().getCourseId() == courseId).findFirst()
				.orElse(null);
		if (courseInfo == null) {
			return null;
		}
		String coursePlan = "-------------------------------- 강의 계획서 ---------------------------------" + "\n\n"
				+ "강의명 : " + courseInfo.getCourse().getName() + "\t강의번호 : " + courseId + "\n" + "개설학기 : "
				+ courseInfo.getCourse().getSemester() + "\n" + "\n" + "개설학과 : " + courseInfo.getDepartment().getName()
				+ "\t" + "학점 : " + courseInfo.getCourse().getCredit() + "\n" + "\n" + "담당교수 : "
				+ courseInfo.getProfessor().getName() + "\n" + "이메일 : " + courseInfo.getProfessor().getEmail() + "\n"
				+ "전화번호 : " + courseInfo.getProfessor().getPhone() + "\n" + "\n" + "강의건물 : "
				+ courseInfo.getBuilding().getName() + "\t" + "강의실 : " + courseInfo.getUnit().getUnitId() + "\n" + "\n"
				+ "강의내용 : " + courseInfo.getCourse().getSyllabus();
		return coursePlan;
	}

	public boolean registerEnrollment(int courseId) {
		List<CourseInfo> courseInfos = studentService.getCourseInfoBySemester("1");
		courseInfos.addAll(studentService.getCourseInfoBySemester("2"));
		CourseInfo courseInfo = courseInfos.stream().filter(t -> t.getCourse().getCourseId() == courseId).findFirst()
				.orElse(null);

		String newCourseStartTime = courseInfo.getClassSchedule().getStartTime();
		String newCourseEndTime = courseInfo.getClassSchedule().getEndTime();
		List<Enrollment> enrollmentInfos = studentService
				.getEnrollmentInfosByStudentIdAndSemester(student.getStudentId(), courseInfo.getCourse().getSemester());

		for (Enrollment e : enrollmentInfos) {

//			if ((e.getCourseInfo().getCourse().getSemester() == courseInfo.getCourse().getSemester())
//					&& (e.getCourseInfo().getClassSchedule().getDayOfWeek()
//							.equals(courseInfo.getClassSchedule().getDayOfWeek()))) {
				if (isTimeOverlap(e.getCourseInfo().getClassSchedule().getStartTime(),
						e.getCourseInfo().getClassSchedule().getEndTime(), newCourseStartTime, newCourseEndTime)) {
					return false; // 학기, 요일, 시간이 겹치면 실패
				}
			}
//		}

		return studentService.registerEnrollment(student.getStudentId(), courseId);
	}

	private boolean isTimeOverlap(String start1, String end1, String start2, String end2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		LocalTime s1 = LocalTime.parse(start1, formatter);
		LocalTime e1 = LocalTime.parse(end1, formatter);
		LocalTime s2 = LocalTime.parse(start2, formatter);
		LocalTime e2 = LocalTime.parse(end2, formatter);
		return (s1.equals(s2) || e1.equals(e2) || (s1.isBefore(e2) && e1.isAfter(s2)));
	}

	public void searchCourses(Scanner sc) {
		String input = selectSemester(sc);

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

	private void searchEnrollments(int studentId, String input) {
		System.out.println("-----------------------" + input + "학기 수강 목록 --------------------------");
		List<Enrollment> enrollmentInfos = studentService.getEnrollmentInfosByStudentIdAndSemester(studentId, input);
		if (enrollmentInfos.size() == 0) {
			System.out.println("수강 정보가 없습니다.");
			return;
		}
		enrollmentInfos.stream()
				.forEach(t -> System.out.println("강의명 : " + t.getCourseInfo().getCourse().getName() + "\t 학기 : "
						+ t.getCourseInfo().getCourse().getSemester() + "\t 건물 : "
						+ t.getCourseInfo().getBuilding().getName() + " " + t.getCourseInfo().getUnit().getUnitId()));
	}

	private void searchGrades(Scanner sc) {
		String input = selectSemester(sc);
		List<Enrollment> grades = studentService.getEnrollmentInfosByStudentIdAndSemester(student.getStudentId(),
				input);
		grades.stream().forEach(
				t -> System.out.println(t.getCourseInfo().getCourse().getName() + "  " + t.getGrade().getGrade()));
	}

	// 한 학기 취득학점 , 성적 평균 조회
	private void searchPrintGrades(Scanner sc) {
		String input = selectSemester(sc);
		double sum = 0.0;
		double avg = 0.0;
		List<Enrollment> grades = studentService.getEnrollmentInfosByStudentIdAndSemester(student.getStudentId(),
				input);
		sum = grades.stream().mapToDouble(t -> Double.parseDouble(t.getCourseInfo().getCourse().getCredit())).sum();
		avg = grades.stream().mapToDouble(t -> t.getGrade().getGrade()).average().orElse(0.0);
		System.out.println("학기:" + input + "학기|" + "취득학점:" + sum + "|평균평점:" + avg + "|");
	}

	public void statusCourses(int studentId, Scanner sc) {
		String input = selectSemester(sc);

		System.out.println("----------------------- " + input + " 학기 강의 정보--------------------------");
		System.out.println("강의번호     강의명                                  교수명     학과            요일    시작교시  종료교시");
		List<Enrollment> EnrollmentInfos = studentService.getEnrollmentInfosByStudentIdAndSemester(studentId, input);
		if (EnrollmentInfos.size() == 0) {
			System.out.println("강의 정보가 없습니다.");
		}
		EnrollmentInfos.stream().forEach(t -> {
			Course c = t.getCourseInfo().getCourse();
			Department d = t.getCourseInfo().getDepartment();
			Professor p = t.getCourseInfo().getProfessor();
			ClassSchedule s = t.getCourseInfo().getClassSchedule();
			System.out.println(c.getCourseId() + "\t"
					+ (c.getName().length() < 33 ? c.getName() + (" ").repeat(33 - c.getName().length()) : c.getName())
					+ "\t" + p.getName() + "\t"
					+ (d.getName().length() < 10 ? d.getName() + (" ").repeat(10 - d.getName().length()) : d.getName())
					+ "\t" + s.getDayOfWeek() + "\t" + s.getStartTime() + " - " + s.getEndTime());
		});
	}
}
