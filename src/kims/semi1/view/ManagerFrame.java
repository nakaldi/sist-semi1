package kims.semi1.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame extends Frame {
    private CardLayout cardLayout;
    private Panel mainPanel;

    public ManagerFrame() { // 전체화면 프레임
        setTitle("학사관리시스템(교직원)");
        setSize(400, 400);  // 창 크기 조정
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new Panel(cardLayout);

        // 홈 화면 및 시간표 관리 화면 추가
        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(createSchedulePanel(), "SCHEDULE"); //  미리 추가해놓기

        add(mainPanel);
        setVisible(true);

        // 윈도우 종료 이벤트 추가
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    /// 매니저 홈화면
    private Panel createHomePanel() {
        Panel homePanel = new Panel(new BorderLayout());

        // 상단 제목 + 로그아웃 버튼
        Panel topPanel = new Panel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(500, 50));

        Label titleLabel = new Label("학사관리시스템(교직원)", Label.LEFT);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);

        Button btnLogout = new Button("로그아웃");
        btnLogout.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        btnLogout.setBackground(Color.white);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);

        homePanel.add(topPanel, BorderLayout.NORTH);

        // 버튼 패널 (바깥쪽 여백 추가)
        Panel outerPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 30, 35));
        Panel buttonPanel = new Panel(new GridLayout(4, 1, 10, 15));

        // 버튼 생성 및 스타일 적용
        Button btnSchedule = createStyledButton("시간표 관리");
        Button btnProfessor = createStyledButton("교수 정보 관리");
        Button btnStudent = createStyledButton("학생 정보 관리");
        Button btnLecture = createStyledButton("강의 조회");

        buttonPanel.add(btnSchedule);
        buttonPanel.add(btnProfessor);
        buttonPanel.add(btnStudent);
        buttonPanel.add(btnLecture);

        outerPanel.add(buttonPanel);
        homePanel.add(outerPanel, BorderLayout.CENTER);

        // "시간표 관리" 버튼 클릭 시 패널 변경
        btnSchedule.addActionListener(e -> {
            cardLayout.show(mainPanel, "SCHEDULE"); // 기존 패널을 보여주기만 함
        });

        return homePanel;
    }

    // 버튼 스타일 적용 함수
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    // 시간표 관리 화면 생성
    private Panel createSchedulePanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1200, 800));

        // 상단 바 (메뉴 + 로그아웃 버튼)
        Panel topPanel = new Panel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);

        Label titleLabel = new Label("시간표 관리", Label.LEFT);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        Button btnLogout = new Button("로그아웃");
        btnLogout.setFont(new Font("맑은 고딕", Font.BOLD, 12));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // 메인 패널 (왼쪽: 등록 패널, 오른쪽: 시간표 관리 패널)
        Panel mainPanel = new Panel(new GridLayout(1, 2, 10, 10));

        // 왼쪽: 강의 등록 패널
        Panel registerPanel = new Panel(new GridBagLayout());
        registerPanel.setBackground(Color.LIGHT_GRAY);
        registerPanel.setPreferredSize(new Dimension(300, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        TextField txtLectureID = new TextField(12);
        TextField txtDay = new TextField(12);
        TextField txtStartTime = new TextField(12);
        TextField txtEndTime = new TextField(12);
        Button btnRegister = new Button("등록");

        gbc.gridx = 0; gbc.gridy = 0;
        registerPanel.add(new Label("강의 ID:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtLectureID, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        registerPanel.add(new Label("요일:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtDay, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        registerPanel.add(new Label("시작시간:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtStartTime, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        registerPanel.add(new Label("종료시간:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtEndTime, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        registerPanel.add(btnRegister, gbc);

        mainPanel.add(registerPanel);

        // 오른쪽: 시간표 관리 패널
        Panel tablePanel = new Panel(new BorderLayout());

        // 버튼 패널
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        Button btnViewAll = new Button("전체시간표조회");
        Button btnUpdate = new Button("시간표 수정");
        Button btnDelete = new Button("시간표 삭제");

        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        tablePanel.add(buttonPanel, BorderLayout.NORTH);

        // 시간표 테이블
        Panel gridTable = new Panel(new GridLayout(5, 5, 2, 2));
        String[] headers = {"시간표ID", "강의명", "요일", "시작시간", "종료시간"};

        for (String header : headers) {
            Label label = new Label(header, Label.CENTER);
            label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
            label.setBackground(Color.DARK_GRAY);
            label.setForeground(Color.WHITE);
            gridTable.add(label);
        }

        for (int i = 0; i < 20; i++) {
            gridTable.add(new Label(" ", Label.CENTER));
        }

        tablePanel.add(gridTable, BorderLayout.CENTER);
        mainPanel.add(tablePanel);

        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        new ManagerFrame();
    }
}
