package main;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Random;
import handChecker.*;

public class Table {
	private Player dealer = null;
	private Player current = null;
	private Card[] communityCards = new Card[5];
	private LinkedList<Player> playerList = new LinkedList<Player>();
	private Deck deck = new Deck(52);
	private Deck discardPile = new Deck(0);
	private int roundCounter = 0;
	private int roundsToBlindRaise = 4;
	private int smallBlind = 25;
	private int maxPlayers = 8;
	private int startMoney = 5000;
	
	private boolean addPlayer(String name, int money, int seat){
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
	
	private void deletePlayer(Player player){
		if (current.equals(player)){
			current = nextPlayer(current);
		}
		playerList.remove(player);
	}
	
	private Player nextPlayer(Player p){
		return (playerList.indexOf(p)+1 < playerList.size()) ? (playerList.get(playerList.indexOf(p)+1)) : (playerList.getFirst());
	}
	
	private Player prevPlayer(Player p){
		return (playerList.indexOf(p) > 0) ? (playerList.get(playerList.indexOf(p)-1)) : (playerList.get(playerList.size()-1));
	}
	
	private void pushDealer(){
		if(dealer == null){
			this.dealer = playerList.get(new Random().nextInt(playerList.size()));
		}else{
			dealer = nextPlayer(dealer);
		}
	}
	
	private void printStatus(){
		System.out.println("aktueller Small Blind: " + smallBlind + " Dealer: " + dealer.getPlayerName());
		for (int i = 0; i<playerList.size(); i++){
			Player tmp = playerList.get(i);
			System.out.println(tmp.getPlayerName() + " Money: " + tmp.getMoney() + " Bet: " + tmp.getCurrentBet() + " PotShare: " + tmp.getPotShare() + " Fold: " + tmp.getFolded() + " Cards: " + tmp.getCards()[0] + " " + tmp.getCards()[1]);
		}
		System.out.println(current.getPlayerName() + " ist dran!");
		System.out.print("Tischkarten:");
		for (int i = 0; i<5; i++){
			System.out.print(" " + communityCards[i]);
		}
		System.out.print("\n");
	}
	
	private List<PokerCard> getSevenCards(Player p){
		List<PokerCard> sevenCards = new LinkedList<PokerCard>();
		for (int i = 0; i < 5; i++){
			sevenCards.add(communityCards[i]);
		}
		sevenCards.add(p.getCards()[0]);
		sevenCards.add(p.getCards()[1]);
		return sevenCards;
	}
	
	public void run (){
		//Basiseinstellungen des Spiels
		maxPlayers = Main.inputMaxPlayers(maxPlayers);
		smallBlind = Main.inputSmallBlind(smallBlind);
		roundsToBlindRaise = Main.inputBlindRaise(roundsToBlindRaise);
		//Spieler hinzufügen
		String newName = "";
		int counter = 0;
		int seat = 0;
		do{
			newName = Main.inputPlayerName(counter); 
			if (newName != null){
				if (!newName.equals("")){
					counter++;
					startMoney = Main.inputStartMoney(newName,startMoney);
					do{
						seat = Main.inputSeat(newName, counter);
					}while (addPlayer(newName, startMoney, seat));
				}
			}
		} while ((newName != null) && (counter < maxPlayers));
		//Spielablauf
		while(playerList.size() > 1){
			roundCounter++;
			pushDealer();
			deck.shuffle();
			if (roundCounter % roundsToBlindRaise == 0){
				smallBlind *= 2;
			}
			int foldCount = 0;
			int round = 0;
			do{
				int lastBet = 0;
				if (playerList.size() != 2){
					current = dealer;
				}else{
					current = prevPlayer(dealer);
				}
				Player lastRaise = nextPlayer(current);
				switch(round){ //Aktionen abhängig von der Rundenzahl
					case 0: //Blinds zahlen
						current = nextPlayer(current);
						if (current.getMoney() < smallBlind){
							current.putMoney(current.getMoney());
						}else{
							current.putMoney(smallBlind);
						}
						current = nextPlayer(current);
						if (current.getMoney() < smallBlind*2){
							current.putMoney(current.getMoney());
						}else{
							current.putMoney(smallBlind*2);
						}
						lastRaise = current;
						lastBet = smallBlind*2;
						//Karten austeilen
						for (int j = 0; j<2; j++){
							for (int i = 0; i<playerList.size(); i++){
								if (!playerList.get(i).getFolded()){
									playerList.get(i).addCard(deck.draw());
								}
							}
						}
						break;
					case 1: //Flop
						discardPile.add(deck.draw());// burncard
						communityCards[0] = deck.draw();
						communityCards[1] = deck.draw();
						communityCards[2] = deck.draw();
						break;
					case 2: //Turn
						discardPile.add(deck.draw());// burncard
						communityCards[3] = deck.draw();
						break;
					case 3: //River
						discardPile.add(deck.draw());// burncard
						communityCards[4] = deck.draw();
						break;
				}
				//Setzrunden
				while(nextPlayer(current) != lastRaise && foldCount+1 < playerList.size()){
					current = nextPlayer(current);
					if (!current.getFolded() && current.getMoney() > 0){
						printStatus();
						// Spielereingabe
						boolean loop = false;
						do{
							loop = false;
							//Für Ausgabe
							String options[] = {null,null,"Fold"};
							if (lastBet==0){
								options[0]="Check";
								options[1]="Bet";
							}else if (current.getCurrentBet() == lastBet){
								options[0]="Check";
								options[1]="Raise";
							}else{
								options[0]="Call";
								options[1]="Raise";
							}
							//Aktionsauswahl
							switch (Main.inputAction(current.getPlayerName(), options)){
								case 0: // Check or Call
									if (current.getMoney() + current.getCurrentBet() <= lastBet){
										if (Main.inputAllIn()==0){
											current.putMoney(current.getMoney());
											if (lastRaise == null){
												lastRaise = current;
											}
										}else{
											loop = true;
										}
									}else{
										current.putMoney(lastBet - current.getCurrentBet());
										if (lastRaise == null){
											lastRaise = current;
										}
									}
									break;
								case 1: // Bet or Raise
									int raiseMoney = lastBet>0 ? Main.inputRaise() : Main.inputBet();
									if (raiseMoney + lastBet - current.getCurrentBet() >= current.getMoney()){
										if (Main.inputAllIn()==0){
											current.putMoney(current.getMoney());
											if (current.getCurrentBet() > lastBet){
												lastRaise = current;
												lastBet = current.getCurrentBet();
											}
										}else{
											loop = true;
										}
									}else{
										current.putMoney(lastBet + raiseMoney - current.getCurrentBet());
										lastRaise = current;
										lastBet = current.getCurrentBet();
									}
									break;
								case 2: // Fold
									current.setFolded(true);
									foldCount++;
									break;
							}
						}while(loop);
					}
				}
				//"Zusammenschieben" des Pots
				for (int i = 0; i<playerList.size(); i++){
					playerList.get(i).raisePotShare();
				}
				round++;
				lastBet = 0;
			}while (round<4 && foldCount+1 < playerList.size());
			//Auswertung
			printStatus();
			Integer minPotShare= null;
			do{
				HandValue maxHandValue= null;
				minPotShare= null;
				LinkedList<Player> holdsMaxHand= new LinkedList<Player>();
				HandChecker checker= new HandChecker();	
				for (int playerCount= 0; playerCount < playerList.size(); playerCount++){ //get minimal PotShare
					if (!playerList.get(playerCount).getFolded() && playerList.get(playerCount).getPotShare()!= 0){
						if ((Integer)minPotShare == null || minPotShare > playerList.get(playerCount).getPotShare()){
							minPotShare=playerList.get(playerCount).getPotShare();
						}
						if(maxHandValue == null || checker.check(getSevenCards(playerList.get(playerCount))).compareTo(maxHandValue)> 0){
							maxHandValue=checker.check(getSevenCards(playerList.get(playerCount)));
							holdsMaxHand=new LinkedList<Player>();
							holdsMaxHand.add(playerList.get(playerCount));
						}else if(checker.check(getSevenCards(playerList.get(playerCount))).compareTo(maxHandValue) == 0){
							holdsMaxHand.add(playerList.get(playerCount));
						}
					}
				}
				int pot = 0;
				if (minPotShare != null){
					for (int playerCount= 0; playerCount < playerList.size(); playerCount++){
						if(playerList.get(playerCount).getPotShare() >= minPotShare){
							pot += minPotShare;
							playerList.get(playerCount).looseShare(minPotShare);
						}else{
							pot += playerList.get(playerCount).getPotShare();
							playerList.get(playerCount).looseShare(playerList.get(playerCount).getPotShare());
						}
					}
					for (int playerCount= 0; playerCount < holdsMaxHand.size(); playerCount++){
						holdsMaxHand.get(playerCount).gainMoney(pot/holdsMaxHand.size());
					}
				}
			}while(minPotShare != null);
			printStatus();
			//Aufräumen
			for (int i = playerList.size() - 1; i>=0; i--){
				if (playerList.get(i).getCards()[0] != null || playerList.get(i).getCards()[1] != null ){
					discardPile.add(playerList.get(i).discardCard());
					discardPile.add(playerList.get(i).discardCard());
				}
				playerList.get(i).setFolded(false);
				if (playerList.get(i).getMoney() == 0){
					deletePlayer(playerList.get(i));
				}
			}
			for (int i = 0; i<5; i++){
				discardPile.add(communityCards[i]);
				communityCards[i] = null;
			}
			while (!discardPile.isEmpty()){
				deck.add(discardPile.draw());
			}
			printStatus();
		}
	}
}
