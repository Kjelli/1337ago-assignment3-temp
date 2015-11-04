package documents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentReader {

	public static Document read(File documentFile) throws IOException {
		FileReader fr = new FileReader(documentFile);
		BufferedReader br = new BufferedReader(fr);
		String name = br.readLine();
		Integer wordCount = Integer.parseInt(br.readLine());

		Map<String, Integer> occurences = new HashMap<String, Integer>();

		String entry = null;
		while ((entry = br.readLine()) != null) {
			String[] elements = entry.split("\t");
			occurences.put(elements[0], Integer.parseInt(elements[1]));
		}

		return new Document(name, occurences, wordCount);
	}

	public static List<Document> readAll(File documentFolder)
			throws IOException {
		List<Document> documents = new ArrayList<Document>();
		File[] documentFiles = documentFolder.listFiles();
		for (File documentFile : documentFiles) {
			documents.add(read(documentFile));
		}
		return documents;
	}
}
