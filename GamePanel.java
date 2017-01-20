package main;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtMoney;

	/**
	 * Create the panel.
	 */
	public GamePanel(Client client) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panel_player = new JPanel();
		scrollPane.setViewportView(panel_player);
		panel_player.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_player.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 5, 0, 5));
		// table head start
		JLabel lblP = new JLabel("Player");
		panel.add(lblP);
		
		JLabel lblCard = new JLabel("Handcard 1");
		panel.add(lblCard);
		
		JLabel lblCard_1 = new JLabel("Handcard 2");
		panel.add(lblCard_1);
		
		JLabel lblBetshare = new JLabel("Bet Share");
		panel.add(lblBetshare);
		
		JLabel lblMoney = new JLabel("Money");
		panel.add(lblMoney);
		// table head end
		/*
		[] CCards= new CardCanvas[5]; 
		for (int i=0; i<5; i++){
			CCards[i] = new CardCanvas(client.getTablestatus().communityCards[i]);
			panel_communityrow.add(CCards[i]);
		}
		*/
		
		JLabel lblP_1 = new JLabel("P2");
		panel.add(lblP_1);
		
		JLabel lblCard_3 = new JLabel("Card1");
		panel.add(lblCard_3);
		
		JLabel lblCard_2 = new JLabel("Card2");
		panel.add(lblCard_2);
		
		JLabel lblBetshare_1 = new JLabel("BetShare");
		panel.add(lblBetshare_1);
		
		JLabel lblMoney_1 = new JLabel("money");
		panel.add(lblMoney_1);
		
		JLabel lblP_2 = new JLabel("P3");
		panel.add(lblP_2);
		
		JLabel lblCard_4 = new JLabel("Card1");
		panel.add(lblCard_4);
		
		JLabel lblCard_5 = new JLabel("Card2");
		panel.add(lblCard_5);
		
		JLabel lblBetshare_2 = new JLabel("BetShare");
		panel.add(lblBetshare_2);
		
		JLabel lblMoney_2 = new JLabel("money");
		panel.add(lblMoney_2);
		
		JLabel label = new JLabel("P3");
		panel.add(label);
		
		JLabel label_1 = new JLabel("Card1");
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Card2");
		panel.add(label_2);
		
		JLabel label_4 = new JLabel("BetShare");
		panel.add(label_4);
		
		JLabel label_3 = new JLabel("money");
		panel.add(label_3);
		
		JLabel label_5 = new JLabel("P3");
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("Card1");
		panel.add(label_6);
		
		JLabel label_7 = new JLabel("Card2");
		panel.add(label_7);
		
		JLabel label_8 = new JLabel("BetShare");
		panel.add(label_8);
		
		JLabel label_9 = new JLabel("money");
		panel.add(label_9);
		
		JLabel label_10 = new JLabel("P3");
		panel.add(label_10);
		
		JLabel label_11 = new JLabel("Card1");
		panel.add(label_11);
		
		JLabel label_12 = new JLabel("Card2");
		panel.add(label_12);
		
		JLabel label_13 = new JLabel("BetShare");
		panel.add(label_13);
		
		JLabel label_14 = new JLabel("money");
		panel.add(label_14);
		
		JLabel label_15 = new JLabel("P3");
		panel.add(label_15);
		
		JLabel label_16 = new JLabel("Card1");
		panel.add(label_16);
		
		JLabel label_17 = new JLabel("Card2");
		panel.add(label_17);
		
		JLabel label_18 = new JLabel("BetShare");
		panel.add(label_18);
		
		JLabel label_19 = new JLabel("money");
		panel.add(label_19);
		
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
		
		CardCanvas[] CCards= new CardCanvas[5]; 
		for (int i=0; i<5; i++){
			CCards[i] = new CardCanvas(client.getTablestatus().communityCards[i]);
			panel_communityrow.add(CCards[i]);
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
		
		JLabel lblValueCurrent = new JLabel(client.getTablestatus().current);
		panel_overview.add(lblValueCurrent);
		
		JLabel lblValueDealer = new JLabel(client.getTablestatus().dealer);
		panel_overview.add(lblValueDealer);
		
		JLabel lblValueSmall = new JLabel(((Integer)client.getTablestatus().smallBlind).toString());
		panel_overview.add(lblValueSmall);
		
		JLabel lblValueBig = new JLabel(((Integer)(client.getTablestatus().smallBlind*2)).toString());
		panel_overview.add(lblValueBig);
		
		JLabel lblValuePot = new JLabel(((Integer)client.getTablestatus().pot).toString());
		panel_overview.add(lblValuePot);
		
		JPanel panel_choice = new JPanel();
		panel_community.add(panel_choice);
		FlowLayout fl_panel_choice = new FlowLayout(FlowLayout.CENTER, 5, 0);
		panel_choice.setLayout(fl_panel_choice);
		
		txtMoney = new JTextField();
		txtMoney.setMaximumSize(txtMoney.getPreferredSize() );
		txtMoney.setText("money");
		panel_choice.add(txtMoney);
		txtMoney.setColumns(10);
		
		JButton btnRaise = new JButton("Raise");
		panel_choice.add(btnRaise);
		
		JButton btnCall = new JButton("Call");
		panel_choice.add(btnCall);
		
		JButton btnFold = new JButton("Fold");
		panel_choice.add(btnFold);

	}

}
