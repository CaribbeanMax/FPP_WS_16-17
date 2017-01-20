package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClientGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GamePanel gamePanel;
	private LoginPanel loginPanel;
	private RegisterPanel registerPanel;
	
	public GamePanel getGamePanel(){
		return gamePanel;
	}
	
	public LoginPanel getLoginPanel(){
		return loginPanel;
	}
	
	public RegisterPanel getRegisterPanel(){
		return registerPanel;
	}
	
	public ClientGUI(Client client) {
		gamePanel = new GamePanel(client);
		loginPanel = new LoginPanel(client);
		registerPanel = new RegisterPanel(client);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(loginPanel, BorderLayout.CENTER);
	}
	
	public void setMasterPanel(String s){
		contentPane.remove(contentPane.getComponent(0));
		switch (s){
			case "game": contentPane.add(gamePanel); break;
			case "login": contentPane.add(loginPanel); break;
			case "register": contentPane.add(registerPanel); break;
		}
	}
}
