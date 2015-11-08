package documents;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
/**
 * 
 * @author 1337ago
 */
public class DocumentWriter {

	public static void write(Document doc, String outputfolder)
			throws IOException {
		String filename = doc.getName().split("-")[1] + ".txt";
		
		FileWriter fw = new FileWriter(new File(outputfolder +"/"+ filename));
		BufferedWriter writer = new BufferedWriter(fw);

		writer.write(doc.getName() + "\r\n");
		writer.write(doc.getWordCount() + "\r\n");

		for (Entry<String, Integer> entry : doc.getOccurenceMap().entrySet()) {
			writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
		}
		writer.close();
	}

	public static void writeAll(List<Document> documents, String outputFolder)
			throws IOException {
		for (Document doc : documents) {
			if (doc == null || doc.getOccurenceMap() == null
					|| doc.getOccurenceMap().isEmpty()) {
				continue;
			}
			String childFolder = "documents/"+doc.getName().split("-")[0];
			
			new File(outputFolder + "/" +childFolder).mkdirs();
			
			write(doc, outputFolder + "/" + childFolder);
		}
	}
}
