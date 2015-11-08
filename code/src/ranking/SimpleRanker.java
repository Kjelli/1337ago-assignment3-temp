
package ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import queries.Query;
import utils.MapUtils;
import documents.Document;
import documents.DocumentOccurenceMap;
/**
 * 
 * @author 1337ago
 */
public class SimpleRanker implements Ranker {

	private final DocumentOccurenceMap dom;

	public SimpleRanker(DocumentOccurenceMap dom) {
		this.dom = dom;
	}
	@Override
	public DocumentQueryResult bm25score(Query q, List<Document> documents){
		List<Double> queryIdfs = new ArrayList<>();
		double avgdl = 0;
		double k = 1.0;
		double b = 0.5;
		double delta = 0.7;
		double wordcount = 0;

		for (Entry<String, Integer> entry : q.getTerms().entrySet()) {
			double idf = 0;
			if (dom.getOccurences(entry.getKey()) > 0) {
				idf =  Math.log10((1 + dom.getDocumentCount() * 1.0f)
						/ (0.5 + dom.getOccurences(entry.getKey())));
				queryIdfs.add(idf);
			}
		}

		Map<Document, Double> rankMap = new HashMap<Document, Double>();
		for (Document doc : documents) {
			wordcount += doc.getWordCount();
		}
		avgdl = wordcount/documents.size();

		for (Document doc : documents) {

			double score = 0;
			int index = 0;
			for (String term : q.getTerms().keySet()) {
				double documentTermTF = doc.getOccurence(term) * 1.0f
						/ doc.getWordCount();
				if(index<queryIdfs.size()){
					score += queryIdfs.get(index)*(1+Math.log((1+Math.log((documentTermTF*(k+1))/(documentTermTF+k)*(1-b+(b*(documents.size()/avgdl)))+delta))));
					index++;
				}
				if (Double.isNaN(score) || Double.isInfinite(score)) {
					score = 0;
				}
			}
			if (score > 0) {
				rankMap.put(doc, score);
			}
		}

		rankMap = MapUtils.sort(rankMap, true);

		// Get top 50 values;
		int trim = 50;
		int omitted = 0;
		for (Iterator<Double> it = rankMap.values().iterator(); it.hasNext();) {
			if (trim > 0) {
				it.next();
				trim--;
			} else {
				it.next();
				it.remove();
				omitted++;
			}
		}

		DocumentQueryResult result = new DocumentQueryResult(q.getId(),
				rankMap, omitted);

		return result;
	}
}

