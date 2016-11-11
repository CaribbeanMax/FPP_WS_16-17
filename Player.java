package main;

import javax.swing.JOptionPane;

public class Player {
	private int money;
	private String playerName;
	private int currentBet=0;
	private int potShare=0;
	private Card[] handCards = new Card[2];
	public Player(String n, int m){
		this.playerName = n; this.money = m;
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
			return true;
		}
	}
	public void raisePotShare(){
		potShare += currentBet;
		currentBet = 0;
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
}
