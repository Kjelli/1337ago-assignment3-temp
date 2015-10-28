package queries;

import java.util.*;

public class Query {
	public ArrayList<String> terms;
	public int id;

	public Query() {
		terms = new ArrayList<String>();
	}
	
	public double tf(String term){
		double count = 0;
		for(String s : terms){
			if(s.equals(term)){
				count++;
			}
		}
		return count/terms.size();
	}
}
