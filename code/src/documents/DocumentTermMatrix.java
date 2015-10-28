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
	
	public void addDocuments(Document...docs){
		for(Document d : docs){
			addDocument(d);
		}
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
		double docFreq = doc.getOccurences().get(term) == null ? 0 : doc.getOccurences().get(term);
		return docFreq / doc.getWordCount();
	}

	public double idf(String term) {
		Set<Document> documents = getDocuments();

		double documentCount = documents.size();
		double cumTermCount = 0;

		for (Document doc : documents) {
			if (doc.getOccurences().get(term) != null) {
				cumTermCount++;
			}
		}
		return Math.log10(documentCount / cumTermCount);
	}

	public TFIDFDocumentTermMatrix generateTFIDFMap() {
		Map<Document, Map<String, Double>> tfidfMap = new HashMap<Document, Map<String, Double>>();
		for (Document d : getDocuments()) {
			tfidfMap.put(d, new HashMap<String, Double>());
			for (String t : getVocabulary()) {
				tfidfMap.get(d).put(t, tf(d, t) * idf(t));
			}
		}
		return new TFIDFDocumentTermMatrix(tfidfMap);
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

	public void clear() {
		vocabulary.clear();
		matrix.clear();
	}

}
