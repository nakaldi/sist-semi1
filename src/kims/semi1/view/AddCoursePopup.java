package kims.semi1.view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddCoursePopup extends Dialog implements ActionListener {
	private TextField txtCourseName, txtCourseBuilding, txtCourseSemester, txtCourseCredit, txtCourseDescription;
	private Button btnAdd, btnCancel;
	private ProfessorFrame parentFrame;

	public AddCoursePopup(ProfessorFrame parentFrame) {
		super(parentFrame, "강의 등록", true);
		this.parentFrame = parentFrame;
		setSize(400, 300);
		setLayout(new GridLayout(6, 2));

		Label lblCourseName = new Label("강의명:");
		txtCourseName = new TextField(20); // 텍스트 필드 생성 및 초기화
		add(lblCourseName);
		add(txtCourseName);

		Label lblCourseBuilding = new Label("강의건물:");
		txtCourseBuilding = new TextField(20); // 텍스트 필드 생성 및 초기화
		add(lblCourseBuilding);
		add(txtCourseBuilding);

		Label lblCourseSemester = new Label("학기:");
		txtCourseSemester = new TextField(20); // 텍스트 필드 생성 및 초기화
		add(lblCourseSemester);
		add(txtCourseSemester);

		Label lblCourseCredit = new Label("학점:");
		txtCourseCredit = new TextField(20); // 텍스트 필드 생성 및 초기화
		add(lblCourseCredit);
		add(txtCourseCredit);

		Label lblCourseDescription = new Label("강의설명:");
		txtCourseDescription = new TextField(20); // 텍스트 필드 생성 및 초기화
		add(lblCourseDescription);
		add(txtCourseDescription);

		btnAdd = new Button("등록");
		btnAdd.addActionListener(this);
		btnCancel = new Button("취소");
		btnCancel.addActionListener(this);
		add(btnAdd);
		add(btnCancel);

		// 팝업 창 닫기
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// 프레임 가운데 배치
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			// 1. 팝업 창에서 입력된 값 가져오기
			String courseName = txtCourseName.getText();
			String courseBuilding = txtCourseBuilding.getText();
			String courseSemester = txtCourseSemester.getText();
			String courseCredit = txtCourseCredit.getText();
			String courseDescription = txtCourseDescription.getText();

			// 2. 입력값 검증 (필요한 경우)

			// 3. ProfessorFrame의 addCourseFromPopup() 메서드 호출하여 강의 추가
			parentFrame.addCourseFromPopup(courseName, courseBuilding, courseSemester, courseCredit, courseDescription);

			// 4. 팝업 창 닫기
			dispose();
		} else if (e.getSource() == btnCancel) {
			dispose();
		}
	}
}
