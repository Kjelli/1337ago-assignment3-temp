package queries;

import java.util.*;
/**
 * 
 * @author 1337ago
 */
public class Query {
	private final Map<String, Integer> terms;
	private final int wordCount;
	private final int id;

	public Query(int id, Map<String, Integer> terms, int wordCount) {
		this.id = id;
		this.terms = terms;
		this.wordCount = wordCount;
	}
	
	public double tf(String term){
		return terms.get(term) == null ? 0 : terms.get(term) * 1.0f / wordCount;
	}
	
	@Override
	public String toString() {
		return "Query " + id;
	}
	
	public Map<String, Integer> getTerms() {
		return terms;
	}
	
	public int getWordCount() {
		return wordCount;
	}
	
	public int getId() {
		return id;
	}
}
