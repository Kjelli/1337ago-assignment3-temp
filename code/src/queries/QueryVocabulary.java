package queries;

import java.util.HashMap;

public class QueryVocabulary {
	private HashMap<String, Boolean> vocabulary;

	public QueryVocabulary() {
		vocabulary = new HashMap<String, Boolean>();
	}
	
	public void addQueryTerms(Query c){
		for(String term : c.getTerms().keySet()){
			vocabulary.put(term, true);
		}
	}
	
	public HashMap<String, Boolean> getVocabulary() {
		return vocabulary;
	}

	public boolean contains(String term) {
		return vocabulary.containsKey(term);
	}
}
