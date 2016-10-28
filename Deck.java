package main;

public class Deck {
	Card[] card = new Card[52];
	Deck(){
		int counter=0;
		for (int i=0; i<13; i++){
			for (int j=0; j<4; j++){
				card[counter] = new Card(Card.Color.values()[j], Card.Value.values()[i]);
				counter++;
			}
		}
	}
}
