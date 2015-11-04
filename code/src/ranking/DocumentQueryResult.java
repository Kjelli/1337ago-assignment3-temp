package ranking;

import java.util.Map;

import documents.Document;

public class DocumentQueryResult implements Comparable<DocumentQueryResult> {
	private final int queryId;
	private final Map<Document, Double> rankMap;
	private final int omitted;

	public DocumentQueryResult(int queryId, Map<Document, Double> rankMap,
			int omitted) {
		this.queryId = queryId;
		this.rankMap = rankMap;
		this.omitted = omitted;
	}

	public int getQueryId() {
		return queryId;
	}

	public Map<Document, Double> getRankMap() {
		return rankMap;
	}

	public int getOmittedCount() {
		return omitted;
	}

	@Override
	public int compareTo(DocumentQueryResult that) {
		return Integer.compare(this.queryId, that.queryId);
	}

}
