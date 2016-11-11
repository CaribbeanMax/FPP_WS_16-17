package main;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import java.util.Random;

public class Table {
	private int dealer = -1;
	private Card[] communityCards = new Card[5];
	private LinkedList<Player> playerList = new LinkedList<Player>();
	private int maxPlayers;
	private Deck deck = new Deck(52);
	private Deck discardPile = new Deck(0);
	Table(int mP){
		this.maxPlayers = mP;
		
	}
	public void firstDealer(){
		if(dealer == -1){
			this.dealer = new Random().nextInt(playerList.size());
		}
	}
	public boolean addPlayer(String name, int money, int seat){	
		int counter = 0;
		do{
			if(counter < playerList.size()){
				if(playerList.get(counter).getSeat() > seat){
					playerList.add(counter, new Player(name, money, seat));
					return false;
				}else if(playerList.get(counter).getSeat() == seat){
					return true;
				}else{
					counter++;
				}
			}else if(counter > maxPlayers){
				return true;
			}else{
				playerList.addLast(new Player(name, money, seat));
				return false;
			}
		}while(true);
	}
	public void deletePlayer(Player player){
		playerList.remove(player);
	}
	public void pushDealer(){
		if(dealer+1 < playerList.size()){
			dealer ++;
		}else{
			dealer = 0; // dealer on first seat
		}
	}
	public Player getDealer(){
		return playerList.get(dealer);
	}
	public Player getPlayer(int index){
		return playerList.get(index);
	}
	
	public void showFlop(){
		discardPile.add(deck.draw());// discardpile.add(); burncard
		communityCards[0] = deck.draw();
		communityCards[1] = deck.draw();
		communityCards[2] = deck.draw();
	}
	public void showTurn(){
		discardPile.add(deck.draw());// discardpile.add(); burncard
		communityCards[3] = deck.draw();
	}
	public void showRiver(){
		discardPile.add(deck.draw());// discardpile.add(); burncard
		communityCards[4] = deck.draw();
	}
}
