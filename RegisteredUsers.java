package main;

import java.io.*;

public class RegisteredUsers {
	
	/*String s = "Deutsche Kinder sind zu dick";
	boolean b = s.regionMatches( 9, "Bewegungsarmut bei Kindern", 19, 6 );
	s beginnt bei 9 , string bei 19 vergleich auf 6 ziechen */
	
	/*substringBefore( "index.html", "." ) -> "index"
	substringAfter( "index.html", "." ) -> "html" */
	
RegisteredUsers(Server server){
	Server s= server;
}	
public static void main(String[] args) {
	RegisteredUsers ru = new RegisteredUsers();
	register("Hallo","Welt");
}
public void register(String textfieldname, String textfieldpwd) {
	String name, zeile;
	
	try {
		FileReader fileReader = null;
		fileReader = new FileReader("registered_users.txt");
	    BufferedReader bReader = new BufferedReader(fileReader);
	    while((zeile = bReader.readLine())!= null) {
	    	name = substringAfter(zeile, "#");
	    	if(name = sub.equals)

	    		
	    		
	    //test
		PrintWriter fileWriter = null;
		fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("registered_users.txt", true))); 
		//Datei landet im Java Projekt Ordner von eclipse
		fileWriter.print(textfieldname);
		fileWriter.print("#");
		fileWriter.println(textfieldpwd);
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (fileWriter != null) {
			fileWriter.flush();
			fileWriter.close();
		}
	}
}
	
	
	try {
		FileReader fileReader = null;
		fileReader = new FileReader("registered_users.txt");
	    BufferedReader bReader = new BufferedReader(fileReader);
	    String s = bReader.readLine();
	} catch (FileNotFoundException f){
		f.fillInStackTrace();
	} finally {
		bReader.close();
	}
	try {
		PrintWriter fileWriter = null;
		fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("registered_users.txt", true))); 
		//Datei landet im Java Projekt Ordner von eclipse
		fileWriter.println("Hallo Welt!");
		fileWriter.println("#");
		fileWriter.println("password");
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (fileWriter != null) {
			fileWriter.flush();
			fileWriter.close();
		}
	}
}
}