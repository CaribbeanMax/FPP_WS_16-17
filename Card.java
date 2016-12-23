package main;

import handChecker.PokerCard;
import java.io.Serializable;

public class Card implements PokerCard, Serializable{
	private static final long serialVersionUID = 1L;
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
