package kims.semi1.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame extends Frame {
    private CardLayout cardLayout;
    private Panel mainPanel;

    public ManagerFrame() { // ��üȭ�� ������
        setTitle("�л�����ý���(������)");
        setSize(400, 400);  // â ũ�� ����
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new Panel(cardLayout);

        // Ȩ ȭ�� �� �ð�ǥ ���� ȭ�� �߰�
        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(createSchedulePanel(), "SCHEDULE"); //  �̸� �߰��س���

        add(mainPanel);
        setVisible(true);

        // ������ ���� �̺�Ʈ �߰�
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    /// �Ŵ��� Ȩȭ��
    private Panel createHomePanel() {
        Panel homePanel = new Panel(new BorderLayout());

        // ��� ���� + �α׾ƿ� ��ư
        Panel topPanel = new Panel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(500, 50));

        Label titleLabel = new Label("�л�����ý���(������)", Label.LEFT);
        titleLabel.setFont(new Font("���� ���", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);

        Button btnLogout = new Button("�α׾ƿ�");
        btnLogout.setFont(new Font("���� ���", Font.PLAIN, 14));
        btnLogout.setBackground(Color.white);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);

        homePanel.add(topPanel, BorderLayout.NORTH);

        // ��ư �г� (�ٱ��� ���� �߰�)
        Panel outerPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 30, 35));
        Panel buttonPanel = new Panel(new GridLayout(4, 1, 10, 15));

        // ��ư ���� �� ��Ÿ�� ����
        Button btnSchedule = createStyledButton("�ð�ǥ ����");
        Button btnProfessor = createStyledButton("���� ���� ����");
        Button btnStudent = createStyledButton("�л� ���� ����");
        Button btnLecture = createStyledButton("���� ��ȸ");

        buttonPanel.add(btnSchedule);
        buttonPanel.add(btnProfessor);
        buttonPanel.add(btnStudent);
        buttonPanel.add(btnLecture);

        outerPanel.add(buttonPanel);
        homePanel.add(outerPanel, BorderLayout.CENTER);

        // "�ð�ǥ ����" ��ư Ŭ�� �� �г� ����
        btnSchedule.addActionListener(e -> {
            cardLayout.show(mainPanel, "SCHEDULE"); // ���� �г��� �����ֱ⸸ ��
        });

        return homePanel;
    }

    // ��ư ��Ÿ�� ���� �Լ�
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("���� ���", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    // �ð�ǥ ���� ȭ�� ����
    private Panel createSchedulePanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1200, 800));

        // ��� �� (�޴� + �α׾ƿ� ��ư)
        Panel topPanel = new Panel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);

        Label titleLabel = new Label("�ð�ǥ ����", Label.LEFT);
        titleLabel.setFont(new Font("���� ���", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        Button btnLogout = new Button("�α׾ƿ�");
        btnLogout.setFont(new Font("���� ���", Font.BOLD, 12));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // ���� �г� (����: ��� �г�, ������: �ð�ǥ ���� �г�)
        Panel mainPanel = new Panel(new GridLayout(1, 2, 10, 10));

        // ����: ���� ��� �г�
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
        Button btnRegister = new Button("���");

        gbc.gridx = 0; gbc.gridy = 0;
        registerPanel.add(new Label("���� ID:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtLectureID, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        registerPanel.add(new Label("����:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtDay, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        registerPanel.add(new Label("���۽ð�:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtStartTime, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        registerPanel.add(new Label("����ð�:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(txtEndTime, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        registerPanel.add(btnRegister, gbc);

        mainPanel.add(registerPanel);

        // ������: �ð�ǥ ���� �г�
        Panel tablePanel = new Panel(new BorderLayout());

        // ��ư �г�
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        Button btnViewAll = new Button("��ü�ð�ǥ��ȸ");
        Button btnUpdate = new Button("�ð�ǥ ����");
        Button btnDelete = new Button("�ð�ǥ ����");

        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        tablePanel.add(buttonPanel, BorderLayout.NORTH);

        // �ð�ǥ ���̺�
        Panel gridTable = new Panel(new GridLayout(5, 5, 2, 2));
        String[] headers = {"�ð�ǥID", "���Ǹ�", "����", "���۽ð�", "����ð�"};

        for (String header : headers) {
            Label label = new Label(header, Label.CENTER);
            label.setFont(new Font("���� ���", Font.BOLD, 12));
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
