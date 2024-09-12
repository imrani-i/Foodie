package View;


import javax.swing.*;

import dto.MemberDTO;
import service.MemberService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.EventListener; 

public class MenuPanel extends JPanel  {
	
	
	private ActionListener postButtonListener;
    private ActionListener searchButtonListener;
    private ActionListener myPageButtonListener;
    String id="";
	String pw="";
	Boolean login = false;
	
	private String userId;
	
	public String getUserId() {
        return userId;
    }
    
    
	public MenuPanel () {
		
		this.setBackground(new Color  (255,250,205));
		
		
		MemberService memberService = new MemberService();
		JLabel titleLabel = new JLabel(" ‧₊˚⊹‎Foodie(ˆ ڡ ˆ) ‧₊˚⊹");
		Font font = titleLabel.getFont();
        titleLabel.setFont(new Font(font.getName(), font.getStyle(), 30));
        titleLabel.setBackground(new Color  (255,250,205));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel mainPanel = new JPanel();
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(new Color  (255,250,205));
		
		
		
		JPanel memberPanel = new JPanel();
		memberPanel.setBackground(new Color  (255,250,205));
		memberPanel.setLayout(new BoxLayout(memberPanel, BoxLayout.X_AXIS));
		
		JPanel otherPanel = new JPanel();
		otherPanel.setBackground(new Color  (255,250,205));
		otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.X_AXIS)); 
        
        JLabel idLabel = new JLabel("아이디: ");
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(100, 30));
        JLabel passwordLabel = new JLabel("비밀번호: ");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 30));
        
        JButton registerButton = new JButton("가입하기");
       
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            	
				id= idField.getText();
				try {
					if (!memberService.memberCheck(1,id)) {
					    JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디입니다!");
					    return;
					}
					if((id.equals("")) || (pw.equals(""))) {
						JOptionPane.showMessageDialog(mainPanel, "문자를 입력하세요!");
					    return;
					}
				} catch (HeadlessException e1) {
					
					e1.printStackTrace();
				} catch (SQLException e1) {
	
					e1.printStackTrace();
				}
            	MemberDTO member = new MemberDTO(id,new String(passwordField.getPassword()));
            	try {
					memberService.memberInsert(member);
					
				} catch (SQLException e1) {
		
					e1.printStackTrace();
				}
            	
                JOptionPane.showMessageDialog(mainPanel, "가입 완료!");
            }
        });
        
        
        // 로그인 버튼 및 리스너 
        JButton loginButton = new JButton("로그인");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				id= idField.getText();
				pw = new String(passwordField.getPassword());
				try {
					if (!memberService.memberCheck(2,id)) {
					    JOptionPane.showMessageDialog(mainPanel, "존재하지 않는 아이디입니다!");
					    login = false; 
					    return;
					}
					if(!memberService.memberCheck(0,pw)) {
						JOptionPane.showMessageDialog(mainPanel, "잘못된 비밀번호입니다!");
						
					    return;
					}	
				} catch (HeadlessException e1) {
				
					e1.printStackTrace();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				login = true;
				userId = id;
                JOptionPane.showMessageDialog(mainPanel, "로그인 완료!");
            }
        });
        
        
        // 포스팅 등록 버튼 및 리스너 
        
        JButton postButton = new JButton("맛집 추천하기");
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!login) {
            		JOptionPane.showMessageDialog(mainPanel, "로그인하세요!");
            		return;
            	}
                if (postButtonListener != null) {
                	postButtonListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "postButtonClicked"));
                }
            }
        });

        // 맛집 검색 버튼 및 리스너 
        
        JButton searchButton = new JButton("맛집 검색하기");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!login) {
            		JOptionPane.showMessageDialog(mainPanel, "로그인하세요!");
            		return;
            	}
            	
                if (searchButtonListener != null) {
                	searchButtonListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "searchButtonClicked"));
                }
            }
        });
        
        // 마이페이지 검색 버튼 및 리스너 
        
        JButton myPageButton = new JButton("마이페이지");
        myPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!login) {
            		JOptionPane.showMessageDialog(mainPanel, "로그인하세요!");
            		return;
            	}
            	
                if (myPageButtonListener != null) {
                	myPageButtonListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "myPageButtonClicked"));
                }
            }
        });
 
        
        
        
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        memberPanel.add(Box.createHorizontalStrut(130));
        memberPanel.add(registerButton);
        memberPanel.add(Box.createHorizontalStrut(50));
        memberPanel.add(loginButton);
        otherPanel.add(Box.createHorizontalStrut(50));
        otherPanel.add(postButton);
        otherPanel.add(Box.createHorizontalStrut(20));
        otherPanel.add(searchButton);
        otherPanel.add(Box.createHorizontalStrut(20));
        otherPanel.add(myPageButton);
        
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        // otherPanel 내의 요소들을 중앙 정렬
        postButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel verticalPanel = new JPanel();
        verticalPanel.setPreferredSize(new Dimension(500, 300));

        verticalPanel.setBackground(new Color  (255,250,205));
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(Box.createVerticalStrut(40)); 
        verticalPanel.add(Box.createVerticalGlue());
        verticalPanel.add(titleLabel);
        verticalPanel.add(Box.createVerticalStrut(30));
        verticalPanel.add(inputPanel);
        verticalPanel.add(Box.createVerticalStrut(10)); 
        memberPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        verticalPanel.add(memberPanel);
        otherPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        verticalPanel.add(otherPanel);
        verticalPanel.add(Box.createVerticalStrut(20)); 
        verticalPanel.add(Box.createVerticalGlue());
        mainPanel.add(verticalPanel);
        
        this.add(mainPanel);
        
	}
        
    
	
	public void setPostButtonListener(ActionListener listener) {
        this.postButtonListener = listener;
    }

    public void setSearchButtonListener(ActionListener listener) {
        this.searchButtonListener = listener;
    }
	
    public void setMyPageButtonListener(ActionListener listener) {
        this.myPageButtonListener = listener;
    }

}