package main;

import java.io.Serializable;

public class InfoWithIndex implements Serializable{
	private static final long serialVersionUID = 1L;
	public final int index;
	public final Serializable data;
	public InfoWithIndex(int i, Serializable d){
		this.index = i;
		this.data = d;
	}
	@Override
	public String toString(){
		return ("Index: " + index + " data: " + data);
	}
}
