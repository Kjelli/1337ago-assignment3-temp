package processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import queries.Query;
import queries.QueryVocabulary;
import queries.QueryWriter;
import queries.RawQuery;
import queries.RawQueryReader;
import whitelist.QueryWhitelist;
import whitelist.Whitelist;
import documents.Document;
import documents.DocumentWriter;
import documents.RawDocument;
import documents.RawDocumentReader;

public class Preprocessor {

	public static boolean process(String queryFilename,
			String documentFoldername, String outputFolder) {
		System.out.println("Started preprocessing at time: " + new Date());

		// Initializing files

		File queryFile = new File(queryFilename);
		if (!queryFile.exists()) {
			System.err.println("Queryfile " + queryFilename
					+ " does not exist!");
			return false;
		}

		File documentFolder = new File(documentFoldername);
		if (!documentFolder.exists()) {
			System.err.println("Documentfolder " + documentFoldername
					+ " does not exist!");
			return false;
		}

		// Files exist, read and process queries.

		RawQueryReader qr = new RawQueryReader();
		QueryProcessor queryProc = new QueryProcessor();

		List<RawQuery> rawQueries = null;
		try {
			rawQueries = qr.readQueries(queryFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}

		// Build vocabulary based on query terms

		QueryVocabulary vocabulary = new QueryVocabulary();
		List<Query> queries = new ArrayList<Query>();
		for (RawQuery rawQuery : rawQueries) {
			Query temp = queryProc.process(rawQuery);
			if (temp == null) {
				continue;
			}
			vocabulary.addQueryTerms(temp);
			queries.add(temp);
		}
		// Write queries to file
		System.out.println("Writing queries...");
		try {
			QueryWriter.writeAll(queries, outputFolder);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		System.out.println(queries.size() + " queries written to file.");

		Whitelist whitelist = new QueryWhitelist(vocabulary);

		DocumentProcessor.getInstance().setWhitelist(whitelist);

		File[] documentFiles = documentFolder.listFiles();

		RawDocumentReader r = new RawDocumentReader();
		int maxDotsInLine = 5;
		int dotCounter = 0;
		int fileCounter = 0;
		int totalDocuments = 0;

		System.out.println("Reading " + documentFiles.length
				+ " files containing documents ...");

		// Read files and process docs
		HashMap<String, Integer> documentOccurences = new HashMap<>();
		for (File file : documentFiles) {
			fileCounter++;
			System.out.print("#");
			if (++dotCounter >= maxDotsInLine) {
				System.out.printf("\n(%.2f%% files read)\n",
						(fileCounter * 100.0f) / documentFiles.length);
				dotCounter = 0;
			}

			// Process the documents from each file

			ArrayList<Document> pDocs = new ArrayList<>();
			for (RawDocument unDoc : r.readFile(file)) {
				Document doc = DocumentProcessor.getInstance().process(unDoc);
				if (doc == null) {
					continue;
				}
				
				// Increment the total number of documents
				
				totalDocuments++;

				// Map each term and the number of documents it appears in.

				for (String term : doc.getOccurenceMap().keySet()) {
					int newValue = documentOccurences.get(term) != null ? documentOccurences
							.get(term) + 1 : 1;
					documentOccurences.put(term, newValue);
				}
				pDocs.add(doc);

			}
			try {
				DocumentWriter.writeAll(pDocs, outputFolder);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		// Write the document occurence map to file

		try {
			writeDocumentTermOccurences(documentOccurences, totalDocuments, outputFolder);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		System.out.println("\n\n Done preprocessing documents at " + new Date()
				+ ".");
		return true;
	}

	private static void writeDocumentTermOccurences(
			HashMap<String, Integer> documentOccurences, int documentCount, String outputFolder)
			throws IOException {
		FileWriter fw = new FileWriter(new File(outputFolder
				+ "/document_occurence.txt"));
		BufferedWriter writer = new BufferedWriter(fw);

		writer.write(documentCount+"\r\n");
		
		for (Entry<String, Integer> entry : documentOccurences.entrySet()) {
			writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
		}
		writer.close();
	}
}
