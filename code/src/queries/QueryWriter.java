package queries;

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
public class QueryWriter {

	public static void writeAll(List<Query> queries, String outputFolder)
			throws IOException {
		for (Query query : queries) {
			if (query == null || query.getTerms() == null
					|| query.getTerms().isEmpty()) {
				continue;
			}
			String childFolder = "queries/query_" + query.getId();

			new File(outputFolder + "/" + childFolder).mkdirs();
			FileWriter fw = new FileWriter(new File(outputFolder + "/"
					+ childFolder + "/" + query.getId() + ".txt"));
			BufferedWriter writer = new BufferedWriter(fw);

			writer.write(query.getId() + "\r\n");
			writer.write(query.getWordCount() + "\r\n");

			for (Entry<String, Integer> entry : query.getTerms().entrySet()) {
				writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
			}
			writer.close();
		}
	}
}
