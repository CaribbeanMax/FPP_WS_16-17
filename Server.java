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
			listener = new ServerSocket(GAMEPORT);
			do{
				Socket newPlayer = listener.accept();
				clientList.add(new Clientmanager(newPlayer,false));
				clientList.getLast().start();
			}while(maxPlayers<clientList.size());
			table.start();
			do{
				Socket newViewer = listener.accept();
				clientList.add(new Clientmanager(newViewer,true));
				clientList.getLast().start();
			}while(table.isAlive());
			do{
				clientList.getLast().endConnection();
				if(!clientList.getLast().isAlive()){
					clientList.removeLast();
				}
			}while(clientList.size()>0);
			listener.close();
		}catch(IOException e){}
	}
	
	public void sendUpdate(String varName, Object data){
		for (int i=0; i<clientList.size(); i++){
			clientList.get(i).sendUpdate(varName, data);
		}
	}
	
	public Object sendQuestion(String varName, int seat){
		int clientnr = -1;
		int i = 0;
		while (i<clientList.size() && clientnr == -1){
			if (clientList.get(i).getSeat() == seat){
				clientnr = i;
			}
		}
		return clientList.get(i).sendQuestion(varName);
	}

	public String getPlayerName(int i){
		return clientList.get(i).getName();
	}
	
	public int getSeat(int i){
		return clientList.get(i).getSeat();
	}
	
}
