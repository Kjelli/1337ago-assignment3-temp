package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import documents.*;
import processing.*;
import queries.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Started at time: " + new Date());

		QueryReader qr = new QueryReader();
		QueryProcessor queryProc = new QueryProcessor();

		List<UnprocessedQuery> rawQueries = qr.readQuery(new File("data/queries.xml"));
		List<Query> queries = new ArrayList<Query>();

		for(UnprocessedQuery rawQuery : rawQueries){
			Query temp = queryProc.process(rawQuery);
			queries.add(temp);
			//System.out.println(temp.terms);
		}

		File[] files = new File("bitesize/").listFiles();

		DocumentReader r = new DocumentReader();
		DocumentProcessor docProc = new DocumentProcessor();
		float procentage = 0;
		int increment = 10;
		for (File file : files) {
			ArrayList<UnprocessedDocument> docs = r.readFile(file);
			for(UnprocessedDocument unDoc : docs ){
				Document doc = docProc.process(unDoc);
				procentage += (float) increment/docs.size();
				System.out.println("Progress: " + procentage);
				
			}
			//procentage += increment;
			
			//break;
		}
		System.out.println("Completed at time: " + new Date());
	}

}
