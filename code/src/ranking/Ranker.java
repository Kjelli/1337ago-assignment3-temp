package ranking;

import java.util.List;

import queries.Query;
import documents.Document;
/**
 * 
 * @author 1337ago
 */
public interface Ranker {
	DocumentQueryResult bm25score(Query q, List<Document> documents);
	//DocumentQueryResult rank(Query q, List<Document> documents);
}
