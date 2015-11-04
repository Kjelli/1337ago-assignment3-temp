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
import similarity.CosineSimilarity;
import documents.Document;
import documents.DocumentOccurenceMap;
import documents.DocumentReader;

public class Main {

	public static void main(String[] args) {
		long start = System.nanoTime();
		System.out.println("Started at time: " + new Date());
		/*
		 * Uncomment this to perform preprocessing. Make sure the paths are
		 * correct.
		 */

//		 String queryFilename = "data/queries.xml";
//		 String documentSourceFolder = "E:/csiro_corpus/csiro-corpus";
//		 String preprocessedFolder = "output/preprocessed";
//		 Preprocessor.process(queryFilename, documentSourceFolder, preprocessedFolder);
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
		Ranker ranker = new SimpleRanker(map, new CosineSimilarity());

		List<DocumentQueryResult> results = new ArrayList<>();
		
		for (Query query : queries) {
			DocumentQueryResult qRes = ranker.rank(query, documents);
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
	// public static void main(String[] args) throws FileNotFoundException {
	// System.out.println("Started at time: " + new Date());
	//
	// QueryReader qr = new QueryReader();
	// QueryProcessor queryProc = new QueryProcessor();
	//
	// // Read file and process queries
	// System.out.println("Reading file and processing queries");
	//
	// List<UnprocessedQuery> rawQueries = qr.readQuery(new File(
	// "data/queries.xml"));
	// List<Query> queries = new ArrayList<Query>();
	//
	// QueryVocabulary vocabulary = new QueryVocabulary();
	//
	// for (UnprocessedQuery rawQuery : rawQueries) {
	// Query temp = queryProc.process(rawQuery);
	// queries.add(temp);
	// vocabulary.addQueryTerms(temp);
	// }
	//
	// Whitelist whitelist = new QueryWhitelist(vocabulary);
	//
	// DocumentProcessor.getInstance().setWhitelist(whitelist);
	//
	// File[] files = new File("E:/csiro_corpus/csiro-corpus").listFiles();
	//
	// UnprocessedDocumentReader r = new UnprocessedDocumentReader();
	// DocumentTermMatrix dtm = new DocumentTermMatrix();
	// System.out.println("Reading file and processing docs");
	// int maxDocuments = -1;
	// int docCounter = 0;
	// int maxDotsInLine = 5;
	// int dotCounter = 0;
	// int fileCounter = 25;
	// int maxFiles = -1;
	//
	// // Read files and process docs
	// HashMap<String, Integer> documentOccurences = new HashMap<>();
	// String outputfolder = "output/preprocessed";
	//
	// for (File file : files) {
	// fileCounter++;
	// System.out.print(".");
	// if (++dotCounter >= maxDotsInLine) {
	// System.out.printf("(%.2f%% complete)\n", (fileCounter * 100.0f)
	// / files.length);
	// dotCounter = 0;
	// }
	// ArrayList<UnprocessedDocument> docs = r.readFile(file);
	// ArrayList<Document> pDocs = new ArrayList<>();
	// for (UnprocessedDocument unDoc : docs) {
	// Document doc = DocumentProcessor.getInstance().process(unDoc);
	// if (doc == null) {
	// continue;
	// }
	// for (String term : doc.getOccurences().keySet()) {
	// documentOccurences
	// .put(term,
	// (documentOccurences.get(term) != null) ? documentOccurences
	// .get(term) + 1 : 1);
	// }
	//
	// // if (doc != null) {
	// // dtm.addDocument(doc);
	// // }
	// pDocs.add(doc);
	// if (maxDocuments != -1 && docCounter++ >= maxDocuments) {
	// break;
	// }
	//
	// }
	// try {
	// DocumentWriter.writeAll(pDocs, outputfolder);
	// } catch (IOException e) {
	// e.printStackTrace();
	// System.exit(1);
	// }
	//
	// if (maxFiles != -1 && fileCounter >= maxFiles) {
	// break;
	// }
	// }
	//
	// try {
	// writeDocumentTermOccurences(documentOccurences, outputfolder);
	// } catch (IOException e) {
	// e.printStackTrace();
	// System.exit(0);
	// }
	//
	// System.out.println("\n\n Done reading and processing documents.");
	//
	// // Calculate tf idf
	// System.out.println("Calculating tf idf map");
	// TFIDFDocumentTermMatrix matrix = dtm.generateTFIDFMap();
	//
	// SimilarityStrategy<Double> strat = new CosineSimilarity();
	//
	// // Setting up printing
	// PrintWriter out = null;
	// try {
	// out = new PrintWriter(new BufferedWriter(new FileWriter(
	// "output/results("
	// + new Date().toString().toLowerCase()
	// .replace(' ', '_').replace(':', '-')
	// + ").txt", true)));
	// } catch (IOException io) {
	// io.printStackTrace();
	// }
	//
	// // Evaluating queries on DocumentTermMatrix
	// System.out.println("Evaluating queries on documents");
	// for (Query query : queries) {
	// DocumentQueryResult result = matrix.runQuery(query, strat);
	// System.out.println("Query : " + query + ", "
	// + result.getRankMap().size() + " results. ("
	// + result.getOmittedCount() + " omitted)");
	// if (result.getRankMap().size() > 0) {
	// for (Entry<Document, Double> entry : result.getRankMap()
	// .entrySet()) {
	// if (out != null) {
	// out.println((query.id + " Q0 " + entry.getKey() + " " + entry
	// .getValue()));
	// }
	// }
	// }
	// }
	// if (out != null) {
	// out.close();
	// }
	// System.out.println("\n\nCompleted at time: " + new Date());
	// }

}
