package kims.semi1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

public class StudentEnrollSpecPanel {
	JFrame frame;
	StudentController studentController;
	public JLabel titleLabel1;
	public JTable table1;

	private Object[][] enrollmentInfos;
	TableRowSorter<DefaultTableModel> sorter;

	private JTextField searchField;
	private JComboBox<String> comboBox;
	private JRadioButton radio2;
	private JRadioButton radio1;
	private JButton btnConditionSearchButton;
	private String[] columnNames;

	public StudentEnrollSpecPanel(JFrame frame, StudentController studentController) {
		this.studentController = studentController;
		this.frame = frame;
	}

	public Panel createEnrollSpecPanel() {
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

		JLabel headTitleLabel = new JLabel("수강 현황");
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
		gbl_centerPanel.columnWidths = new int[] { 820 };
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
			columnNames = new String[] { "강의번호", "강의명", "교수명", "학과", "학점", "요일", "강의시간" };
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
		gbl_filterPanel.columnWidths = new int[] { 200, 70, 70, 120, 250, 100 };
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
		gbc_radio1.gridx = 1;
		gbc_radio1.gridy = 0;
		filterPanel.add(radio1, gbc_radio1);

		radio2 = new JRadioButton(" 2학기");
		radio2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		group.add(radio2);
		GridBagConstraints gbc_radio2 = new GridBagConstraints();
		gbc_radio2.insets = new Insets(0, 0, 0, 5);
		gbc_radio2.gridx = 2;
		gbc_radio2.gridy = 0;
		filterPanel.add(radio2, gbc_radio2);

		comboBox = new JComboBox<>(new String[] { "전체 조회", "강의번호", "강의명", "교수명", "학과", "학점" });
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 1, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 0;
		filterPanel.add(comboBox, gbc_comboBox);
		searchField = new JTextField(10);
		searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.insets = new Insets(0, 0, 0, 5);
		gbc_searchField.anchor = GridBagConstraints.SOUTH;
		gbc_searchField.fill = GridBagConstraints.BOTH;
		gbc_searchField.gridx = 4;
		gbc_searchField.gridy = 0;
		filterPanel.add(searchField, gbc_searchField);
		btnConditionSearchButton = new JButton("조회");
		btnConditionSearchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		GridBagConstraints gbc_btnConditionSearchButton = new GridBagConstraints();
		gbc_btnConditionSearchButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConditionSearchButton.insets = new Insets(0, 0, 1, 5);
		gbc_btnConditionSearchButton.gridx = 5;
		gbc_btnConditionSearchButton.gridy = 0;
		filterPanel.add(btnConditionSearchButton, gbc_btnConditionSearchButton);

		gbc_filterPanel.gridy = 2;
		gbc_filterPanel.weighty = 0;
		centerPanel.add(filterPanel, gbc_filterPanel);

		table1.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
				int selectedRow = table1.getSelectedRow();
				int courseId = (Integer) table1.getValueAt(selectedRow, 0);
				showSyllabusDialog(frame, courseId);
				table1.clearSelection();
			}

		});

		radio1.addActionListener(e -> setTablesByRadioButton());
		radio2.addActionListener(e -> setTablesByRadioButton());
		btnConditionSearchButton.addActionListener(e -> sortTable1ByFilter());

		setTablesByRadioButton();
		sortTable1ByFilter();
		StudentFrame.setBackgroundDisableForAllComponents(innerPanel);
		return innerPanel;
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
		titleLabel1.setText(semester + "학기 수강 목록");
		enrollmentInfos = studentController.searchEnrollmentInfosToArray(semester);
		setEnrollmentInfoTable(enrollmentInfos, columnNames);
	}

	public void sortTable1ByFilter() {

		String condition = searchField.getText();
		int comboIndex = comboBox.getSelectedIndex() - 1;
		if (comboIndex == -1) {
			searchField.setText("");
		}

		if (condition.trim().length() == 0 || comboIndex == -1) {
			sorter.setRowFilter(null);
		} else {
			// 특정 열에서 검색
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + condition, comboIndex));
		}

	}

	// 강의계획서 팝업 창
	private void showSyllabusDialog(JFrame parent, int courseId) {
		String syllabus = studentController.makeSyllabus(courseId);

		JDialog dialog = new JDialog(parent, "강의계획서", false);
		dialog.setSize(550, 450);
		dialog.setBackground(new Color(245, 245, 245));
		dialog.setLocationRelativeTo(parent);

		JTextArea textArea = new JTextArea(syllabus);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(null);
		textArea.setBackground(null);

		JScrollPane scrollPane = new JScrollPane(textArea);
		JButton saveButton = new JButton("수강 취소");
		saveButton.setBackground(null);
		JButton closeButton = new JButton("닫기");
		closeButton.setBackground(null);
		saveButton.addActionListener(e -> {

			if (studentController.deleteEnrollment(courseId)) {
				showMessageDialog(frame, "수강 취소가 완료되었습니다.");
			} else {
				showMessageDialog(frame, "수강 취소가 실패했습니다.");
			}
			dialog.dispose();
			setTablesByRadioButton();
		});
		closeButton.addActionListener(e -> dialog.dispose());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.add(saveButton);
		panel.add(closeButton);
		panel.setBackground(null);

		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
		dialog.getContentPane().add(panel, BorderLayout.SOUTH);

		dialog.setVisible(true);
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
