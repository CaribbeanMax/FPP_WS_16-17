package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtMoney;
	private Client client;
	private JPanel panelPlayers;

	private CardCanvas[] cCards= new CardCanvas[5];
	private JPanel[] cCardsPanel = new JPanel[5];
	private JLabel lblValueCurrent;
	private JLabel lblValueDealer;
	private JLabel lblValueSmall;
	private JLabel lblValueBig;
	private JLabel lblValuePot;
	
	private JLabel[] lblPName;
	private JLabel[] lblPBet;
	private JLabel[] lblPMoney;
	private CardCanvas[][] pCards =new CardCanvas[2][];
	private JPanel[][] pCardsPanel;
	
	public GamePanel(Client c) {
		this.client = c;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panelTable = new JPanel();
		scrollPane.setViewportView(panelTable);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		panelPlayers = new JPanel();
		panelTable.add(panelPlayers, BorderLayout.NORTH);
		panelPlayers.setLayout(new GridLayout(0, 5, 0, 0));
		
		// table head start
		JLabel lblP = new JLabel("Player");
		panelPlayers.add(lblP);

		JLabel lblCards = new JLabel("Handcards");
		panelPlayers.add(lblCards);
		JLabel lblDummy = new JLabel("");
		panelPlayers.add(lblDummy);
		
		JLabel lblBet = new JLabel("Bet");
		panelPlayers.add(lblBet);
		
		JLabel lblMoney = new JLabel("Money");
		panelPlayers.add(lblMoney);
		// table head end
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setMaximum(0);
		
		setLayout(new BorderLayout(0, 0));
		add(scrollPane);
		
		JPanel panel_community = new JPanel();
		add(panel_community, BorderLayout.SOUTH);
		panel_community.setLayout(new BoxLayout(panel_community, BoxLayout.Y_AXIS));
		
		JPanel panel_communitycards = new JPanel();
		panel_community.add(panel_communitycards);
		panel_communitycards.setLayout(new BorderLayout(0, 0));

		JPanel panel_communityrow = new JPanel();
		panel_communitycards.add(panel_communityrow);
		
		for (int i=0; i<5; i++){
			cCardsPanel[i] = new JPanel();
			cCards[i] = new CardCanvas(client.getTablestatus().communityCards[i]);
			cCardsPanel[i].setMinimumSize(cCards[i].getMinimumSize());
			cCardsPanel[i].add(cCards[i]);
			panel_communityrow.add(cCardsPanel[i]);
		}
		
		JLabel lblCommunityCards = new JLabel("Community Cards");
		lblCommunityCards.setHorizontalAlignment(SwingConstants.CENTER);
		panel_communitycards.add(lblCommunityCards, BorderLayout.SOUTH);
		
		JPanel panel_overview = new JPanel();
		panel_community.add(panel_overview);
		panel_overview.setLayout(new GridLayout(0, 5, 0, 0));
		
		//labels above values start
		JLabel lblCurrent = new JLabel("Current");
		panel_overview.add(lblCurrent);
		
		JLabel lblDealer = new JLabel("Dealer");
		panel_overview.add(lblDealer);
		
		
		JLabel lblSmall = new JLabel("Small Blind");
		panel_overview.add(lblSmall);
		
		JLabel lblBig = new JLabel("Big Blind");
		panel_overview.add(lblBig);
		
		JLabel lblPot = new JLabel("Pot");
		panel_overview.add(lblPot);
		//labels above values end
		
		lblValueCurrent = new JLabel(client.getTablestatus().current);
		panel_overview.add(lblValueCurrent);
		
		lblValueDealer = new JLabel(client.getTablestatus().dealer);
		panel_overview.add(lblValueDealer);
		
		lblValueSmall = new JLabel(((Integer)client.getTablestatus().smallBlind).toString());
		panel_overview.add(lblValueSmall);
		
		lblValueBig = new JLabel(((Integer)(client.getTablestatus().smallBlind*2)).toString());
		panel_overview.add(lblValueBig);
		
		lblValuePot = new JLabel(((Integer)client.getTablestatus().pot).toString());
		panel_overview.add(lblValuePot);
		
		JPanel panel_choice = new JPanel();
		panel_community.add(panel_choice);
		
		FlowLayout fl_panel_choice = new FlowLayout(FlowLayout.CENTER, 5, 0);
		panel_choice.setLayout(fl_panel_choice);
		
		txtMoney = new JTextField();
		txtMoney.setMaximumSize(txtMoney.getPreferredSize() );
		txtMoney.setText("0");
		panel_choice.add(txtMoney);
		txtMoney.setColumns(10);
		
		JButton btnRaise = new JButton("Bet/Raise");
		panel_choice.add(btnRaise);
		btnRaise.addActionListener(client);
		btnRaise.setActionCommand("bet/raise");
		
		JButton btnCall = new JButton("Check/Call");
		panel_choice.add(btnCall);
		btnCall.addActionListener(client);
		btnCall.setActionCommand("check/call");
		
		JButton btnFold = new JButton("Fold");
		panel_choice.add(btnFold);
		btnFold.addActionListener(client);
		btnFold.setActionCommand("fold");

	}

	public void addPlayersToTable(){
		lblPName = new JLabel[client.getTablestatus().playerNames.length];
		lblPBet = new JLabel[client.getTablestatus().playerNames.length];
		lblPMoney = new JLabel[client.getTablestatus().playerNames.length];
		pCardsPanel = new JPanel[2][client.getTablestatus().playerNames.length];
		pCards = new CardCanvas[2][client.getTablestatus().playerNames.length];
		for (int i=1; i<client.getTablestatus().playerNames.length; i++){
			lblPName[i] = new JLabel(client.getTablestatus().playerNames[i]);
			panelPlayers.add(lblPName[i]);
			for (int j=0; j<2; j++){
				pCardsPanel[j][i] = new JPanel();
				pCards[j][i] = new CardCanvas(client.getTablestatus().playerCards[j][i]);
				pCardsPanel[j][i].setMinimumSize(pCards[j][i].getMinimumSize());
				panelPlayers.add(pCardsPanel[j][i]);
				pCardsPanel[j][i].add(pCards[j][i]);
			}
			lblPBet[i] = new JLabel(((Integer)(client.getTablestatus().playerBet[i])).toString());
			panelPlayers.add(lblPBet[i]);
			lblPMoney[i] = new JLabel(((Integer)(client.getTablestatus().playerMoney[i])).toString());
			panelPlayers.add(lblPMoney[i]);
		}
		validate();
	}

	public int getInput(){
		return Integer.parseInt(txtMoney.getText());
	}

	public JLabel getLabel(String s){
		switch (s){
			case "current": return lblValueCurrent;
			case "dealer": return lblValueDealer;
			case "small": return lblValueSmall;
			case "big": return lblValueBig;
			case "pot": return lblValuePot;
			default : return null;
		}
	}

	public JLabel getLabel(String s,int i){
		switch (s){
			case "name": return lblPName[i];
			case "bet": return lblPBet[i];
			case "money": return lblPMoney[i];
			default : return null;
		}
	}
	
	public JPanel getCardPanel(String s,int i){
		switch (s){
			case "cCardP": return cCardsPanel[i];
			case "pCardP1": return pCardsPanel[0][i];
			case "pCardP2": return pCardsPanel[1][i];
			default : return null;
		}
	}
}