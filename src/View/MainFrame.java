package View;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private JPanel cardPanel;
    private CardLayout cardLayout;
    private String userId;
    
    
   
    public MainFrame() {
        setTitle("Foodie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
            
        MenuPanel menuPanel = new MenuPanel();
        PostPanel postPanel = new PostPanel();
        SearchPanel searchPanel = new SearchPanel();
        MypagePanel mypagePanel = new MypagePanel();
        
        menuPanel.setPostButtonListener(e -> {
        	cardLayout.show(cardPanel, "postPanel");
        	userId=menuPanel.getUserId();
        	postPanel.setUserId(userId);
        	
        });
        menuPanel.setSearchButtonListener(e ->
        {
        	cardLayout.show(cardPanel, "searchPanel");
        	userId=menuPanel.getUserId();
        	searchPanel.setUserId(userId);
        	
        });
        menuPanel.setMyPageButtonListener(e ->
        {
        	cardLayout.show(cardPanel, "mypagePanel");
        	userId=menuPanel.getUserId();
        	mypagePanel.setUserId(userId);
        	
        });

        searchPanel.setBackButtonListener(e-> cardLayout.show(cardPanel, "menuPanel"));
        postPanel.setBackButtonListener(e-> cardLayout.show(cardPanel, "menuPanel"));
        mypagePanel.setBackButtonListener(e-> cardLayout.show(cardPanel, "menuPanel"));
        
        
        cardPanel.add(menuPanel, "menuPanel");
        cardPanel.add(postPanel, "postPanel");
        cardPanel.add(searchPanel, "searchPanel");
        cardPanel.add(mypagePanel, "mypagePanel");
        
        
            
        getContentPane().add(cardPanel);
            
        pack();
        setVisible(true);
        
    }

	

}