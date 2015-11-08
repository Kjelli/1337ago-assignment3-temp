package processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import ranking.DocumentQueryResult;
import documents.Document;
/**
 * 
 * @author 1337ago
 */
public class ResultWriter {

	public static boolean writeAll(List<DocumentQueryResult> results,
			String outputFoldername) {
		// Setting up printing
		PrintWriter out = null;
		
		File outputFolder = new File(outputFoldername);
		outputFolder.mkdirs();
		
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					outputFolder
							+ "/results("
							+ new Date().toString().toLowerCase()
									.replace(' ', '_').replace(':', '-')
							+ ").txt", true)));
			for (DocumentQueryResult result : results) {
				for (Entry<Document, Double> entry : result.getRankMap()
						.entrySet()) {
					out.println(result.getQueryId() + " Q0 " + entry.getKey()
							+ " " + entry.getValue());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if (out != null) {
			out.close();
		}
		return true;
	}

}
