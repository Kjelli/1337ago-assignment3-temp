package processing;

import java.util.HashMap;
import java.util.Map;

import queries.RawQuery;
import queries.Query;

public class QueryProcessor {
	private static String regex = "[ \\\\\\-\\\n\r\"\t<>+*”“’&©{}\\[\\]%~#=$•·—– ,.?!'|();:/]+| ";

	public QueryProcessor() {
	};

	// makes the unprocessed query into a processed one
	public Query process(RawQuery rawQuery) {
		TermProcessor termProc = TermProcessor.getInstance();
		String[] terms = rawQuery.queries.split(regex);
		String tempTerm;
		int wordCount = 0;
		int id = 0;
		Map<String, Integer> termMap = new HashMap<>();
		for (int i = 0; i < terms.length - 1; i++) {
			if (i == 3)
				id = Integer.parseInt(terms[i]);
			if (i > 3) {
				tempTerm = termProc.process(terms[i]);
				if (!tempTerm.equals("")) {
					wordCount++;
					termMap.put(tempTerm, termMap.get(tempTerm) == null ? 1
							: termMap.get(tempTerm) + 1);
				}
			}
		}
		Query query = new Query(id, termMap, wordCount);
		return query;
	}
}
