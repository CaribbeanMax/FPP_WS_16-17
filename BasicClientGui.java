package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class BasicClientGui {

	private JFrame frame;
	private JPanel panel_8;
	private JLabel lblP;
	private JLabel lblCard;
	private JLabel lblCard_1;
	private JLabel lblPotshare;
	private JLabel lblMoney;
	private JLabel lblP_1;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel lblP_2;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel lblP_3;
	private JLabel label_11;
	private JLabel label_12;
	private JLabel label_13;
	private JLabel label_14;
	private JLabel lblP_4;
	private JLabel label_16;
	private JLabel label_17;
	private JLabel label_18;
	private JLabel label_19;
	private JLabel lblP_5;
	private JLabel label_21;
	private JLabel label_22;
	private JLabel label_23;
	private JLabel label_24;
	private JLabel lblCcard;
	private JLabel lblCcard_1;
	private JLabel lblCcard_2;
	private JLabel lblCcard_3;
	private JLabel lblCcard_4;
	private JLabel lblDealer;
	private JLabel lblSmall;
	private JLabel lblBig;
	private JLabel lblCurrent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasicClientGui window = new BasicClientGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BasicClientGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(9, 6, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblP = new JLabel("P1");
		panel.add(lblP);
		
		lblCard = new JLabel("Card1");
		panel.add(lblCard);
		
		lblCard_1 = new JLabel("Card2");
		panel.add(lblCard_1);
		
		lblPotshare = new JLabel("PotShare");
		panel.add(lblPotshare);
		
		lblMoney = new JLabel("Money");
		panel.add(lblMoney);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblP_1 = new JLabel("P2");
		panel_1.add(lblP_1);
		
		label_1 = new JLabel("Card1");
		panel_1.add(label_1);
		
		label_2 = new JLabel("Card2");
		panel_1.add(label_2);
		
		label_3 = new JLabel("PotShare");
		panel_1.add(label_3);
		
		label_4 = new JLabel("Money");
		panel_1.add(label_4);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblP_2 = new JLabel("P3");
		panel_2.add(lblP_2);
		
		label_6 = new JLabel("Card1");
		panel_2.add(label_6);
		
		label_7 = new JLabel("Card2");
		panel_2.add(label_7);
		
		label_8 = new JLabel("PotShare");
		panel_2.add(label_8);
		
		label_9 = new JLabel("Money");
		panel_2.add(label_9);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblP_3 = new JLabel("P4");
		panel_3.add(lblP_3);
		
		label_11 = new JLabel("Card1");
		panel_3.add(label_11);
		
		label_12 = new JLabel("Card2");
		panel_3.add(label_12);
		
		label_13 = new JLabel("PotShare");
		panel_3.add(label_13);
		
		label_14 = new JLabel("Money");
		panel_3.add(label_14);
		
		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblP_4 = new JLabel("P5");
		panel_4.add(lblP_4);
		
		label_16 = new JLabel("Card1");
		panel_4.add(label_16);
		
		label_17 = new JLabel("Card2");
		panel_4.add(label_17);
		
		label_18 = new JLabel("PotShare");
		panel_4.add(label_18);
		
		label_19 = new JLabel("Money");
		panel_4.add(label_19);
		
		JPanel panel_5 = new JPanel();
		frame.getContentPane().add(panel_5);
		panel_5.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblP_5 = new JLabel("P6");
		panel_5.add(lblP_5);
		
		label_21 = new JLabel("Card1");
		panel_5.add(label_21);
		
		label_22 = new JLabel("Card2");
		panel_5.add(label_22);
		
		label_23 = new JLabel("PotShare");
		panel_5.add(label_23);
		
		label_24 = new JLabel("Money");
		panel_5.add(label_24);
		
		JPanel panel_6 = new JPanel();
		frame.getContentPane().add(panel_6);
		panel_6.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblCcard = new JLabel("ccard1");
		panel_6.add(lblCcard);
		
		lblCcard_1 = new JLabel("ccard2");
		panel_6.add(lblCcard_1);
		
		lblCcard_2 = new JLabel("ccard3");
		panel_6.add(lblCcard_2);
		
		lblCcard_3 = new JLabel("ccard4");
		panel_6.add(lblCcard_3);
		
		lblCcard_4 = new JLabel("ccard5");
		panel_6.add(lblCcard_4);
		
		panel_8 = new JPanel();
		frame.getContentPane().add(panel_8);
		panel_8.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblDealer = new JLabel("dealer");
		panel_8.add(lblDealer);
		
		lblSmall = new JLabel("small");
		panel_8.add(lblSmall);
		
		lblBig = new JLabel("big");
		panel_8.add(lblBig);
		
		lblCurrent = new JLabel("current");
		panel_8.add(lblCurrent);
		
		JPanel panel_7 = new JPanel();
		frame.getContentPane().add(panel_7);
		
		JButton btnRaise = new JButton("Raise");
		panel_7.add(btnRaise);
		
		JButton btnCall = new JButton("Call");
		panel_7.add(btnCall);
		
		JButton btnFold = new JButton("Fold");
		panel_7.add(btnFold);
	}

}
