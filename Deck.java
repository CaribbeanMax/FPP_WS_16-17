package main;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck{
	private LinkedList<Card> deck = new LinkedList<Card>();
	
	Deck(int cardCount){
		switch (cardCount) {
		case 52:
			for (int i=0; i<13; i++){
				for (int j=0; j<4; j++){
					deck.add(new Card(Card.Color.values()[j], Card.Value.values()[i]));
				}
			}
			break;
		default:
			break;
		}
	}
	
	public void shuffle(){
		LinkedList<Card> tmp = new LinkedList<Card>();
		Random rnd = new Random();
		while(this.deck.isEmpty()==false){
			tmp.add(this.deck.remove(rnd.nextInt(this.deck.size())));
		}
		this.deck = tmp;
	}
	
	public Card draw(){
		return this.deck.remove();
	}
	
	public void add(Card c){
		this.deck.addFirst(c);
	}
	
	public List<Card> getList(){
		return this.deck;
	}
	
	public boolean isEmpty(){
		return this.deck.isEmpty();
	}
}
