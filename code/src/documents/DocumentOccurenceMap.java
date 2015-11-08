package documents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Mapping of the terms and the number of documents they appear in.
 * 
 * @author 1337ago
 *
 */
public class DocumentOccurenceMap {
	private final Map<String, Integer> termOccurences;
	private final int documentCount;

	public DocumentOccurenceMap(Map<String, Integer> termOccurences,
			int documentCount) {
		this.termOccurences = termOccurences;
		this.documentCount = documentCount;
	}

	public static DocumentOccurenceMap fromFile(File documentOccurenceMapFile)
			throws IOException {
		FileReader fr = new FileReader(documentOccurenceMapFile);
		BufferedReader reader = new BufferedReader(fr);

		Map<String, Integer> termOccurences = new HashMap<String, Integer>();

		int documentCount = Integer.parseInt(reader.readLine());

		String entry = null;
		while ((entry = reader.readLine()) != null) {
			String[] elements = entry.split("\t");
			termOccurences.put(elements[0], Integer.parseInt(elements[1]));
		}
		reader.close();
		
		return new DocumentOccurenceMap(termOccurences, documentCount);
	}

	public int getOccurences(String term) {
		Integer occurences = termOccurences.get(term);
		if (occurences == null) {
			return 0;
		} else {
			return occurences;
		}
	}

	public int getDocumentCount() {
		return documentCount;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Documentcount: " + documentCount+"\n");
		for(Entry<String, Integer> entry : termOccurences.entrySet()){
			sb.append(entry+"\n");
		}
		return sb.toString();
	}
}
