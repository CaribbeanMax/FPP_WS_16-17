package main;

import javax.swing.JOptionPane;

public class Player {
	private int money;
	private String playerName;
	private int currentBet=0;
	private int potShare=0;
	private Card[] handCards = new Card[2];
	private int seat;
	private boolean folded = false;
	public Player(String n, int m, int s){
		this.playerName = n; this.money = m; this.seat=s;
	}
	public void addCard(Card card){
		if(handCards[0] == null){
			handCards[0] = card;
		}else if(handCards[1] == null){
			handCards[1] = card;
		}else{
			JOptionPane.showMessageDialog(null, "Player hand is full!");
		}
	}
	public Card discardCard(){
		Card tmp;
		if(handCards[0] != null){
			tmp = handCards[0];
			handCards[0] = null;
			return tmp;
		}else if(handCards[1] != null){
			tmp = handCards[1];
			handCards[1] = null;
			return tmp;
		}else{
			JOptionPane.showMessageDialog(null, "Player hand is empty already!");
			return  null;
		}
	}
	public boolean putMoney(int payMoney){
		if(money > payMoney){
			money -= payMoney;
			currentBet += payMoney;
			return false;
		}else{
			JOptionPane.showMessageDialog(null, "Player has not enough money!");
			return true;
		}
	}
	public void raisePotShare(){
 		potShare += currentBet;
 		currentBet = 0;
	}
	public void looseShare(int minPotShare){
		potShare -= minPotShare;
	}
	public void gainMoney(int moneyGain){
		money += moneyGain;
	}
	public void getPot(int potMoney){
		money += potMoney;
		potShare = 0;
	}
	public String getPlayerName(){
		return playerName;
	}
	public int getMoney(){
		return money;
	}
	public int getCurrentBet(){
		return currentBet;
	}
	public int getPotShare(){
		return potShare;
	}
	public Card[] getCards(){
		return handCards;
	}
	public int getSeat(){
		return this.seat;
	}
	public boolean getFolded(){
		return this.folded;
	}
	public void setFolded(boolean f){
		this.folded = f;
	}
}
