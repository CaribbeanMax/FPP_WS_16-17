package main;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtMoney;

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel CommunityPanel = new JPanel();
		add(CommunityPanel);
		CommunityPanel.setLayout(new GridLayout(0, 5, 0, 0));
		
		JLabel lblCurrent = new JLabel("Current");
		CommunityPanel.add(lblCurrent);
		
		JLabel lblDealer = new JLabel("Dealer");
		CommunityPanel.add(lblDealer);
		
		JLabel lblSmall = new JLabel("Small");
		CommunityPanel.add(lblSmall);
		
		JLabel lblBig = new JLabel("Big");
		CommunityPanel.add(lblBig);
		
		JLabel lblPot = new JLabel("Pot");
		CommunityPanel.add(lblPot);
		
		JPanel ChoicePanel = new JPanel();
		add(ChoicePanel);
		ChoicePanel.setLayout(new BoxLayout(ChoicePanel, BoxLayout.X_AXIS));
		
		txtMoney = new JTextField();
		txtMoney.setMaximumSize(txtMoney.getPreferredSize() );
		txtMoney.setText("money");
		ChoicePanel.add(txtMoney);
		txtMoney.setColumns(10);
		
		JButton btnRaise = new JButton("Raise");
		ChoicePanel.add(btnRaise);
		
		JButton btnCall = new JButton("Call");
		ChoicePanel.add(btnCall);
		
		JButton btnFold = new JButton("Fold");
		ChoicePanel.add(btnFold);

	}

}
