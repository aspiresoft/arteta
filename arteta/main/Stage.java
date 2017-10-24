package com.aspire.arteta.main;
import java.util.ArrayList;


public class Stage {

	private String name;
	private Object type;
	private ArrayList<Object> list;
	
	public Stage(String name,Object type){
		this.name = name;
		this.type = type;
		list = new ArrayList<Object>();
	}
}
