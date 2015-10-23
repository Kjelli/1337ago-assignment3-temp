package assignment3;

import java.util.ArrayList;

public class Document {
	String name;
	ArrayList<String> html;
	public Document(String name){
		this.name = name;
		html = new ArrayList<String>();
	}
	public void addLine(String line){
		html.add(line);
	}
	
	public void printHtml(){

		System.out.println("html.size: "+html.size());
		for(int i=0; i<html.size();i++){
			System.out.println(html.get(i));
		}
	}
}


