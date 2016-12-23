package main;

import java.net.*;
import java.io.*;

public class Clientmanager extends Thread{
	private Socket client;
	private Server server;
	private int seat = -1;
	private String name;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean endConnection = false;
	private boolean flip = false;
	@SuppressWarnings("unused")	private boolean isViewer;
	private Object lastHeard = null;
	public Clientmanager(Socket c, boolean iV, Server s){
		client = c;
		server = s;
		this.isViewer = iV;
	}
	
	public void run(){
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			//Begrüßung
			String requestedName;
			do{
				requestedName = (String)sendMyQuestion("name");
			}while(!server.approve(requestedName));
			name=requestedName;
			int requestedSeat;
			do{
				requestedSeat = (int)sendMyQuestion("seat");
			}while(!server.approve(requestedSeat));
			seat=requestedSeat;
			//Hören
			do{
				lastHeard = in.readObject();
				System.out.println(lastHeard);
				flip = !flip;
			}while(!endConnection);
			//Verabschiedung
			
			client.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void sendUpdate(String varName, Serializable data){
		Communication com = new Communication(varName, data);
		System.out.println("U: " + com);
		try{
			out.writeObject(com);
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	private Object sendMyQuestion(String varName){
		Communication com = new Communication("question",varName);
		Object data = null;
		System.out.println("F: " + com);
		try{
			out.writeObject(com);
			data = in.readObject();
		}catch(IOException e){
			System.out.println("Error connecting to client.\n" + e); 
		}catch(ClassNotFoundException e) {
			System.out.println("Communication error.\n" + e);
		}
		return data;
	}
	
	public Object sendQuestion(String varName){
		Communication com = new Communication("question",varName);
		Object data = null;
		System.out.println("F: " + com);
		try{
			boolean tmp = flip;
			out.writeObject(com);
			while (tmp == flip){}
			data = lastHeard;
		}catch(IOException e){
			System.out.println("Error connecting to client.\n" + e); 
		}
		return data;
	}

	public void endConnection(){
		this.endConnection = true;
	}
	
	public int getSeat(){
		return this.seat;
	}
	public String getPName(){
		return this.name;
	}
}
