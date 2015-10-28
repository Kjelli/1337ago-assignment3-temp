package documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import queries.Query;
import similarity.SimilarityStrategy;

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

	public Set<Document> getDocuments() {
		return matrix.keySet();
	}

	public Map<String, Double> getIDFMap() {
		return IDFMap;
	}

	public Map<Document, Double> runQuery(Query q,
			SimilarityStrategy<Double> strat) {
		ArrayList<Double> weightedQueryVector = new ArrayList<>();
		ArrayList<Double> weightedDocumentVector = new ArrayList<>();
		Map<Document, Double> similarityMap = new HashMap<>();
		for (Document d : getDocuments()) {
			weightedQueryVector.clear();
			weightedDocumentVector.clear();
			for (String term : vocabulary) {
				weightedQueryVector.add(IDFMap.get(term) * q.tf(term));
				weightedDocumentVector.add(matrix.get(d).get(term));
			}

			similarityMap.put(d, strat.similarity(weightedQueryVector,
					weightedDocumentVector));

		}
		return similarityMap;

	}
}
