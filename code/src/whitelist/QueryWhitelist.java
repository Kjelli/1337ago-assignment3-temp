package whitelist;

import queries.QueryVocabulary;

public class QueryWhitelist implements Whitelist {

	private final QueryVocabulary vocabulary;

	public QueryWhitelist(QueryVocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	@Override
	public boolean contains(String term) {
		return vocabulary.contains(term);
	}

}