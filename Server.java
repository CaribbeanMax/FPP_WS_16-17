package main;

import java.net.*;
import java.io.*;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Server extends Thread{
	public static final int GAMEPORT = 4242;
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	private final int MAX_PLAYERS_INIT = 4;
	private final int SMALL_BLIND_INIT = 25;
	
	private ServerSocket listener;
	private LinkedList<Clientmanager> clientList = new LinkedList<Clientmanager>();
	
	public void run(){
		int maxPlayers = Integer.parseInt(JOptionPane.showInputDialog("Anzahl an Plätzen am Spieltisch:", MAX_PLAYERS_INIT));
		int smallBlind = Integer.parseInt(JOptionPane.showInputDialog("Startwert für den Small Blind:", SMALL_BLIND_INIT));
		int roundsToBlindRaise = Integer.parseInt(JOptionPane.showInputDialog("Runden bis zur Blinderhöhung:", maxPlayers*2));
		Table table = new Table(this,maxPlayers,smallBlind,roundsToBlindRaise);
		try{
			System.out.println("Server läuft und wartet auf Clients.");
			listener = new ServerSocket(GAMEPORT);
			do{
				Socket newPlayer = listener.accept();
				System.out.println("Neuer Client entdeckt!");
				clientList.add(new Clientmanager(newPlayer,false, this));
				clientList.getLast().start();
			}while(maxPlayers>clientList.size());
			int readyPlayers = 0;
			do{
				readyPlayers = 0;
				for (int i=0; i<clientList.size(); i++){
					if (clientList.get(i).getSeat() != -1){
						readyPlayers++;
					}
				}
			}while(maxPlayers>readyPlayers);
			table.start();
			System.out.println("Spiel gestartet.");
			do{
				Socket newViewer = listener.accept();
				clientList.add(new Clientmanager(newViewer,true, this));
				clientList.getLast().start();
			}while(table.isAlive());
			System.out.println("Spiel beendet.");
			do{
				clientList.getLast().endConnection();
				if(!clientList.getLast().isAlive()){
					clientList.removeLast();
				}
			}while(clientList.size()>0);
			listener.close();
			System.out.println("Server wird beendet.");
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public void sendUpdate(String varName, Serializable data){
		for (int i=0; i<clientList.size(); i++){
			clientList.get(i).sendUpdate(varName, data);
		}
	}
	
	public void sendUpdate(String varName, Serializable data, int seat){
		int clientnr = -1;
		int i = 0;
		while (i<clientList.size() && clientnr == -1){
			if (clientList.get(i).getSeat() == seat){
				clientnr = i;
			}else{
				i++;
			}
		}
		clientList.get(i).sendUpdate(varName, data);
	}
	
	public Object sendQuestion(String varName, int seat){
		int clientnr = -1;
		int i = 0;
		while (i<clientList.size() && clientnr == -1){
			if (clientList.get(i).getSeat() == seat){
				clientnr = i;
			}else{
				i++;
			}
		}
		return clientList.get(i).sendQuestion(varName);
	}

	public String getPlayerName(int i){
		return clientList.get(i).getPName();
	}
	
	public int getSeat(int i){
		return clientList.get(i).getSeat();
	}
	
	public boolean approve(String name){
		for(int i=0; i < clientList.size(); i++){
			if (name.equals(clientList.get(i).getPName())){
				return false;
			}
		}
		return true;
	}
	
	public boolean approve(int seat){
		for(int i=0; i < clientList.size(); i++){
			if (seat == clientList.get(i).getSeat()){
				return false;
			}
		}
		return true;
	}
	
}
