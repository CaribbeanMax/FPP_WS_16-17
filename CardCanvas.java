package main;

import javax.imageio.*;
import java.awt.*;
import java.io.IOException;

public class CardCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	private Image cardI;
	
	public CardCanvas(Card card) {
		setMinimumSize(new Dimension(100,200));
		String url = "/Kartenbilder/";
		if (card == null){
			url += "unknownCard";
		}else{
			switch (card.getColor().toString()){
				case "CLUBS": url += "clubs/"; break;
				case "DIAMONDS": url += "diamonds/"; break;
				case "HEARTS": url += "hearts/"; break;
				case "SPADES": url += "spades/"; break;
			}
			switch (card.getValue().toString()){
				case "ASS": url += "14"; break;
				case "TWO": url += "2"; break;
				case "THREE": url += "3"; break;
				case "FOUR": url += "4"; break;
				case "FIVE": url += "5"; break;
				case "SIX": url += "6"; break;
				case "SEVEN": url += "7"; break;
				case "EIGHT": url += "8"; break;
				case "NINE": url += "9"; break;
				case "TEN": url += "10"; break;
				case "JACK": url += "11"; break;
				case "QUEEN": url += "12"; break;
				case "KING": url += "13"; break;
			}
		}
		url += ".png";
		try{
			cardI = ImageIO.read(getClass().getResource(url));
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	@Override public void paint(Graphics g){
		if (getWidth()*cardI.getHeight(null) < cardI.getWidth(null)*getHeight()){
			g.drawImage(cardI, 0, 0, getWidth(), getWidth()*cardI.getHeight(null)/cardI.getWidth(null), null);
		}else{
			g.drawImage(cardI, 0, 0, getHeight()*cardI.getWidth(null)/cardI.getHeight(null), getHeight(), null);
		}
	}
}
