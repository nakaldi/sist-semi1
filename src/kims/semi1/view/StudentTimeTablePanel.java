package kims.semi1.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import kims.semi1.controller.StudentController;

public class StudentTimeTablePanel {
	JFrame frame;
	StudentController studentController;
	public JLabel titleLabel1;
	public JTable table1;
	public JLabel titleLabel2;
	public JTable table2;
	private Object[][] enrollmentInfos;
	TableRowSorter<DefaultTableModel> sorter;
	TableRowSorter<DefaultTableModel> sorter2;
	private JRadioButton radio2;
	private JRadioButton radio1;
	private String[] columnNames;
	private final Map<Object, Color> colorMap = new HashMap<>();

	public StudentTimeTablePanel(JFrame frame, StudentController studentController) {
		this.studentController = studentController;
		this.frame = frame;
	}

	public Panel createTimeTablePanel() {
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

		JLabel headTitleLabel = new JLabel("시간표");
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
		gbl_centerPanel.rowHeights = new int[] { 40, 200, 40, 30, 80 };
		gbl_centerPanel.columnWidths = new int[] { 700 };
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
		columnNames = new String[] { "시간", "월", "화", "수", "목", "금" };
		table1 = new JTable(new Object[9][6], columnNames);
		table1.setRowSelectionAllowed(false);
		table1.setEnabled(false);
		table1.setGridColor(new Color(192, 192, 192));
		table1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table1.setRowHeight(45);

		GridBagConstraints gbc_table1 = new GridBagConstraints();
		gbc_table1.fill = GridBagConstraints.BOTH;
		gbc_table1.anchor = GridBagConstraints.NORTH;
		gbc_table1.gridx = 0;
		JPanel timeTablePanel = new JPanel();

		GridBagLayout gbl_timeTablePanel = new GridBagLayout();
		gbl_timeTablePanel.columnWidths = new int[] { 700 };
		gbl_timeTablePanel.rowHeights = new int[] { 450 };
		gbl_timeTablePanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_timeTablePanel.rowWeights = new double[] { 0.0 };
		timeTablePanel.setLayout(gbl_timeTablePanel);
		gbc_table1.gridy = 1;
		centerPanel.add(timeTablePanel, gbc_table1);

		GridBagConstraints gbc_table1_1 = new GridBagConstraints();
		gbc_table1_1.fill = GridBagConstraints.BOTH;
		gbc_table1_1.anchor = GridBagConstraints.NORTH;
		gbc_table1_1.gridx = 0;
		gbc_table1_1.gridy = 0;
		timeTablePanel.add(table1, gbc_table1_1);

		JTableHeader header = table1.getTableHeader();
		header.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				comp.setBackground(new Color(245, 245, 245)); // 배경색 설정
				comp.setForeground(Color.BLACK); // 글자색 설정
				((JComponent) comp).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // 테두리 설정
				return comp;
			}
		});

		// 3. 필터 패널
		JPanel filterPanel = new JPanel();
		GridBagConstraints gbc_filterPanel = new GridBagConstraints();
		gbc_filterPanel.anchor = GridBagConstraints.EAST;
		gbc_filterPanel.gridx = 0;
		ButtonGroup group = new ButtonGroup();
		GridBagLayout gbl_filterPanel = new GridBagLayout();
		gbl_filterPanel.columnWidths = new int[] { 200, 70, 70, 70, 70, 50 };
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
		gbc_radio1.gridx = 3;
		gbc_radio1.gridy = 0;
		filterPanel.add(radio1, gbc_radio1);

		radio2 = new JRadioButton(" 2학기");
		radio2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		group.add(radio2);
		GridBagConstraints gbc_radio2 = new GridBagConstraints();
		gbc_radio2.insets = new Insets(0, 0, 0, 5);
		gbc_radio2.gridx = 4;
		gbc_radio2.gridy = 0;
		filterPanel.add(radio2, gbc_radio2);

		gbc_filterPanel.gridy = 2;
		gbc_filterPanel.weighty = 0;
		centerPanel.add(filterPanel, gbc_filterPanel);

		radio1.addActionListener(e -> setTablesByRadioButton());
		radio2.addActionListener(e -> setTablesByRadioButton());

		setTablesByRadioButton();
		setTimeTablefoTable(enrollmentInfos, columnNames);
		StudentFrame.setBackgroundDisableForAllComponents(innerPanel);
		return innerPanel;
	}

	public void setTimeTablefoTable(Object[][] enrollmentInfos, String[] columnNames) {
		// 모든셀 편집불가
		DefaultTableModel tableModel = new DefaultTableModel(enrollmentInfos, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table1.setModel(tableModel);

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				// 요소가 있는지 확인하여 색상 설정
				if ((value != null) && row != 0 && column != 0) {
					comp.setBackground(getColorForValue(value)); // 요소가 있는 셀에 대해 색상 설정
				} else {
					comp.setBackground(null); // 요소가 없는 셀에 대해 기본 배경색 설정
				}

				if (isSelected) {
					comp.setBackground(table.getSelectionBackground());
				}

				return comp;
			}
		};
		// 모든셀 가운데 정렬
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 모든 열에 대해 커스텀 렌더러 설정
		for (int i = 0; i < table1.getColumnCount(); i++) {
			table1.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
	}

	private Color getColorForValue(Object value) {
		return colorMap.computeIfAbsent(value, k -> new Color(180 + (int) (Math.random() * 61),
				180 + (int) (Math.random() * 61), 180 + (int) (Math.random() * 61)));
	}

	public void setTablesByRadioButton() {
		String semester = radio1.isSelected() ? "1" : "2";
		titleLabel1.setText(semester + "학기 시간표");
		enrollmentInfos = studentController.makeTimeTableInfoBySemester(semester);
		setTimeTablefoTable(enrollmentInfos, columnNames);
	}

}
