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
		double result = count/terms.size();
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String s : terms){
			sb.append(s+ " ");
		}
		return sb.toString();
	}
}
