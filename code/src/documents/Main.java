package documents;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.print("Started at time: " + new Date());
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
		System.out.print("Completed at time: " + new Date());

	}

}
