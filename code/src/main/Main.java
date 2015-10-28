package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import documents.*;
import processing.*;
import queries.*;
import similarity.CosineSimilarity;
import similarity.SimilarityStrategy;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Started at time: " + new Date());

		QueryReader qr = new QueryReader();
		QueryProcessor queryProc = new QueryProcessor();

		// Read file and process queries
		System.out.println("Reading file and processing queries");

		List<UnprocessedQuery> rawQueries = qr.readQuery(new File(
				"data/queries.xml"));
		List<Query> queries = new ArrayList<Query>();

		for (UnprocessedQuery rawQuery : rawQueries) {
			Query temp = queryProc.process(rawQuery);
			queries.add(temp);
		}

		File[] files = new File("bitesize/").listFiles();

		DocumentReader r = new DocumentReader();
		DocumentTermMatrix dtm = new DocumentTermMatrix();

		// Read files and process docs
		System.out.println("Reading file and processing docs");
		int maxDocuments = 10;
		int docCounter = 0;
		int maxDotsInLine = 5;
		int dotCounter = 0;
		int fileCounter = 0;
		for (File file : files) {
			fileCounter++;
			System.out.print(".");
			if (++dotCounter >= maxDotsInLine) {
				System.out.printf("(%.2f%% complete)\n", (fileCounter * 100.0f)
						/ files.length);
				dotCounter = 0;
			}
			ArrayList<UnprocessedDocument> docs = r.readFile(file);
			docCounter = 0;
			for (UnprocessedDocument unDoc : docs) {
				Document doc = DocumentProcessor.process(unDoc);
				dtm.addDocument(doc);
				if (docCounter++ >= maxDocuments) {
					break;
				}
			}
		}
		System.out.println("\n\n Done reading and processing documents.");

		// Calculate tf idf
		System.out.println("Calculating tf idf map");
		TFIDFDocumentTermMatrix matrix = dtm.generateTFIDFMap();

		SimilarityStrategy<Double> strat = new CosineSimilarity();

		// Setting up printing
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					"output/results("
							+ new Date().toString().toLowerCase()
									.replace(' ', '_').replace(':', '-')
							+ ").txt", true)));
		} catch (IOException io) {
			io.printStackTrace();
		}

		// Evaluating queries on DocumentTermMatrix
		System.out.println("Evaluating queries on documents");
		for (Query query : queries) {
			DocumentQueryResult result = matrix.runQuery(query, strat);
			System.out.println("Query : " + query + ", "
					+ result.getRankMap().size() + " results. ("
					+ result.getOmittedCount() + " omitted)");
			if (result.getRankMap().size() > 0) {
				for (Entry<Document, Double> entry : result.getRankMap()
						.entrySet()) {
					if (out != null) {
						out.println((query.id + " Q0 " + entry.getKey() + " " + entry
								.getValue()));
					}
				}
			}
		}
		if (out != null) {
			out.close();
		}
		System.out.println("\n\nCompleted at time: " + new Date());
	}
}
