package main;

import java.io.Serializable;

public class IDpack implements Serializable{
	private static final long serialVersionUID = 1L;
	String name, password;
	IDpack(String n, String pw){
		name = n;
		password = pw;
	}
}
