package main;

import handChecker.PokerCard;

public class Card implements PokerCard{
	Color color;
	Value value;
	
	Card(Color c, Value v){
		this.color = c;
		this.value = v;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public Value getValue(){
		return this.value;
	}
}
