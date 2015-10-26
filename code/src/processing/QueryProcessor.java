	package processing;

import java.util.Arrays;

public class QueryProcessor {
	private static String regex = "[ \\\\\\-\\\n\r\"\t<>+*”“’&©{}\\[\\]%~#=$•·—– ,.?!'|();:/]+| ";
	public QueryProcessor(){};
	//makes the unprocessed query into a processed one
	public static Query process(UnprocessedQuery rawQuery){
		String[] terms = rawQuery.queries.split(regex);
		System.out.println(Arrays.toString(terms));
		Query query = new Query(terms.length-5);
		for (int i = 0; i < terms.length-1; i++){
			if (i == 3) query.id = Integer.parseInt(terms[i]);
			if (i > 3) query.terms[i-4] = terms[i];
		}
		return query;
	}
}
