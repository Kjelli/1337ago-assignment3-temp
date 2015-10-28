package documents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DocumentTermMatrix {
	private final Map<Document, Map<String, Integer>> matrix;
	private final HashSet<String> vocabulary;

	// Used for pretty printing
	private int longestTerm;

	public DocumentTermMatrix() {
		matrix = new HashMap<>();
		vocabulary = new HashSet<String>();
		longestTerm = 0;
	}

	public void addDocument(Document doc) {
		if (matrix.get(doc) == null) {
			matrix.put(doc, new HashMap<>(doc.getOccurences()));
			for (Entry<String, Integer> entry : matrix.get(doc).entrySet()) {
				vocabulary.add(entry.getKey());
				if (entry.getKey().length() > longestTerm) {
					longestTerm = entry.getKey().length();
				}
			}
		}
	}

	public double tf(Document doc, String term) {
		double docFreq = doc.getOccurences().get(term);
		double cumDocFreq = 0;
		for (Document d : getDocuments()) {
			cumDocFreq += d.getOccurences().get(term) == null ? 0 : d
					.getOccurences().get(term);
		}
		
		return docFreq / cumDocFreq;
	}
	
	public double idf(String term){
		
	}

	public Set<String> getVocabulary() {
		return vocabulary;
	}

	public Set<Document> getDocuments() {
		return matrix.keySet();
	}

	public Integer get(Document doc, String term) {
		return matrix.get(doc).get(term);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < longestTerm; i++) {
			sb.append(" ");
		}
		sb.append("\t");

		for (Document doc : matrix.keySet()) {
			sb.append(doc.getName() + "\t");
		}

		sb.append("\n");

		for (String term : vocabulary) {
			sb.append(term);
			for (int i = 0; i < longestTerm - term.length(); i++) {
				sb.append(" ");
			}
			sb.append("\t");
			for (Document doc : matrix.keySet()) {
				sb.append(matrix.get(doc).get(term) + "\t");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}
