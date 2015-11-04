package ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import queries.Query;
import similarity.SimilarityStrategy;
import utils.MapUtils;
import documents.Document;
import documents.DocumentOccurenceMap;

public class SimpleRanker implements Ranker {

	private final DocumentOccurenceMap dom;
	private final SimilarityStrategy<Double> simstrat;

	public SimpleRanker(DocumentOccurenceMap dom,
			SimilarityStrategy<Double> simstrat) {
		this.dom = dom;
		this.simstrat = simstrat;
	}

	@Override
	public DocumentQueryResult rank(Query q, List<Document> documents) {
		List<Double> queryValues = new ArrayList<>();
		List<Double> documentValues = new ArrayList<>();

		for (Entry<String, Integer> entry : q.getTerms().entrySet()) {
			double queryTermTF = q.tf(entry.getKey());
			double idf = 0;
			if (dom.getOccurences(entry.getKey()) > 0) {
				idf = Math.log10(dom.getDocumentCount() * 1.0f
						/ dom.getOccurences(entry.getKey()));
				queryValues.add(queryTermTF * idf);
			}
		}

		Map<Document, Double> rankMap = new HashMap<Document, Double>();

		for (Document doc : documents) {

			documentValues.clear();

			for (String term : q.getTerms().keySet()) {
				double documentTermTF = doc.getOccurence(term) * 1.0f
						/ doc.getWordCount();
				double idf = Math.log10(dom.getDocumentCount() * 1.0f
						/ dom.getOccurences(term));

				documentValues.add(documentTermTF * idf);
			}
			double result = 1-simstrat
					.similarity(queryValues, documentValues);
			if (Double.isNaN(result)) {
				result = 0;
			}
			if (result > 0) {
				rankMap.put(doc, result);
			}
		}

		rankMap = MapUtils.sort(rankMap, true);

		// Get top 50 values;
		int trim = rankMap.size() - 50;
		int omitted = 0;
		for (Iterator<Double> it = rankMap.values().iterator(); it.hasNext();) {
			if (trim <= 0) {
				break;
			} else if (trim > 0) {
				it.next();
				it.remove();
				trim--;
				omitted++;
			} 
		}

		DocumentQueryResult result = new DocumentQueryResult(q.getId(), rankMap,
				omitted);

		return result;
	}

}
