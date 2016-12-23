package main;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import handChecker.*;

public class Table extends Thread{
	private Player dealer = null;
	private Player current = null;
	private Card[] communityCards = new Card[5];
	private LinkedList<Player> playerList = new LinkedList<Player>();
	private Deck deck = new Deck(52);
	private Deck discardPile = new Deck(0);
	private int roundCounter = 0;
	private int roundsToBlindRaise;
	private int smallBlind;
	private int maxPlayers;
	private final int STARTMONEY = 5000;
	private Server server;
	
	public Table(Server s, int mP, int sB, int rTBR){
		//Basiseinstellungen des Spiels
		this.server = s;
		this.maxPlayers = mP;
		this.smallBlind = sB;
		this.roundsToBlindRaise = rTBR;
	}
	
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

	private int getPot(){
		int tmpPot=0;
		for(int i=0; i< playerList.size(); i++){
			tmpPot += playerList.get(i).getPotShare();
		}
		return tmpPot;
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
			System.out.println(tmp.getPlayerName() + " Money: " + tmp.getMoney() + " Bet: " + tmp.getCurrentBet() + " PotShare: "
					+ tmp.getPotShare() + " Fold: " + tmp.getFolded() + " Cards: " + tmp.getCards()[0] + " " + tmp.getCards()[1]);
		}
		System.out.print("Tischkarten:");
		for (int i = 0; i<5; i++){
			System.out.print(" " + communityCards[i]);
		}
		System.out.print("\n");
		System.out.println(current.getPlayerName() + " ist dran!");
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
	
	private synchronized Object sendQuestion(String q, int s){
		server.sendQuestion(q, s);
		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		return server.getAnswer(s);
	}
	
