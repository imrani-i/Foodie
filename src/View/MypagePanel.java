package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.PostDTO;
import service.MemberService;

public class MypagePanel extends JPanel {

	private String userId;
	int deleteNo;


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	MemberService memMemberService = new MemberService();
	DefaultTableModel postModel = new DefaultTableModel();
	private ActionListener setBackButtonListener;

	public MypagePanel() {
		
		JPanel mainPanel = new JPanel();
		
		postModel.addColumn("포스팅번호");
		postModel.addColumn("가게명");
		postModel.addColumn("카테고리");
		postModel.addColumn("평점");
		postModel.addColumn("추천메뉴");
		postModel.addColumn("한줄평");
		postModel.addColumn("작성 시간");
		
		JTable postTable = new JTable();

		JLabel deleteLabel = new JLabel("포스팅번호 : ");
        JTextField deleteField = new JTextField();
 
        JButton deleteButton = new JButton("삭제하기");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	deleteNo = Integer.parseInt(deleteField.getText());
            	if ((deletePost(deleteNo))>0) {
            		JOptionPane.showMessageDialog(mainPanel, "삭제되었습니다!");};}});
            	
	
		JButton inquiryButton = new JButton("조회하기");
		inquiryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				postModel = selectAll();
				postTable.setModel(postModel);
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

				for (int i = 0; i < postTable.getColumnCount(); i++) {
					postTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
				}
				

			}
		});

		JButton backButton = new JButton("뒤로가기");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (setBackButtonListener != null) {
					setBackButtonListener
							.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "backButtonClicked"));
				}
			}
		});

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(deleteLabel);
		topPanel.add(deleteField);
		topPanel.add(deleteButton);
		topPanel.add(backButton);
		topPanel.add(inquiryButton);
		
		add(topPanel, BorderLayout.NORTH);

		
		add(new JScrollPane(postTable), BorderLayout.CENTER);

	}

	public void setBackButtonListener(ActionListener listener) {
		this.setBackButtonListener = listener;
	}

	
	public int deletePost(int deleteNo) {
		return memMemberService.deletePost(deleteNo);
	}
	
	
	public DefaultTableModel selectAll() {
        DefaultTableModel newPostModel = new DefaultTableModel();
        newPostModel.addColumn("포스팅번호");
        newPostModel.addColumn("가게명");
        newPostModel.addColumn("카테고리");
        newPostModel.addColumn("평점");
        newPostModel.addColumn("추천메뉴");
        newPostModel.addColumn("한줄평");
        newPostModel.addColumn("작성 시간");

        for (PostDTO post : memMemberService.selectByWriter(userId)) {
            String postTime = post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            newPostModel.addRow(new Object[] {
                    post.getPostNo(),
                    post.getRestaurantPname(),
                    post.getPcategory(),
                    gradeToStr(post.getGrade()),
                    post.getRecommendMenu(),
                    post.getContent(),
                    postTime
            });
        }
        return newPostModel;
        
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

}
