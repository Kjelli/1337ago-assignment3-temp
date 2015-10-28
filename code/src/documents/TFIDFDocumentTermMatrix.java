package documents;

import java.util.Map;
import java.util.Set;

public class TFIDFDocumentTermMatrix {
	private final Map<Document, Map<String, Double>> matrix;
	private final Set<String> vocabulary;

	public TFIDFDocumentTermMatrix(Map<Document, Map<String, Double>> matrix,
			Set<String> vocabulary) {
		this.matrix = matrix;
		this.vocabulary = vocabulary;
	}
	
	public Map<Document, Map<String, Double>> getMatrix() {
		return matrix;
	}
	
	public Set<String> getVocabulary() {
		return vocabulary;
	}
}
