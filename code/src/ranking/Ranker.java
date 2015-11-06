package ranking;

import java.util.List;

import queries.Query;
import documents.Document;

public interface Ranker {
	DocumentQueryResult bm25score(Query q, List<Document> documents);
	//DocumentQueryResult rank(Query q, List<Document> documents);
}
