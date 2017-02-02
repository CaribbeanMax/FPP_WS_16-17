package main;

import java.io.Serializable;

public class Tablestatus implements Serializable{
	private static final long serialVersionUID = 1L;
	public String dealer;
	public String current;
	public Card[] communityCards = new Card[5];
	public String[] playerNames;
	public int[] playerMoney;
	public int[] playerBet;
	public boolean[] playerFolded;
	public Card[][] playerCards;
	public int pot;
	public boolean deckIsEmpty;
	public boolean discardpileIsEmpty;
	public int roundCounter;
	public int roundsToBlindRaise;
	public int smallBlind;
}
