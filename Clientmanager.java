package main;

import java.net.*;
import java.io.*;

public class Clientmanager extends Thread{
	private Socket client;
	private int seat;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean endConnection = false;
	private boolean asked = false;
	@SuppressWarnings("unused")	private boolean isViewer;
	@SuppressWarnings("unused")	private Object lastHeard = null;
	public Clientmanager(Socket c, boolean iV){
		try {
			client = c;
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());			
		} catch (Exception e) {}
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
}
