package main;

import java.net.*;
import java.io.*;

public class Clientmanager extends Thread{
	private Socket client;
	private Server server;
	private int seat = -1;
	private String name;
	private String password;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean endConnection = false;
	@SuppressWarnings("unused")	private boolean isViewer;
	private Object lastHeard = null;
	public Clientmanager(Socket c, boolean iV, Server s){
		client = c;
		server = s;
		isViewer = iV;
	}
	
	public void run(){
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			//Begrüßung
			boolean loggedIn = false;
			do{
				lastHeard = in.readObject();
				IDpack id = (IDpack)((Communication)lastHeard).data;
				name = id.name;
				password = id.password;
				switch (((Communication)lastHeard).varName){
					case "login":
						if (server.approveLogin(id)){
							sendUpdate("login",true);
							loggedIn = true;
						}else{
							sendUpdate("login",false);
						}
						break;
					case "register":
						if (server.approveRegister(id)){
							sendUpdate("register",true);
						}else{
							sendUpdate("register",false);
						}
						break;
				}
			}while(!loggedIn);
			int requestedSeat;
			do{
				requestedSeat = (int)sendMyQuestion("seat");
			}while(!server.approveSeat(requestedSeat));
			seat=requestedSeat;
			//Hören
			do{
				lastHeard = in.readObject();
				System.out.println("I: " + lastHeard);
				synchronized(server.getTable()){
					server.getTable().notify();
				}
			}while(!endConnection);
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
	public String getPassword(){
		return this.password;
	}
}
