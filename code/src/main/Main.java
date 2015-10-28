package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import documents.DocumentReader;
import documents.UnprocessedDocument;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Started at time: " + new Date());
		DocumentReader r = new DocumentReader();
		File[] files = new File("bitesize/").listFiles();
		for (File file : files) {
			ArrayList<UnprocessedDocument> docs = r.readFile(file);
			/*
			 * 
			 * Do things with docs here.
			 * 
			 * */
		}
		System.out.println("Completed at time: " + new Date());

	}

}
