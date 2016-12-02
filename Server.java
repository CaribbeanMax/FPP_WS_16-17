package main;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
	public static void main(String[] args) {
		Server server = new Server();		
	}
	public Server(){
		try {
			ServerSocket listener = new ServerSocket(4242);
			while(true){
				Socket client = listener.accept(); // wait for connection
				Clientmanager cm = new Clientmanager(client);
				cm.start();
			}
		} catch (Exception e) {}
	}
	
	
	// folgend aufgaben vom clientmangager
	public void run(){	
		try {
			Table table = new Table();
			
			while(playerList.size() < maxPlayers) {//true 
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				String name = bin.readLine();
				playerList.addPlayer(name , startMoney, seat);
			}
			//Table table = new Table();
			table.run();// Spielerliste wird erstellt nach connecten
			
			client.close();
			
			listener.close();
			
		}
	}
}


