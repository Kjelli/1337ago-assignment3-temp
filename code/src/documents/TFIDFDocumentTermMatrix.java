package documents;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import queries.Query;

public class TFIDFDocumentTermMatrix {
	private final Map<Document, Map<String, Double>> matrix;
	private final Set<String> vocabulary;
	private final Map<String, Double> IDFMap;

	public TFIDFDocumentTermMatrix(Map<Document, Map<String, Double>> matrix,
			Map<String, Double> IDFMap, Set<String> vocabulary) {
		this.IDFMap = IDFMap;
		this.matrix = matrix;
		this.vocabulary = vocabulary;
	}

	public Map<Document, Map<String, Double>> getMatrix() {
		return matrix;
	}

	public Set<String> getVocabulary() {
		return vocabulary;
	}

	public Map<String, Double> getIDFMap() {
		return IDFMap;
	}

	public void runQuery(Query q) {
		ArrayList<Double> weightedQueryVector = new ArrayList<>();
		ArrayList<Double> weightedDocumentVector = new ArrayList<>();

		for (String qTerm : q.terms) {
			weightedQueryVector.add(IDFMap.get(qTerm) * q.tf(qTerm));
		}

	}
}
