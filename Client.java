package main;

import java.net.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Client implements ActionListener{
	public static void main(String args[]) {
		Client client = new Client();
		client.run();
	}
	
	private Tablestatus tablestatus = new Tablestatus();
	private ClientGUI frame = new ClientGUI(this);

	public Tablestatus getTablestatus(){
		return tablestatus;
	}
	
	@Override public void actionPerformed(ActionEvent ae){
		switch (ae.getActionCommand()){
			case "register":
				frame.getRegisterPanel().getPlayerName();
				frame.getRegisterPanel().getPassword();
				frame.getRegisterPanel().getConfirmPassword();
				break;
			case "login":
				break;
			case "toRegister":
				break;
			case "bet/raise":
				break;
			case "call/check":
				break;
			case "fold":
				break;
		}
	}
	
	
	private void run(){
		try {
			Socket server = new Socket("localhost", Server.GAMEPORT);
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			try {
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Object lastHeard = null;
			boolean end = false;
			do{
				try{
					lastHeard = in.readObject();
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
				//Fallunterscheidung für Inputs
				if (lastHeard instanceof Communication){
					String varName = ((Communication)lastHeard).varName;
					Serializable varData = ((Communication)lastHeard).data;
					System.out.println(varName + " + " + varData);
					switch(varName){
						case "end":
							end = true;
							break;
						case "winner":
							
							break;
						case "tablestatus":
							tablestatus = (Tablestatus)varData;
							break;
						case "dealer":
							tablestatus.dealer = (String)varData;
							break;
						case "current":
							tablestatus.current = (String)varData;
							break;
						case "communityCards":
							InfoWithIndex tmpCC = (InfoWithIndex)varData;
							tablestatus.communityCards[tmpCC.index] = (Card)tmpCC.data;
							break;
						case "playerNames":
							InfoWithIndex tmpPN = (InfoWithIndex)varData;
							tablestatus.playerNames[tmpPN.index] = (String)tmpPN.data;
							break;
						case "playerMoney":
							InfoWithIndex tmpPM = (InfoWithIndex)varData;
							tablestatus.playerMoney[tmpPM.index] = (Integer)tmpPM.data;
							break;
						case "playerBet":
							InfoWithIndex tmpPB = (InfoWithIndex)varData;
							tablestatus.playerBet[tmpPB.index] = (Integer)tmpPB.data;
							break;
						case "playerFolded":
							InfoWithIndex tmpPF = (InfoWithIndex)varData;
							tablestatus.playerFolded[tmpPF.index] = (Boolean)tmpPF.data;
							break;
						case "playerCards":
							InfoWithIndex tmpPC = (InfoWithIndex)varData;
							tablestatus.playerCards[tmpPC.index] = (Card)tmpPC.data;
							break;
						case "pot":
							tablestatus.pot = (Integer)varData;
							break;
						case "deckIsEmpty":
							tablestatus.deckIsEmpty = (Boolean)varData;
							break;
						case "discardpileIsEmpty":
							tablestatus.discardpileIsEmpty = (Boolean)varData;
							break;
						case "roundCounter":
							tablestatus.roundCounter = (Integer)varData;
							break;
						case "roundsToBlindRaise":
							tablestatus.roundsToBlindRaise = (Integer)varData;
							break;
						case "smallBlind":
							tablestatus.smallBlind = (Integer)varData;
							break;
						case "question":
							//Antworten
							String[] option = {null,null,"Fold"};
							switch((String)varData){
								case "name":
									out.writeObject(JOptionPane.showInputDialog("Spielername:"));
									break;
								case "seat":
									out.writeObject(Integer.parseInt(JOptionPane.showInputDialog("Sitzplatznr:")));
									break;
								case "inputBet":
									out.writeObject(Integer.parseInt(JOptionPane.showInputDialog("Wieviel setzen?")));
									break;
								case "inputRaise":
									out.writeObject(Integer.parseInt(JOptionPane.showInputDialog("Um wieviel erhöhen?")));
									break;
								case "inputAllIn":
									out.writeObject(Integer.parseInt(JOptionPane.showInputDialog("All-In gehen?")));
									break;
								case "checkOrBet":
									option[0] = "Check"; option[1] = "Bet";
									Integer tmpCB = JOptionPane.showOptionDialog(null, ("Was möchten Sie tun?"), "",
											JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);
									out.writeObject(tmpCB);
									break;
								case "checkOrRaise":
									option[0] = "Check"; option[1] = "Raise";
									Integer tmpCR = JOptionPane.showOptionDialog(null, ("Was möchten Sie tun?"), "",
											JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);
									out.writeObject(tmpCR);
									break;
								case "callOrRaise":
									option[0] = "Call"; option[1] = "Raise";
									Integer tmpCR2 = JOptionPane.showOptionDialog(null, ("Was möchten Sie tun?"), "",
											JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);
									out.writeObject(tmpCR2);
									break;
								default:
									System.out.println("Didn't understand Question from server.");
									break;
							}
							break;
						default:
							System.out.println("Didn't understand Message from server: " + varName);
							break;
					}
					if (!varName.equals("question")){
						//print status falls neue Info
						System.out.println("Small Blind: " + tablestatus.smallBlind + " Dealer: " + tablestatus.dealer
							+ " Pot: " + tablestatus.pot);
						for (int i = 1; i<tablestatus.playerNames.length; i++){
							System.out.println(tablestatus.playerNames[i] + " Money: " + tablestatus.playerMoney[i]
								+ " Bet: " + tablestatus.playerBet[i] + " Folded: " + tablestatus.playerFolded[i]);
						}
						System.out.print("Tischkarten: ");
						for (int i = 0; i<5; i++){
							System.out.print(" " + tablestatus.communityCards[i]);
						}
						System.out.println();
						System.out.println("Ihre Karten: " + tablestatus.playerCards[0] + " " + tablestatus.playerCards[1]);
						System.out.println(tablestatus.current + " ist dran!");
					}
				} else {
					System.out.println("Kommunikationsfehler");
				}
			}while(!end);
			server.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Siegeranzeige
	}
}
