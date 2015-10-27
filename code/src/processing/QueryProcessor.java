	package queries;

import java.util.Arrays;

public class QueryProcessor {
	private static String regex = "[ \\\\\\-\\\n\r\"\t<>+*”“’&©{}\\[\\]%~#=$•·—– ,.?!'|();:/]+| ";
	public QueryProcessor(){};
	TermProcessor termProc = TermProcessor.getInstance();
	//makes the unprocessed query into a processed one
	public static Query process(UnprocessedQuery rawQuery){
		String[] terms = rawQuery.queries.split(regex);
		Query query = new Query();
		for (int i = 0; i < terms.length-1; i++){
			if (i == 3) query.id = Integer.parseInt(terms[i]);
			if (i > 3) {
				if (!termProc.isStopWord(terms[i])){
				query.terms.add(terms[i]);
				}
			}
		}
		return query;
	}
}
