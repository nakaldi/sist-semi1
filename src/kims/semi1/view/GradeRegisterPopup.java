package kims.semi1.view;

import java.awt.*;
import java.awt.event.*;

public class GradeRegisterPopup extends Dialog implements ActionListener {
    private TextField txtCourseIDGrade, txtStudentName, txtStudentGrade;
    private Button btnRegister, btnCancel;
    private ProfessorFrame parentFrame;

    public GradeRegisterPopup(ProfessorFrame parentFrame, String title, boolean modal) {
        super(parentFrame, title, modal);
        this.parentFrame = parentFrame;

        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        Label lblCourseIDGrade = new Label("강의 ID:");
        txtCourseIDGrade = new TextField(20);
        Label lblStudentName = new Label("학생 이름:");
        txtStudentName = new TextField(20);
        Label lblStudentGrade = new Label("성적:");
        txtStudentGrade = new TextField(20);

        btnRegister = new Button("등록");
        btnCancel = new Button("취소");

        add(lblCourseIDGrade);
        add(txtCourseIDGrade);
        add(lblStudentName);
        add(txtStudentName);
        add(lblStudentGrade);
        add(txtStudentGrade);
        add(btnRegister);
        add(btnCancel);

        btnRegister.addActionListener(this);
        btnCancel.addActionListener(this);

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

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            // 1. 팝업 창에서 입력된 값 가져오기
            String courseId = txtCourseIDGrade.getText();
            String studentName = txtStudentName.getText();
            String grade = txtStudentGrade.getText();

            // parentFrame이 null인지 확인
            if (parentFrame == null) {
                System.err.println("오류: parentFrame이 null입니다.");
                return;
            }

            // 2. ProfessorFrame의 registerGradeFromPopup() 메서드 호출하여 성적 등록
            parentFrame.registerGradeFromPopup(courseId, studentName, grade);

            // 3. 팝업 닫기
            dispose();
        } else if (e.getSource() == btnCancel) {
            // 취소 버튼 클릭 시 동작
            dispose(); // 팝업 닫기
        }
    }
}
