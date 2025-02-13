package kims.semi1.view;

// GradeRegisterPopup.java
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class GradeRegisterPopup extends Dialog implements ActionListener {
    private ProfessorFrame parentFrame;
    private Button btnRegister, btnCancel;
    private TextField txtCourseID, txtStudentID, txtGrade;

    public GradeRegisterPopup(ProfessorFrame parentFrame, String title, boolean modal) {
        super(parentFrame, title, modal);
        this.parentFrame = parentFrame;

        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        Label lblCourseID = new Label("강의 ID:");
        txtCourseID = new TextField(10);
        Label lblStudentID = new Label("학생 ID:");
        txtStudentID = new TextField(10);
        Label lblGrade = new Label("성적:");
        txtGrade = new TextField(10);

        btnRegister = new Button("등록");
        btnCancel = new Button("취소");

        add(lblCourseID);
        add(txtCourseID);
        add(lblStudentID);
        add(txtStudentID);
        add(lblGrade);
        add(txtGrade);
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
            // 1. 텍스트 필드에서 값 가져오기
            String courseID = txtCourseID.getText();
            String studentID = txtStudentID.getText();
            String grade = txtGrade.getText();

            // 2. ProfessorFrame의 registerGrade 메서드 호출하여 성적 등록
            parentFrame.registerGrade(courseID, studentID, grade);

            // 3. 팝업 닫기
            dispose();
        } else if (e.getSource() == btnCancel) {
            // 취소 버튼 클릭 시 동작
            dispose(); // 팝업 닫기
        }
    }
}
