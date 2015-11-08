package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import processing.Preprocessor;
import processing.ResultWriter;
import queries.Query;
import queries.QueryReader;
import ranking.DocumentQueryResult;
import ranking.Ranker;
import ranking.SimpleRanker;
import documents.Document;
import documents.DocumentOccurenceMap;
import documents.DocumentReader;
/**
 * 
 * @author 1337ago
 */
public class Main {

	public static void main(String[] args) {
		long start = System.nanoTime();
		System.out.println("Started at time: " + new Date());
		/*
		 * Uncomment this to perform preprocessing. Make sure the paths are
		 * correct.
		 */

		String queryFilename = "data/queries.xml";
		String documentSourceFolder = "csiro-corpus";
		String preprocessedFolder = "output/preprocessed";
		Preprocessor.process(queryFilename, documentSourceFolder, preprocessedFolder);
		//		 System.exit(0);
		/*
		 * Operate the preprocessed data
		 */

		// Read preprocessed documents
		String outputFolder = "output";
		String ppParentFolder = outputFolder+"/preprocessed";
		String ppDocumentsFoldername = ppParentFolder + "/documents";
		String ppQueriesFoldername = ppParentFolder + "/queries";
		String documentOccurenceMapFilename = ppParentFolder
				+ "/document_occurence.txt";

		String resultsFolder = outputFolder+"/results";

		File ppDocumentsFolder = new File(ppDocumentsFoldername);
		if (!ppDocumentsFolder.exists()) {
			System.err
			.println("The folder containing preprocessed documents specified "
					+ ppDocumentsFoldername + " does not exist!");
			System.exit(1);
		}

		File ppQueriesFolder = new File(ppQueriesFoldername);
		if (!ppQueriesFolder.exists()) {
			System.err
			.println("The folder containing preprocessed queries specified "
					+ ppQueriesFoldername + " does not exist!");
			System.exit(1);
		}

		File documentOccurenceMapFile = new File(documentOccurenceMapFilename);
		if (!documentOccurenceMapFile.exists()) {
			System.err
			.println("The folder containing the document occurence map specified "
					+ documentOccurenceMapFilename + " does not exist!");
			System.exit(1);
		}

		// Query folder and document folder exists

		File[] documentFolders = ppDocumentsFolder.listFiles();

		// Read the document occurence map
		DocumentOccurenceMap map = null;
		try {
			map = DocumentOccurenceMap.fromFile(documentOccurenceMapFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Read preprocessed documents

		System.out.println("Reading preprocessed documents ...");

		int maxDotsInLine = 5;
		int fileCounter = 0;

		List<Document> documents = new ArrayList<>();
		try {
			for (File documentFolder : documentFolders) {

				documents.addAll(DocumentReader.readAll(documentFolder));

				System.out.print(".");
				if (++fileCounter % maxDotsInLine == 0) {
					System.out.printf("\n(%.2f%% complete)\n",
							(fileCounter * 100.0f) / documentFolders.length);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Done reading documents!\n\n");
		File[] queryFolders = ppQueriesFolder.listFiles();

		// Read preprocessed queries

		System.out.println("Started reading queries at time: " + new Date());

		fileCounter = 0;
		List<Query> queries = new ArrayList<>();
		try {
			for (File queryFolder : queryFolders) {

				queries.addAll(QueryReader.readAll(queryFolder));

				System.out.print(".");
				if (++fileCounter % maxDotsInLine == 0) {
					System.out.printf("\n(%.2f%% complete)\n",
							(fileCounter * 100.0f) / queryFolders.length);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Done reading queries!\n\n");

		// Queries and documents have been read
		System.out.println("Number of queries: " + queries.size());
		System.out.println("Number of documents: " + documents.size());

		// Run queries on the documents
		Ranker ranker = new SimpleRanker(map);

		List<DocumentQueryResult> results = new ArrayList<>();

		for (Query query : queries) {
			DocumentQueryResult qRes = ranker.bm25score(query, documents);
			results.add(qRes);
			System.out.println(query.getId() + " had " + qRes.getRankMap().size() + " results! (" + qRes.getOmittedCount() + " omitted)");
		}

		Collections.sort(results);

		System.out.println("Writing results to file ...");
		ResultWriter.writeAll(results, resultsFolder);
		System.out.println("Done writing results to file!");

		double timeElapsed = (System.nanoTime() - start) / 1_000_000_000.0f;
		System.out.printf(
				"\n\nCompleted at time: %s.\n\nRunning for %.2f seconds.",
				new Date().toString(), timeElapsed);
	}
}
