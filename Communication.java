package main;

import java.io.*;

public class Communication implements Serializable{
	private static final long serialVersionUID = 1L;
	public final String varName;
	public final Serializable data;
	public Communication(String vN, Serializable d){
		this.varName = vN;
		this.data = d;
	}
	@Override
	public String toString(){
		return ("Var: " + varName + " data: " + data);
	}
}
