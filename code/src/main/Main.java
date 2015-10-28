package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import documents.Document;
import documents.DocumentReader;
import documents.UnprocessedDocument;
import processing.DocumentProcessor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Started at time: " + new Date());
		DocumentReader r = new DocumentReader();
		File[] files = new File("bitesize/").listFiles();
		DocumentProcessor docProc = new DocumentProcessor();
		for (File file : files) {
			ArrayList<UnprocessedDocument> docs = r.readFile(file);
			/*
			 * 
			 * Do things with docs here.
			 * 
			 * */
			for(UnprocessedDocument unDoc : docs ){
				Document doc = docProc.process(unDoc);
				//System.out.println(doc.getName());
			}
		}
		System.out.println("Completed at time: " + new Date());

	}

}
