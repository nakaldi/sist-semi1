package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kims.semi1.controller.StudentController;

public class StudentEnrollmentPanel {
	JFrame frame;
	StudentController studentController;
	public JLabel titleLabel1;
	public JTable table1;
	public JLabel titleLabel2;
	public JTable table2;
	public StudentEnrollmentPanel(JFrame frame, StudentController studentController) {
		this.studentController = studentController;
		this.frame = frame;
	}
	
	public Panel createEnrollmentPanel() {
		Panel innerPanel = new Panel();
		GridBagLayout gbl_innerPanel = new GridBagLayout();
		gbl_innerPanel.columnWidths = new int[] { 850 };
		gbl_innerPanel.rowHeights = new int[] {120, 640, 50};
		gbl_innerPanel.columnWeights = new double[] { 1.0 };
		gbl_innerPanel.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		innerPanel.setLayout(gbl_innerPanel);

		JPanel headPanel = new JPanel();
		
			GridBagConstraints gbc_headPanel = new GridBagConstraints();
			gbc_headPanel.anchor = GridBagConstraints.NORTH;
			gbc_headPanel.insets = new Insets(0, 0, 5, 0);
			gbc_headPanel.fill = GridBagConstraints.BOTH;
			gbc_headPanel.gridx = 0;
			gbc_headPanel.gridy = 0;
			innerPanel.add(headPanel, gbc_headPanel);
			GridBagLayout gbl_headPanel = new GridBagLayout();
			gbl_headPanel.columnWidths = new int[] { 300, 550 };
			gbl_headPanel.rowHeights = new int[] { 100, 20 };
			gbl_headPanel.columnWeights = new double[] { 0.0 };
			gbl_headPanel.rowWeights = new double[] { 0.0, 0.0 };
			headPanel.setLayout(gbl_headPanel);

			JLabel headTitleLabel = new JLabel("수강 신청");
			headTitleLabel.setBackground(new Color(0, 0, 0));
			headTitleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 28));
			GridBagConstraints gbc_headTitleLabel = new GridBagConstraints();
			gbc_headTitleLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_headTitleLabel.anchor = GridBagConstraints.WEST;
			gbc_headTitleLabel.insets = new Insets(30, 10, 5, 0);
			gbc_headTitleLabel.gridx = 0;
			gbc_headTitleLabel.gridy = 0;
			headPanel.add(headTitleLabel, gbc_headTitleLabel);

			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.anchor = GridBagConstraints.NORTH;
			gbc_separator.fill = GridBagConstraints.HORIZONTAL;

			separator.setForeground(new Color(128, 128, 128));
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 1;
			headPanel.add(separator, gbc_separator);
			
	        GridBagLayout gbl_centerPanel = new GridBagLayout();
	        gbl_centerPanel.rowHeights = new int[] {40, 200, 40, 40, 160};
	        gbl_centerPanel.columnWidths = new int[] {800};
	        JPanel centerPanel = new JPanel(gbl_centerPanel);
	        GridBagConstraints gbc_centerPanel = new GridBagConstraints();
	        gbc_centerPanel.gridy = 1;
	        gbc_centerPanel.gridx = 0;
	        gbc_centerPanel.insets = new Insets(5, 5, 5, 0);
	        gbc_centerPanel.fill = GridBagConstraints.BOTH;
	        innerPanel.add(centerPanel, gbc_centerPanel);
		
			// 1. 첫 번째 라벨
	        titleLabel1 = new JLabel("2학기 강의 목록");
	        titleLabel1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
	        GridBagConstraints gbc_titleLabel1 = new GridBagConstraints();
	        gbc_titleLabel1.anchor = GridBagConstraints.WEST;
	        gbc_titleLabel1.gridx = 0;
	        gbc_titleLabel1.gridy = 0;
	        gbc_titleLabel1.weighty = 0;
	        centerPanel.add(titleLabel1, gbc_titleLabel1);

	        // 2. 첫 번째 테이블
	        String[] columnNames = {"강의번호", "강의명", "교수명", "학과", "학점", "요일", "강의시간"};
	        table1 = new JTable(new Object[10][7], columnNames);
	        table1.setGridColor(new Color(192,192,192));
	        table1.setShowVerticalLines(false);
	        table1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        GridBagConstraints gbc_table1 = new GridBagConstraints();
	        gbc_table1.anchor = GridBagConstraints.NORTH;
	        gbc_table1.weighty = 0.2;
	        gbc_table1.fill = GridBagConstraints.BOTH;
	        gbc_table1.gridx = 0;
	        JScrollPane scrollPane1 = new JScrollPane(table1);
	        gbc_table1.gridy = 1;
	        centerPanel.add(scrollPane1, gbc_table1);

	        // 3. 필터 패널
	        JPanel filterPanel = new JPanel();
	        GridBagConstraints gbc_filterPanel = new GridBagConstraints();
	        gbc_filterPanel.anchor = GridBagConstraints.EAST;
	        gbc_filterPanel.gridx = 0;
	        ButtonGroup group = new ButtonGroup();
	        	        	        GridBagLayout gbl_filterPanel = new GridBagLayout();
	        	        	        gbl_filterPanel.columnWidths = new int[] {80, 70, 70, 120, 250, 100, 120};
	        	        	        gbl_filterPanel.rowHeights = new int[] {0};
	        	        	        gbl_filterPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	        	        	        gbl_filterPanel.rowWeights = new double[]{0.0};
	        	        	        filterPanel.setLayout(gbl_filterPanel);
	        	        	        	        
	        	        	        	        JRadioButton radio1 = new JRadioButton(" 1학기");
	        	        	        	        radio1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        	        	        	        group.add(radio1);
	        	        	        	        GridBagConstraints gbc_radio1 = new GridBagConstraints();
	        	        	        	        gbc_radio1.insets = new Insets(0, 0, 0, 5);
	        	        	        	        gbc_radio1.gridx = 1;
	        	        	        	        gbc_radio1.gridy = 0;
	        	        	        	        filterPanel.add(radio1, gbc_radio1);
	        	        	        	        JRadioButton radio2 = new JRadioButton(" 2학기");
	        	        	        	        radio2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        	        	        	        group.add(radio2);
	        	        	        	        GridBagConstraints gbc_radio2 = new GridBagConstraints();
	        	        	        	        gbc_radio2.insets = new Insets(0, 0, 0, 5);
	        	        	        	        gbc_radio2.gridx = 2;
	        	        	        	        gbc_radio2.gridy = 0;
	        	        	        	        filterPanel.add(radio2, gbc_radio2);
	        	        	        
	        	        	        	        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
	        	        	        	        comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        	        	        	        GridBagConstraints gbc_comboBox = new GridBagConstraints();
	        	        	        	        gbc_comboBox.insets = new Insets(0, 0, 1, 5);
	        	        	        	        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
	        	        	        	        gbc_comboBox.gridx = 3;
	        	        	        	        gbc_comboBox.gridy = 0;
	        	        	        	        filterPanel.add(comboBox, gbc_comboBox);
	        	        	        JTextField searchField = new JTextField(10);
	        	        	        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        	        	        GridBagConstraints gbc_searchField = new GridBagConstraints();
	        	        	        gbc_searchField.insets = new Insets(0, 0, 0, 5);
	        	        	        gbc_searchField.anchor = GridBagConstraints.SOUTH;
	        	        	        gbc_searchField.fill = GridBagConstraints.BOTH;
	        	        	        gbc_searchField.gridx = 4;
	        	        	        gbc_searchField.gridy = 0;
	        	        	        filterPanel.add(searchField, gbc_searchField);
	        	        	        JButton btnConditionSearchButton = new JButton("조건으로 조회");
	        	        	        btnConditionSearchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        	        	        GridBagConstraints gbc_btnConditionSearchButton = new GridBagConstraints();
	        	        	        gbc_btnConditionSearchButton.fill = GridBagConstraints.HORIZONTAL;
	        	        	        gbc_btnConditionSearchButton.insets = new Insets(0, 0, 1, 5);
	        	        	        gbc_btnConditionSearchButton.gridx = 5;
	        	        	        gbc_btnConditionSearchButton.gridy = 0;
	        	        	        filterPanel.add(btnConditionSearchButton, gbc_btnConditionSearchButton);
	        	        	        JButton btnAllSearchButton = new JButton("전체 조회");
	        	        	        btnAllSearchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        	        	        GridBagConstraints gbc_btnAllSearchButton = new GridBagConstraints();
	        	        	        gbc_btnAllSearchButton.insets = new Insets(0, 0, 1, 0);
	        	        	        gbc_btnAllSearchButton.fill = GridBagConstraints.HORIZONTAL;
	        	        	        gbc_btnAllSearchButton.gridx = 6;
	        	        	        gbc_btnAllSearchButton.gridy = 0;
	        	        	        filterPanel.add(btnAllSearchButton, gbc_btnAllSearchButton);

	        gbc_filterPanel.gridy = 2;
	        gbc_filterPanel.weighty = 0;
	        centerPanel.add(filterPanel, gbc_filterPanel);

	        // 4. 두 번째 라벨
	        titleLabel2 = new JLabel("2학기 수강 목록");
	        GridBagConstraints gbc_titleLabel2 = new GridBagConstraints();
	        gbc_titleLabel2.anchor = GridBagConstraints.WEST;
	        gbc_titleLabel2.gridx = 0;
	        titleLabel2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
	        gbc_titleLabel2.gridy = 3;
	        centerPanel.add(titleLabel2, gbc_titleLabel2);

	        // 5. 두 번째 테이블
	        table2 = new JTable(new Object[10][7], columnNames);
	        table2.setGridColor(new Color(192,192,192));
	        table2.setShowVerticalLines(false);
	        table2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
	        GridBagConstraints gbc_table2 = new GridBagConstraints();
	        gbc_table2.anchor = GridBagConstraints.NORTH;
	        gbc_table2.insets = new Insets(0, 0, 30, 0);
	        gbc_table2.weighty = 0.1;
	        gbc_table2.fill = GridBagConstraints.BOTH;
	        gbc_table2.gridx = 0;
	        JScrollPane scrollPane2 = new JScrollPane(table2);
	        gbc_table2.gridy = 4;
	        centerPanel.add(scrollPane2, gbc_table2);
	        
	        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent event) {
	                if (!event.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
	                    int selectedRow = table1.getSelectedRow();
	                    String courseName = (String) table1.getValueAt(selectedRow, 1);
	                    showSyllabusDialog(frame, courseName);
	                }
	            }
	        });
	        

		StudentFrame.setBackgroundDisableForAllComponents(innerPanel);
		return innerPanel;
	}
	
    // 강의계획서 팝업 창
    private void showSyllabusDialog(JFrame parent, String courseName) {
        String syllabus = "강의계획서";

        JDialog dialog = new JDialog(parent, "강의계획서 - " + courseName, true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea(syllabus);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton closeButton = new JButton("닫기");
        JButton saveButton = new JButton("닫기");
        closeButton.addActionListener(e -> dialog.dispose());
//        saveButton.addActionListener(e -> courseName);

        JPanel panel = new JPanel();
        panel.add(closeButton);

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
        dialog.getContentPane().add(panel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

}
