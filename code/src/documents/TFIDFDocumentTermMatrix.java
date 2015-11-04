//package documents;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import queries.Query;
//import ranking.DocumentQueryResult;
//import similarity.SimilarityStrategy;
//import utils.MapUtils;
//
//@Deprecated
//public class TFIDFDocumentTermMatrix {
//	private final Map<Document, Map<String, Double>> matrix;
//	private final Set<String> vocabulary;
//	private final Map<String, Double> IDFMap;
//
//	public TFIDFDocumentTermMatrix(Map<Document, Map<String, Double>> matrix,
//			Map<String, Double> IDFMap, Set<String> vocabulary) {
//		this.IDFMap = IDFMap;
//		this.matrix = matrix;
//		this.vocabulary = vocabulary;
//	}
//
//	public Map<Document, Map<String, Double>> getMatrix() {
//		return matrix;
//	}
//
//	public Set<String> getVocabulary() {
//		return vocabulary;
//	}
//
//	public Set<Document> getDocuments() {
//		return matrix.keySet();
//	}
//
//	public Map<String, Double> getIDFMap() {
//		return IDFMap;
//	}
//
//	public DocumentQueryResult runQuery(Query q,
//			SimilarityStrategy<Double> strat) {
//
//		ArrayList<Double> weightedQueryVector = new ArrayList<>();
//		ArrayList<Double> weightedDocumentVector = new ArrayList<>();
//		Map<Document, Double> similarityMap = new HashMap<>();
//
//		// System.out.println("\n\n###\n\n");
//		// for (String t : q.terms) {
//		// System.out.println(t);
//		// }
//		// System.out.println("\n\n###\n\n");
//		for (Document d : getDocuments()) {
//
//			weightedQueryVector.clear();
//			weightedDocumentVector.clear();
//
//			for (String term : vocabulary) {
//				double queryValue = IDFMap.get(term) * q.getTerms().get(term);
//				weightedQueryVector.add(queryValue);
//
//				double documentValue = matrix.get(d).get(term);
//				weightedDocumentVector.add(documentValue);
//			}
//
//			double similarity = strat.similarity(weightedQueryVector,
//					weightedDocumentVector);
//			// Add results with any relevance above 0.
//			if (similarity > 0.0f) {
//				similarityMap.put(d, similarity);
//			}
//
//		}
//
//		similarityMap = MapUtils.sort(similarityMap, false);
//		// Get top 50 values;
//		int trim = 50;
//		int omitted = 0;
//		for (Iterator<Double> it = similarityMap.values().iterator(); it
//				.hasNext();) {
//			if (trim == 0) {
//				it.next();
//				it.remove();
//				omitted++;
//			} else {
//				it.next();
//				trim--;
//			}
//		}
//		DocumentQueryResult result = new DocumentQueryResult(q.id,
//				similarityMap, omitted);
//
//		return result;
//
//	}
//}
