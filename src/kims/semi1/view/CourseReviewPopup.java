package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.List;

class CourseReviewPopup extends Dialog {
    private List reviewList;

    public CourseReviewPopup(Frame parent, String title, boolean modal, String courseId) {
        super(parent, title, modal);
        setSize(400, 300);
        setLayout(new BorderLayout());

        reviewList = new List();
        add(new Label("강의평가 목록:"), BorderLayout.NORTH);
        add(reviewList, BorderLayout.CENTER);

        loadReviews(courseId);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(parent);
    }

    private void loadReviews(String courseId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(ProfessorFrame.DB_URL, ProfessorFrame.USER, ProfessorFrame.PASS);
            String sql = "SELECT s.name, g.student_review FROM grades g " +
                         "JOIN enrollments e ON g.enrollment_id = e.enrollment_id " +
                         "JOIN students s ON e.student_id = s.student_id " +
                         "WHERE e.course_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courseId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String studentName = rs.getString("name");
                String review = rs.getString("student_review");
                reviewList.add(studentName + ": " + review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
        }
    }
}