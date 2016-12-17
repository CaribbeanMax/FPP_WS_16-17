package main;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Client {
	public static void main(String args[]) {
		Client client = new Client();
		client.run();
	}
	
	private Tablestatus tablestatus;
	
	public void	run(){
		try {
			Socket server = new Socket("localhost", Server.GAMEPORT);
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			Object lastHeard = null;
			boolean end = false;
			do{
				try{
					lastHeard = in.readObject();
				}catch (ClassNotFoundException e){
					lastHeard = null;
				}
				//Fallunterscheidung für Inputs
				if (lastHeard instanceof Communication){
					String varName = ((Communication)lastHeard).varName;
					switch(varName){
						case "end":
							end = true;
						case "tablestatus":
							this.tablestatus = (Tablestatus)((Communication)lastHeard).data;
							break;
						case "dealer":
							this.tablestatus.dealer = (String)((Communication)lastHeard).data;
							break;
						case "current":
							this.tablestatus.current = (String)((Communication)lastHeard).data;
							break;
						case "communityCards":
							InfoWithIndex tmpCC = ((InfoWithIndex)(((Communication)lastHeard).data));
							this.tablestatus.communityCards[tmpCC.index] = (Card)tmpCC.data;
							break;
						case "playerNames":
							InfoWithIndex tmpPN = ((InfoWithIndex)(((Communication)lastHeard).data));
							this.tablestatus.playerNames[tmpPN.index] = (String)tmpPN.data;
							break;
						case "playerMoney":
							InfoWithIndex tmpPM = ((InfoWithIndex)(((Communication)lastHeard).data));
							this.tablestatus.playerMoney[tmpPM.index] = (int)tmpPM.data;
							break;
						case "playerBet":
							InfoWithIndex tmpPB = ((InfoWithIndex)(((Communication)lastHeard).data));
							this.tablestatus.playerBet[tmpPB.index] = (int)tmpPB.data;
							break;
						case "playerFolded":
							InfoWithIndex tmpPF = ((InfoWithIndex)(((Communication)lastHeard).data));
							this.tablestatus.playerFolded[tmpPF.index] = (boolean)tmpPF.data;
						case "playerCards":
							this.tablestatus.playerCards = (Card[])((Communication)lastHeard).data;
							break;
						case "pot":
							this.tablestatus.pot = (int)((Communication)lastHeard).data;
							break;
						case "deckIsEmpty":
							this.tablestatus.deckIsEmpty = (boolean)((Communication)lastHeard).data;
							break;
						case "discardpileIsEmpty":
							this.tablestatus.discardpileIsEmpty = (boolean)((Communication)lastHeard).data;
							break;
						case "roundCounter":
							this.tablestatus.roundCounter = (int)((Communication)lastHeard).data;
							break;
						case "roundsToBlindRaise":
							this.tablestatus.roundsToBlindRaise = (int)((Communication)lastHeard).data;
							break;
						case "smallBlind":
							this.tablestatus.smallBlind = (int)((Communication)lastHeard).data;
							break;
						case "question":
							//Antworten
							String[] option = {null,null,"Fold"};
							switch((String)((Communication)lastHeard).data){
								case "name":
									out.writeObject(JOptionPane.showInputDialog("Spielername:"));
									break;
								case "seat":
									out.writeObject(JOptionPane.showInputDialog("Sitzplatznr:"));
									break;
								case "bet":
									out.writeObject(JOptionPane.showInputDialog("Wieviel setzen?"));
									break;
								case "raise":
									out.writeObject(JOptionPane.showInputDialog("Um wieviel erhöhen?"));
									break;
								case "allIn":
									out.writeObject(JOptionPane.showInputDialog("All-In gehen?"));
									break;
								case "checkOrBet":
									option[0] = "Check"; option[1] = "Bet";
									out.writeObject(JOptionPane.showOptionDialog(null, ("Was möchten Sie tun?"), "",
											JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null));
									break;
								case "checkOrRaise":
									option[0] = "Check"; option[1] = "Raise";
									out.writeObject(JOptionPane.showOptionDialog(null, ("Was möchten Sie tun?"), "",
											JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null));
									break;
								case "callOrRaise":
									option[0] = "Call"; option[1] = "Raise";
									out.writeObject(JOptionPane.showOptionDialog(null, ("Was möchten Sie tun?"), "",
											JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null));
									break;
								default: System.out.println("Didn't understand Question from server.");
							}
							break;
						default: System.out.println("Didn't understand Message from server.");
					}
					if (!varName.equals("question")){
						//print status falls neue Info
						System.out.println("Small Blind: " + tablestatus.smallBlind + " Dealer: " + tablestatus.dealer
							+ " Pot: " + tablestatus.pot);
						for (int i = 0; i<tablestatus.playerNames.length; i++){
							System.out.println(tablestatus.playerNames[i] + " Money: " + tablestatus.playerMoney[i]
								+ " Bet: " + tablestatus.playerBet[i] + " Folded: " + tablestatus.playerFolded[i]);
						}
						System.out.println("Tischkarten:");
						for (int i = 0; i<5; i++){
							System.out.print(" " + tablestatus.communityCards[i]);
						}
						System.out.println();
						System.out.println("Ihre Karten: " + tablestatus.playerCards[0] + tablestatus.playerCards[1]);
						System.out.println(tablestatus.current + " ist dran!");
					}
				}
			}while(!end);
			server.close();
		} catch (UnknownHostException e) {
			System.out.println("Can´t find host.");
		} catch (IOException e) {
			System.out.println("Error connecting to host."); 
		}
		//Siegeranzeige
	}
}
