package main;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		Table table = new Table();
		table.run();
	}
	public static int inputMaxPlayers(int mP){
		return Integer.parseInt(JOptionPane.showInputDialog("Anzahl an Plätzen am Spieltisch:", mP));
	}
	public static int inputSmallBlind(int sB){
		return Integer.parseInt(JOptionPane.showInputDialog("Startwert für den Small Blind:", sB));
	}
	public static String inputPlayerName(int c){
		return JOptionPane.showInputDialog("Name von Spieler " + (c+1) + "?" + (c>0?"\nAbbrechen für Spielbeginn":""), "Spieler "+ (c+1));
	}
	public static int inputStartMoney(String N){
		return Integer.parseInt(JOptionPane.showInputDialog("Startgeld von " + N, 5000));
	}
	public static int inputSeat(String N, int c){
		return Integer.parseInt(JOptionPane.showInputDialog("Sitzplatz von " + N, c));
	}
	public static int inputAction(String N,String[] options){
		return JOptionPane.showOptionDialog(null, ("Was will " + N + " tun?"), "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
	}
	public static int inputAllIn(){
		return JOptionPane.showConfirmDialog(null, "All in gehen?");
	}
	public static int inputRaise(){
		return Integer.parseInt(JOptionPane.showInputDialog("Um wieviel erhöhen?", 0));
	}
	public static int inputBet(){
		return Integer.parseInt(JOptionPane.showInputDialog("Wieviel setzen?", 0));
	}
}
