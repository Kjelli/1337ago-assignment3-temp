package documents;

import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		System.out.print("done");

	}

}
