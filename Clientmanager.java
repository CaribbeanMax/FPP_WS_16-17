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
				System.out.println("I: " + lastHeard);
				synchronized(server.getTable()){
					server.getTable().notify();
				}
			}while(!endConnection);
			//Verabschiedung
			
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendUpdate(String varName, Serializable data){
		Communication com = new Communication(varName, data);
		System.out.println("U: " + com);
		try{
			out.writeObject(com);
		}catch(IOException e){
			e.printStackTrace();
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
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void sendQuestion(String varName){
		Communication com = new Communication("question",varName);
		System.out.println("F: " + com);
		try{
			out.writeObject(com);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public Object getAnswer(){
		return lastHeard;
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
