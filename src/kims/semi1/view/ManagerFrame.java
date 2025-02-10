package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagerFrame extends Frame {
	public ManagerFrame() {
		setTitle("학사관리시스템");
		setSize(600, 400);
		setLayout(new BorderLayout());

		Panel topPanel = new Panel();
		topPanel.setLayout(new FlowLayout());
		Button btnMyPage = new Button("마이페이지");
		Button btnCourseReg = new Button("수강신청");
		Button btnCourseStatus = new Button("수강현황");
		Button btnGrades = new Button("성적관리");

		topPanel.add(btnMyPage);
		topPanel.add(btnCourseReg);
		topPanel.add(btnCourseStatus);
		topPanel.add(btnGrades);

		add(topPanel, BorderLayout.NORTH);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setVisible(true);
	}
}
