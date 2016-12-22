package main;

import java.net.*;
import java.io.*;

public class Clientmanager extends Thread{
	private Socket client;
	private Server server;
	private int seat;
	private String name;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean endConnection = false;
	private boolean asked = false;
	@SuppressWarnings("unused")	private boolean isViewer;
	@SuppressWarnings("unused")	private Object lastHeard = null;
	public Clientmanager(Socket c, boolean iV, Server s){
		try {
			client = c;
			server = s;
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			String requestedName="";
			do{
				requestedName = (String)sendQuestion("name");
			}while(!server.approve(requestedName));
			name=requestedName;
			int requestedSeat=-1;
			do{
				requestedSeat = (Integer)sendQuestion("seat");
			}while(!server.approve(requestedSeat));
			seat=requestedSeat;
			
		} catch (IOException e) {}
		this.isViewer = iV;
	}
	
	public void run(){
		try {
			//Begrüßung
			
			do{
				if (!asked){
					lastHeard = in.readObject();
				}
			}while(!endConnection);
			//Verabschiedung
			
			client.close();
		} catch (Exception e) {}
	}
	
	public void sendUpdate(String varName, Object data){
		Communication com = new Communication(varName, data);
		try{
			out.writeObject(com);
		}catch(IOException e){}
	}
	
	public Object sendQuestion(String varName){
		Communication com = new Communication("question",varName);
		Object data = null;
		try{
			asked = true;
			out.writeObject(com);
			data = in.readObject();
			asked = false;
		}catch(IOException e){
		}catch(ClassNotFoundException e){}
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
