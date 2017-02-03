package main;

import java.net.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Client implements ActionListener{
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int nextAction = -1;
	private int nextActionValue;
	private String myName;
	private int mySeat;
	private boolean serverIsWaiting = false;
	
	public static void main(String args[]) {
		Client client = new Client();
		client.run();
	}
	
	public Tablestatus getTablestatus(){
		return this.tablestatus;
	}
	
	public String getMyName(){
		return this.myName;
	}
	
	private Tablestatus tablestatus = new Tablestatus();
	private ClientGUI frame = new ClientGUI(this);
	
	@Override public void actionPerformed(ActionEvent ae){
		String name, password, cPassword;
		System.out.println("Knopf mit ae: " + ae.getActionCommand());
		switch (ae.getActionCommand()){
			case "register":
				name = frame.getRegisterPanel().getPlayerName();
				password = frame.getRegisterPanel().getPassword();
				cPassword = frame.getRegisterPanel().getConfirmPassword();
				if(password.equals(cPassword)){
					try {
						out.writeObject(new Communication("register", new IDpack(name, password)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case "login":
				name = frame.getLoginPanel().getName();
				myName = name;
				password = frame.getLoginPanel().getPassword();
				try {
					out.writeObject(new Communication("login", new IDpack(name, password)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "toRegister":
				frame.setMasterPanel("register");
				break;
			case "call/check":
				if (serverIsWaiting){
					try{
						out.writeObject(0);
					}catch (Exception e){
						e.printStackTrace();
					}
					serverIsWaiting = false;
				}else{
					nextAction = 0;
				}
				break;
			case "bet/raise":
				nextActionValue = frame.getGamePanel().getInput();
				if (serverIsWaiting){
					try{
						out.writeObject(1);
					}catch (Exception e){
						e.printStackTrace();
					}
					serverIsWaiting = false;
				}else{
					nextAction = 1;
				}
				break;
			case "fold":
				if (serverIsWaiting){
					try{
						out.writeObject(2);
					}catch (Exception e){
						e.printStackTrace();
					}
					serverIsWaiting = false;
				}else{
					nextAction = 2;
				}
				break;
		}
	}
	
	
	private void run(){
		try {
			Socket server = new Socket("localhost", Server.GAMEPORT);
			in = new ObjectInputStream(server.getInputStream());
			out = new ObjectOutputStream(server.getOutputStream());
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
							frame.getGamePanel().addPlayersToTable();
							break;
						case "dealer":
							tablestatus.dealer = (String)varData;
							frame.getGamePanel().getLabel("dealer").setText((String)varData);
							break;
						case "current":
							tablestatus.current = (String)varData;
							frame.getGamePanel().getLabel("current").setText((String)varData);
							break;
						case "communityCards":
							InfoWithIndex tmpCC = (InfoWithIndex)varData;
							tablestatus.communityCards[tmpCC.index] = (Card)tmpCC.data;
							JPanel tmpCCP = frame.getGamePanel().getCardPanel("cCardP", tmpCC.index);
							tmpCCP.remove(tmpCCP.getComponent(0));
							tmpCCP.add(new CardCanvas((Card)tmpCC.data));
							break;
						case "playerNames":
							InfoWithIndex tmpPN = (InfoWithIndex)varData;
							tablestatus.playerNames[tmpPN.index] = (String)tmpPN.data;
							frame.getGamePanel().getLabel("name", tmpPN.index).setText((String)tmpPN.data);
							break;
						case "playerMoney":
							InfoWithIndex tmpPM = (InfoWithIndex)varData;
							tablestatus.playerMoney[tmpPM.index] = (Integer)tmpPM.data;
							frame.getGamePanel().getLabel("money", tmpPM.index).setText(((Integer)tmpPM.data).toString());
							break;
						case "playerBet":
							InfoWithIndex tmpPB = (InfoWithIndex)varData;
							tablestatus.playerBet[tmpPB.index] = (Integer)tmpPB.data;
							frame.getGamePanel().getLabel("bet", tmpPB.index).setText(((Integer)tmpPB.data).toString());
							break;
						case "playerFolded":
							InfoWithIndex tmpPF = (InfoWithIndex)varData;
							tablestatus.playerFolded[tmpPF.index] = (Boolean)tmpPF.data;
							if ((Boolean)tmpPF.data){
								JPanel tmpPFP = frame.getGamePanel().getCardPanel("pCardP0", tmpPF.index);
								if (tmpPFP.getComponentCount() != 0){
									tmpPFP.remove(tmpPFP.getComponent(0));
								}
								JPanel tmpPFP2 = frame.getGamePanel().getCardPanel("pCardP1", tmpPF.index);
								if (tmpPFP2.getComponentCount() != 0){
									tmpPFP2.remove(tmpPFP2.getComponent(0));
								}
							}
							break;
						case "playerCards":
							InfoWithIndex tmpPC = (InfoWithIndex)varData;
							if (tmpPC.data != null){
								InfoWithIndex tmpPC2 = (InfoWithIndex)tmpPC.data;
								tablestatus.playerCards[tmpPC2.index][tmpPC.index] = (Card)tmpPC2.data;
								JPanel tmpPC2P;
								if (tmpPC2.index==0){
									tmpPC2P = frame.getGamePanel().getCardPanel("pCardP0", tmpPC.index);
								}else{
									tmpPC2P = frame.getGamePanel().getCardPanel("pCardP1", tmpPC.index);
								}
								if(tmpPC2P.getComponentCount() != 0){
									tmpPC2P.remove(tmpPC2P.getComponent(0));
								}
								tmpPC2P.add(new CardCanvas((Card)tmpPC2.data));
								frame.validate();
							}
							break;
						case "pot":
							tablestatus.pot = (Integer)varData;
							frame.getGamePanel().getLabel("pot").setText(((Integer)varData).toString());
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
							frame.getGamePanel().getLabel("small").setText(((Integer)varData).toString());
							frame.getGamePanel().getLabel("big").setText(((Integer)(((Integer)varData)*2)).toString());
							break;
						case "login":
							if((boolean)varData){
								frame.setMasterPanel("game");
							}
							break;
						case "register":
							if((boolean)varData){
								frame.setMasterPanel("login");
							}
							break;
						case "question":
							//Antworten
							switch((String)varData){
								case "name":
									out.writeObject(JOptionPane.showInputDialog("Spielername:"));
									break;
								case "seat":
									mySeat = Integer.parseInt(JOptionPane.showInputDialog("Sitzplatznr:"));
									out.writeObject(mySeat);
									break;
								case "inputBet":
									out.writeObject(nextActionValue);
									break;
								case "inputRaise":
									out.writeObject(nextActionValue);
									break;
								case "inputAllIn":
									out.writeObject(JOptionPane.showConfirmDialog(null,"All-In gehen?"));
									break;
								case "checkOrBet":
								case "checkOrRaise":
								case "callOrRaise":
									if (nextAction == -1){
										serverIsWaiting = true;
									}else{
										out.writeObject(nextAction);
										nextAction = -1;
									}
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
					if (!varName.equals("question") && !varName.equals("login") && !varName.equals("register")){
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
						System.out.print("Ihr Sitzplatz: " + mySeat);
						System.out.println(" Ihre Karten: " + tablestatus.playerCards[0][mySeat] + " " + tablestatus.playerCards[1][mySeat]);
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