	public synchronized void run (){
		//Spieler hinzufügen
		String newName = "";
		int seat = 0;
		for(int i=0; i < maxPlayers; i++){
			newName = server.getPlayerName(i);
			seat = server.getSeat(i);
			addPlayer(newName, STARTMONEY, seat);
		}
		
		//Starttischinfos senden
		Tablestatus firstTablestatus = new Tablestatus();
		firstTablestatus.dealer = "";
		firstTablestatus.current = "";
		firstTablestatus.playerCards = new Card[2];
		firstTablestatus.communityCards = new Card[5];
		firstTablestatus.playerNames = new String[maxPlayers+1];
		firstTablestatus.playerMoney = new int[maxPlayers+1];
		firstTablestatus.playerBet = new int[maxPlayers+1];
		firstTablestatus.playerFolded = new boolean[maxPlayers+1];
		for (int i=0; i<maxPlayers; i++){
			firstTablestatus.playerNames[i+1] = playerList.get(i).getPlayerName();
			firstTablestatus.playerMoney[i+1] = playerList.get(i).getMoney();
			firstTablestatus.playerBet[i+1] = 0;
			firstTablestatus.playerFolded[i+1] = false;
		}
		firstTablestatus.pot = 0;
		firstTablestatus.deckIsEmpty = false;
		firstTablestatus.discardpileIsEmpty = true;
		firstTablestatus.roundCounter = 0;
		firstTablestatus.roundsToBlindRaise = roundsToBlindRaise;
		firstTablestatus.smallBlind = smallBlind;
		server.sendUpdate("tablestatus", firstTablestatus);
		
		//Spielablauf
		while(playerList.size() > 1){
			roundCounter++;
			server.sendUpdate("roundCounter", roundCounter);
			pushDealer();
			server.sendUpdate("dealer", dealer.getPlayerName());
			deck.shuffle();
			if (roundCounter % roundsToBlindRaise == 0){
				smallBlind *= 2;
				server.sendUpdate("smallBlind", smallBlind);
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
				server.sendUpdate("current", current.getPlayerName());
				Player lastRaise = nextPlayer(current);
				switch(round){ //Aktionen abhängig von der Rundenzahl
					case 0: //Blinds zahlen
						current = nextPlayer(current);
						if (current.getMoney() < smallBlind){
							current.putMoney(current.getMoney());
						}else{
							current.putMoney(smallBlind);
						}
						server.sendUpdate("playerBet", new InfoWithIndex(current.getSeat(),current.getCurrentBet()));
						server.sendUpdate("playerMoney",new InfoWithIndex(current.getSeat(), current.getMoney()));
						current = nextPlayer(current);
						server.sendUpdate("current", current.getPlayerName());
						if (current.getMoney() < smallBlind*2){
							current.putMoney(current.getMoney());
						}else{
							current.putMoney(smallBlind*2);
						}
						server.sendUpdate("playerBet", new InfoWithIndex(current.getSeat(),current.getCurrentBet()));
						server.sendUpdate("playerMoney",new InfoWithIndex(current.getSeat(), current.getMoney()));
						lastRaise = nextPlayer(current);
						lastBet = smallBlind*2;
						//Karten austeilen
						for (int j = 0; j<2; j++){
							for (int i = 0; i<playerList.size(); i++){
								if (!playerList.get(i).getFolded()){
									Card drawn = deck.draw();
									playerList.get(i).addCard(drawn);
									server.sendUpdate("playerCards", new InfoWithIndex(j, drawn), playerList.get(i).getSeat());
								}
							}
						}
						break;
					case 1: //Flop
						discardPile.add(deck.draw());// burncard
						server.sendUpdate("discardpileIsEmpty", false);
						communityCards[0] = deck.draw();
						communityCards[1] = deck.draw();
						communityCards[2] = deck.draw();
						server.sendUpdate("communityCards", new InfoWithIndex(0, communityCards[0]));
						server.sendUpdate("communityCards", new InfoWithIndex(1, communityCards[1]));
						server.sendUpdate("communityCards", new InfoWithIndex(2, communityCards[2]));
						break;
					case 2: //Turn
						discardPile.add(deck.draw());// burncard
						communityCards[3] = deck.draw();
						server.sendUpdate("communityCards", new InfoWithIndex(3, communityCards[3]));
						break;
					case 3: //River
						discardPile.add(deck.draw());// burncard
						communityCards[4] = deck.draw();
						server.sendUpdate("communityCards", new InfoWithIndex(4, communityCards[4]));
						break;
				}
				
				//Setzrunden
				boolean wasInside = false;
				while((nextPlayer(current) != lastRaise || !wasInside)&& foldCount+1 < playerList.size()){
					wasInside = true;
					current = nextPlayer(current);
					server.sendUpdate("current", current.getPlayerName());
					if (!current.getFolded() && current.getMoney() > 0){
						printStatus();
						
						// Spielereingabe
						boolean loop = false;
						do{
							loop = false;

							//Aktionsauswahl
							String option = "";
							if (lastBet==0){
								option="checkOrBet";
							}else if (current.getCurrentBet() == lastBet){
								option="checkOrRaise";
							}else{
								option="callOrRaise";
							}
							int tmpSwitch = (int)sendQuestion(option, current.getSeat());
							switch (tmpSwitch){
								case 0: // Check or Call
									if (current.getMoney() + current.getCurrentBet() <= lastBet){
										if ((int)(sendQuestion("inputAllIn", current.getSeat()))==0){
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
									int raiseMoney = (int)sendQuestion( lastBet>0 ? "inputRaise":"inputBet", current.getSeat());
									if (raiseMoney + lastBet - current.getCurrentBet() >= current.getMoney()){
										if ((int)sendQuestion("inputAllIn", current.getSeat())==0){
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
								default:
									System.out.println("Ungültige Entscheidung");
									break;
							}
						}while(loop);
						server.sendUpdate("playerMoney", new InfoWithIndex(current.getSeat(), current.getMoney()));
						server.sendUpdate("playerBet", new InfoWithIndex(current.getSeat(), current.getCurrentBet()));
						server.sendUpdate("playerFolded", new InfoWithIndex(current.getSeat(), current.getFolded()));
					}
				}
				
				//"Zusammenschieben" des Pots
				for (int i = 0; i<playerList.size(); i++){
					playerList.get(i).raisePotShare();
				}
				server.sendUpdate("pot", getPot());
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
			for(int i=0; i < playerList.size(); i++){
				server.sendUpdate("playerMoney", new InfoWithIndex(playerList.get(i).getSeat(), playerList.get(i).getMoney()));
				server.sendUpdate("playerBet", new InfoWithIndex(playerList.get(i).getSeat(), playerList.get(i).getCurrentBet()));
				server.sendUpdate("playerFolded", new InfoWithIndex(playerList.get(i).getSeat(), playerList.get(i).getFolded()));
			}
			server.sendUpdate("pot", getPot());
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
			for(int i=0; i < playerList.size(); i++){
				for(int j=0; j<2; j++){
					server.sendUpdate("playerCards", new InfoWithIndex(j, null), playerList.get(i).getSeat());
				}
			}
			server.sendUpdate("discardpileIsEmpty", true);
			printStatus();
		}
		
		//Gewinner ermitteln
		server.sendUpdate("winner", playerList.element().getPlayerName());
		System.out.println(playerList.element().getPlayerName() + " hat das Spiel gewonnen!");
	}
}
