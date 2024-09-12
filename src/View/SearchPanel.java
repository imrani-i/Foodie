package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.RestaurantDAO;
import dto.RestaurantDTO;
import service.SearchService;

public class SearchPanel extends JPanel {

	SearchService searchService = new SearchService();
	String selectedCategory = "";
	String selectedStandard = "";
	
	private String userId;

    public SearchPanel(String userId) {
        this.userId = userId;
    }
    

	public void setUserId(String userId) {
		this.userId = userId;
	}


	private ActionListener setBackButtonListener;
   
    

	public SearchPanel() {
		
		
		setLayout(new BorderLayout()); // 패널에 BorderLayout 설정

		// 상단에 콤보 박스와 라디오 버튼을 가로로 배치할 패널 생성
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextArea textArea = new JTextArea();
		JTable restaurantTable = new JTable();
		

		// 콤보 박스 생성 및 항목 추가
		String[] items = { "전체", "한식", "일식", "중식", "양식", "아시아", "분식", "패스트푸드", "기타" };
		JComboBox<String> comboBox = new JComboBox<>(items);

		// 콤보 박스에 아이템이 선택되었을 때 발생하는 이벤트 처리
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedCategory = (String) comboBox.getSelectedItem();
				
			}
		});

		JButton backButton = new JButton("뒤로가기");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (setBackButtonListener != null) {
					setBackButtonListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "searchButtonClicked"));
                }
			}
		});
		
		// 라디오 버튼 생성
		JRadioButton radioButton1 = new JRadioButton("평점 높은 순");
		JRadioButton radioButton2 = new JRadioButton("많이 등록된 순");

		// 라디오 버튼 그룹 생성
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton1);
		buttonGroup.add(radioButton2);

		// 라디오 버튼에 이벤트 처리를 위한 리스너 추가
		ActionListener radioListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton selectedButton = (AbstractButton) e.getSource();
				selectedStandard = selectedButton.getText().equals("평점 높은 순") ? "avg_grade" : "post_count";}
		};
		radioButton1.addActionListener(radioListener);
		radioButton2.addActionListener(radioListener);

		// 조회하기 버튼 생성
		JButton searchButton = new JButton("조회하기");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel restaurantModel = new DefaultTableModel();
				restaurantModel.addColumn("가게명");
				restaurantModel.addColumn("평점");
				restaurantModel.addColumn("카테고리");
				restaurantModel.addColumn("등록횟수");
				if (selectedCategory.equals("전체")) {
					restaurantModel = selectByAll(restaurantModel);
				} else {
					restaurantModel = selectByCategory(restaurantModel);
				}

				restaurantTable.setModel(restaurantModel);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setPreferredSize(new Dimension(150, 50));
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

				for (int i = 0; i < restaurantTable.getColumnCount(); i++) {
					restaurantTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
				}

			}

		});
		
		

		// 패널에 콤보 박스, 라디오 버튼, 조회하기 버튼 추가
		
		topPanel.add(comboBox);
		topPanel.add(radioButton1);
		topPanel.add(radioButton2);
		topPanel.add(searchButton);
		topPanel.add(backButton);
		// 패널에 상단 패널 추가
		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(restaurantTable), BorderLayout.CENTER);
		setVisible(true);

	}

	public DefaultTableModel selectByAll(DefaultTableModel restaurantModel) {

		for (RestaurantDTO restaurant : searchService.selectByAll(selectedStandard)) {
			restaurantModel.addRow(new Object[] { restaurant.getRestaurantName(), restaurant.getAvgGrade(),
					restaurant.getCategory(), restaurant.getPost_count() });
		}
		return restaurantModel;
	}

	public DefaultTableModel selectByCategory(DefaultTableModel restaurantModel) {

		for (RestaurantDTO restaurant : searchService.selectByCategory(selectedCategory, selectedStandard)) {
			restaurantModel.addRow(new Object[] { restaurant.getRestaurantName(), restaurant.getAvgGrade(),
					restaurant.getCategory(), restaurant.getPost_count() });
		}

		return restaurantModel;

	}
	
	public void setBackButtonListener(ActionListener listener) {
		this.setBackButtonListener = listener;
	}
	
}