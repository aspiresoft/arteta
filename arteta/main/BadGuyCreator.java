package com.aspire.arteta.main;
import java.util.ArrayList;

public class BadGuyCreator {
	private int x;
	private int y;
	private ArrayList<Penguin> penguins;
	private ArrayList<Murloc> murlocs;
	private ArrayList<Axe> axes;

	public BadGuyCreator(int x, int y) {
		this.x = x;
		this.y = y;
		penguins = null;
		murlocs = null;
		axes = null;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public void setPenguins(ArrayList<Penguin> penguins){
		this.penguins = penguins;
	}
	public void setMurlocs(ArrayList<Murloc> murlocs){
		this.murlocs = murlocs;
	}
	public void setAxes(ArrayList<Axe> axes){
		this.axes = axes;
	}

	public void createBadPenguin() {
		if(penguins != null)
		penguins.add(new Penguin(x, y));
	}

	public void createMurloc() {
		if(murlocs != null)
		murlocs.add(new Murloc(x, y));
	}

	public void createAxe() {
		if(axes != null)
		axes.add(new Axe(x, y));
	}

}
