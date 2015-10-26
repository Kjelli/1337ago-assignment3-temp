package documents;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.Jsoup;

/**
 * TODO To be implemented
 * @author Fredrik Wæhre Severinsen
 */
public class UnprocessedDocument {
	String name;
	ArrayList<String> rawHtml;
	String body;
	
	public UnprocessedDocument(String name){
		this.name = name;
		body = "";
		rawHtml = new ArrayList<String>();
	}
	
	public void addLine(String line){
		rawHtml.add(line);
	}
	
	public String buildBodyText(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<rawHtml.size();i++){
			sb.append(rawHtml.get(i));
		}
		Document jdoc = Jsoup.parse(new String(sb));
		body = jdoc.body().text();
		//System.out.println(body);
		return body;
	}
}


