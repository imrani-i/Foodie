package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.PostDAO;
import dto.PostDTO;
import dto.RestaurantDTO;
import service.PostService;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class PostPanel extends JPanel {

	PostService postService = new PostService();
	private ActionListener setBackButtonListener;
	private PostDAO postDAO = new PostDAO();

	private String userId;

	public PostPanel(String userId) {
		this.userId = userId;

	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
	
	
	DefaultTableModel postModel = new DefaultTableModel();

	

	public PostPanel() {
		
		

		setLayout(new BorderLayout()); // 패널에 BorderLayout 설정
		JTable postTable = new JTable();

		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// 카테고리 콤보 박스 생성 및 항목 추가
		String[] categories = { "전체", "한식", "일식", "중식", "양식", "아시아", "분식", "패스트푸드", "기타" };
		JComboBox<String> comboBox = new JComboBox<>(categories);
		topPanel.add(comboBox); 

		// 가게 이름 입력 
		JTextField storeNameField = new JTextField(20); 
		topPanel.add(new JLabel("가게 이름:"));
		topPanel.add(storeNameField); 

		// 한줄평 입력 
		JTextField contentField = new JTextField(20);
		topPanel.add(new JLabel("한줄평:")); 
		topPanel.add(contentField); 

		// 추천메뉴 입력 
		JTextField recommendMenuField = new JTextField(20); 
		topPanel.add(new JLabel("추천메뉴:"));
		topPanel.add(recommendMenuField);

		// 평점 콤보 박스 생성
		String[] grade = { "평점", "★★★★★", "★★★★", "★★★", "★★", "★" };
		JComboBox<String> secondComboBox = new JComboBox<>(grade);
		topPanel.add(secondComboBox); 


		postModel.addColumn("가게명");
		postModel.addColumn("카테고리");
		postModel.addColumn("평점");
		postModel.addColumn("추천메뉴");
		postModel.addColumn("한줄평");
		postModel.addColumn("작성 시간");
		postModel = selectAll(postModel);
		
		// 작성하기 버튼 생성
		JButton postButton = new JButton("작성하기");
		postButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		
				
				String selectedCategory = (String) comboBox.getSelectedItem();
				String restaurant_pname = storeNameField.getText();
				String content = contentField.getText();
				String rcMenu = recommendMenuField.getText();
				String gradeStr = (String) secondComboBox.getSelectedItem();
				int grade = gradeToInt(gradeStr);
				
				LocalDateTime now = LocalDateTime.now();
				PostDTO postDTO = postDAO.makePost(selectedCategory, restaurant_pname, userId, content, rcMenu, grade,now);
				
				String postTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				postModel.addRow(
						new Object[] { restaurant_pname, selectedCategory, gradeStr, rcMenu, content, postTime });
				postDAO.postInsert(postDTO);

			}
		});

		postTable.setModel(postModel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		postTable.setModel(postModel);
		for (int i = 0; i < postTable.getColumnCount(); i++) {
			postTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		
		// 뒤로가기 버튼 생성
		JButton backButton = new JButton("뒤로가기");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (setBackButtonListener != null) {
					setBackButtonListener.actionPerformed(
							new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "searchButtonClicked"));
				}
			}
		});

		topPanel.add(postButton); 
		topPanel.add(backButton);
		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(postTable), BorderLayout.CENTER);

	}

	public DefaultTableModel selectAll(DefaultTableModel postModel) {

		for (PostDTO post : postService.selectAll()) {
			String postTime = post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			postModel.addRow(new Object[] { post.getRestaurantPname(), post.getPcategory(), gradeToStr(post.getGrade()),
					post.getRecommendMenu(), post.getContent(), postTime });
		}

		return postModel;

	}

	public void setBackButtonListener(ActionListener listener) {
		this.setBackButtonListener = listener;
	}

	public String gradeToStr(int grade) {
		String gradeStr = "";
		switch (grade) {
		case 5 -> gradeStr = "★★★★★";
		case 4 -> gradeStr = "★★★★";
		case 3 -> gradeStr = "★★★";
		case 2 -> gradeStr = "★★";
		case 1 -> gradeStr = "★";

		}
		return gradeStr;

	}

	public int gradeToInt(String gradeStr) {
		int grade = 0;
		switch (gradeStr) {
		case "★★★★★" -> grade = 5;
		case "★★★★" -> grade = 4;
		case "★★★" -> grade = 3;
		case "★★" -> grade = 2;
		case "★" -> grade = 1;

		}
		return grade;
	}
	
	

}
