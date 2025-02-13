package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import kims.semi1.controller.StudentController;

public class StudentGradePanel {
	JFrame frame;
	StudentController studentController;
	public JLabel titleLabel1;
	public JTable table1;

	private Object[][] courseInfos;
	private Object[][] enrollmentInfos;
	TableRowSorter<DefaultTableModel> sorter;

	private JTextField searchField;
	private JComboBox<String> comboBox;
	private JRadioButton radio2;
	private JRadioButton radio1;
	private JButton btnConditionSearchButton;
	private String[] columnNames;

	public StudentGradePanel(JFrame frame, StudentController studentController) {
		this.studentController = studentController;
		this.frame = frame;
	}

	public Panel createGradePanel() {
		Panel innerPanel = new Panel();
		GridBagLayout gbl_innerPanel = new GridBagLayout();
		gbl_innerPanel.columnWidths = new int[] { 850 };
		gbl_innerPanel.rowHeights = new int[] { 120, 640, 50 };
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

		JLabel headTitleLabel = new JLabel("성적 확인");
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
		gbl_centerPanel.rowHeights = new int[] { 40, 160, 40, 40, 160 };
		gbl_centerPanel.columnWidths = new int[] { 800 };
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
		{
			// 2. 첫 번째 테이블
			columnNames = new String[] { "강의번호", "강의명", "교수명", "학점", "평가 학점", "강의 평가" };
			table1 = new JTable(new Object[1][8], columnNames);
			table1.setGridColor(new Color(192, 192, 192));
			table1.setShowVerticalLines(false);
			table1.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			table1.setRowHeight(28);
			table1.setIntercellSpacing(new Dimension(1, 1));
			table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			GridBagConstraints gbc_table1 = new GridBagConstraints();
			gbc_table1.anchor = GridBagConstraints.NORTH;
			gbc_table1.weighty = 0.2;
			gbc_table1.fill = GridBagConstraints.BOTH;
			gbc_table1.gridx = 0;
			JScrollPane scrollPane1 = new JScrollPane(table1);
			gbc_table1.gridy = 1;
			centerPanel.add(scrollPane1, gbc_table1);

			JTableHeader header = table1.getTableHeader();
			header.setDefaultRenderer(new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
							column);
					comp.setBackground(new Color(245, 245, 245)); // 배경색 설정
					comp.setForeground(Color.BLACK); // 글자색 설정
					((JComponent) comp).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // 테두리 설정
					return comp;
				}
			});
		}

		// 3. 필터 패널
		JPanel filterPanel = new JPanel();
		GridBagConstraints gbc_filterPanel = new GridBagConstraints();
		gbc_filterPanel.anchor = GridBagConstraints.EAST;
		gbc_filterPanel.gridx = 0;
		ButtonGroup group = new ButtonGroup();
		GridBagLayout gbl_filterPanel = new GridBagLayout();
		gbl_filterPanel.columnWidths = new int[] { 200, 70, 70, 70, 70, 70 };
		gbl_filterPanel.rowHeights = new int[] { 0 };
		gbl_filterPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_filterPanel.rowWeights = new double[] { 0.0 };
		filterPanel.setLayout(gbl_filterPanel);

		radio1 = new JRadioButton(" 1학기");
		radio1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		radio1.setSelected(true);
		group.add(radio1);
		GridBagConstraints gbc_radio1 = new GridBagConstraints();
		gbc_radio1.insets = new Insets(0, 0, 0, 5);
		gbc_radio1.gridx = 4;
		gbc_radio1.gridy = 0;
		filterPanel.add(radio1, gbc_radio1);

		radio2 = new JRadioButton(" 2학기");
		radio2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		group.add(radio2);
		GridBagConstraints gbc_radio2 = new GridBagConstraints();
		gbc_radio2.insets = new Insets(0, 0, 0, 5);
		gbc_radio2.gridx = 5;
		gbc_radio2.gridy = 0;
		filterPanel.add(radio2, gbc_radio2);

		gbc_filterPanel.gridy = 2;
		gbc_filterPanel.weighty = 0;
		centerPanel.add(filterPanel, gbc_filterPanel);

		table1.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
				int selectedRow = table1.getSelectedRow();
				int courseId = (Integer) table1.getValueAt(selectedRow, 0);
				showCourseEvaluationDialog(frame, courseId);
				table1.clearSelection();
			}

		});

		radio1.addActionListener(e -> setTablesByRadioButton());
		radio2.addActionListener(e -> setTablesByRadioButton());

		setTablesByRadioButton();
		StudentFrame.setBackgroundDisableForAllComponents(innerPanel);
		return innerPanel;
	}

	public void showCourseEvaluationDialog(JFrame parentFrame, int courseId) {
		JDialog dialog = new JDialog(parentFrame, "강의 평가 입력", false); // 모달 다이얼로그
		dialog.setLayout(new BorderLayout());
		dialog.setBackground(new Color(245, 245, 245));

		JLabel label = new JLabel("강의 평가 입력", JLabel.CENTER);
		label.setFont(new Font("Malgun Gothic", Font.PLAIN, 17));
		label.setAlignmentY(10);

		// 텍스트 필드 (100글자 제한)
		JTextField textField = new JTextField();
		textField.setColumns(30); // 글자 수 제한을 위해 컬럼 수 설정 (기본 글자수 제한은 100자)
		textField.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(245, 245, 245));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton registerButton = new JButton("등록");
		registerButton.setBackground(null);
		JButton closeButton = new JButton("닫기");
		closeButton.setBackground(null);

		// 등록 버튼 리스너
		registerButton.addActionListener(e -> {
			if (studentController.updateStudentReview(courseId, textField.getText())) {
				showMessageDialog(parentFrame, "강의 평가 등록이 완료되었습니다.");
			} else {
				showMessageDialog(parentFrame, "강의 평가 등록이 실패했습니다.");
			}
			dialog.dispose();
			setTablesByRadioButton();
		});

		closeButton.addActionListener(e -> dialog.dispose());

		buttonPanel.add(registerButton);
		buttonPanel.add(closeButton);

		dialog.add(label, BorderLayout.NORTH);
		dialog.add(textField, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);

		dialog.setSize(360, 160); 
		dialog.setLocationRelativeTo(parentFrame);
		dialog.setVisible(true);
	}

	public void setEnrollmentInfoTable(Object[][] enrollmentInfos, String[] columnNames) {
		DefaultTableModel tableModel = new DefaultTableModel(enrollmentInfos, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 모든 셀 편집 불가
			}
		};
		table1.setModel(tableModel);
		sorter = new TableRowSorter<>(tableModel);
		table1.setRowSorter(sorter);
	}

	public void setTablesByRadioButton() {
		String semester = radio1.isSelected() ? "1" : "2";
		titleLabel1.setText(semester + "학기 성적 목록");
		enrollmentInfos = studentController.searchGrades(semester);
		setEnrollmentInfoTable(enrollmentInfos, columnNames);
	}

	private void showMessageDialog(JFrame parent, String message) {

		JDialog dialog = new JDialog();
		dialog.setSize(280, 120);
		dialog.setBackground(new Color(245, 245, 245));
		dialog.setLocationRelativeTo(parent);
		dialog.getContentPane().setLayout(new BorderLayout());

		JLabel messageLabel = new JLabel();
		messageLabel.setText(message);
		messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(245, 245, 245));
		panel1.add(messageLabel);
		dialog.getContentPane().add(panel1, BorderLayout.CENTER);

		JButton closeButton = new JButton("닫기");
		closeButton.setBackground(null);

		closeButton.addActionListener(e -> dialog.dispose());

		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(245, 245, 245));
		panel2.add(closeButton);

		dialog.getContentPane().add(panel2, BorderLayout.SOUTH);

		dialog.setVisible(true);
	}

}
