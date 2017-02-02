package main;

import javax.swing.*;

public class ClientGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private LoginPanel loginPanel;
	private RegisterPanel registerPanel;
	private Client client;
	
	public GamePanel getGamePanel(){
		return gamePanel;
	}
	
	public LoginPanel getLoginPanel(){
		return loginPanel;
	}
	
	public RegisterPanel getRegisterPanel(){
		return registerPanel;
	}
	
	public ClientGUI(Client c) {
		this.client = c;
		gamePanel = new GamePanel(client);
		loginPanel = new LoginPanel(client);
		registerPanel = new RegisterPanel(client);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setContentPane(loginPanel);
		setMinimumSize(loginPanel.getPreferredSize());
	}
	
	public void setMasterPanel(String s){
		switch (s){
			case "game":
				setContentPane(gamePanel);
				setTitle(client.getMyName());
				break;
			case "login": setContentPane(loginPanel); break;
			case "register": setContentPane(registerPanel); break;
		}
		validate();
	}
}
