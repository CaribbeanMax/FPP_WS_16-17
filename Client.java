package main;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Client {
	public static void main(String args[]) {
		Client client = new Client();
		client.run();
	}
	public void	run(){
		try {
			Socket server = new Socket("localhost", 4242);
			InputStream in = server.getInputStream();
			OutputStream out = server.getOutputStream();
			//namen senden um jeden einzeln hinzuzufügen
			ObjectOutputStream oout = new ObjectOutputStream(out);
			Integer.parseInt(JOptionPane.showInputDialog("Anzahl an Plätzen am Spieltisch:", mP));
			String playerName = (JOptionPane.showInputDialog("Spielername: "));
			oout.writeObject(playerName); oout.flush();
			server.close();
		} catch(UnknownHostException e) {
			System.out.println("Can´t find host.");
		} catch (IOException e) {
			System.out.println("Error connecting to host."); 
		}
	}
}
