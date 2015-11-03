package processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import documents.Document;

public class DocumentWriter {

	public static void write(Document doc, String outputfolder)
			throws IOException {
		String filename = doc.getName().split("-")[1] + ".txt";
		
		FileWriter fw = new FileWriter(new File(outputfolder +"/"+ filename));
		BufferedWriter writer = new BufferedWriter(fw);

		writer.write(doc.getName() + "\r\n");
		writer.write(doc.getWordCount() + "\r\n");

		for (Entry<String, Integer> entry : doc.getOccurences().entrySet()) {
			writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
		}
		writer.close();
//		System.out.println("Written " + doc.getName() + " to file at "
//				+ new Date() + ".");
	}

	public static void writeAll(List<Document> documents, String outputFolder)
			throws IOException {
		for (Document doc : documents) {
			if (doc == null || doc.getOccurences() == null
					|| doc.getOccurences().isEmpty()) {
				continue;
			}
			String childFolder = doc.getName().split("-")[0];
			
			new File(outputFolder + "/" +childFolder).mkdirs();
			
			write(doc, outputFolder + "/" + childFolder);
		}
	}
}
