package main;

import handChecker.PokerCard;

public class Card implements PokerCard{
	private Color color;
	private Value value;
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
	@Override
	public String toString(){
		return "[" + color + " " + value + "]";
	}
}
