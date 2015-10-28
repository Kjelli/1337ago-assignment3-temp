package queries;
import queries.UnprocessedQuery;
import queries.Query;

public class QueryProcessor {
	private static String regex = "[ \\\\\\-\\\n\r\"\t<>+*”“’&©{}\\[\\]%~#=$•·—– ,.?!'|();:/]+| ";
	public QueryProcessor(){};
	
	//makes the unprocessed query into a processed one
	public static Query process(UnprocessedQuery rawQuery){
		TermProcessor termProc = TermProcessor.getInstance();
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
