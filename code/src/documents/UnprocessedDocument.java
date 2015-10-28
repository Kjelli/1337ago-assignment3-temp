package documents;

import java.util.ArrayList;

/**
 * 
 * @author Fredrik Wæhre Severinsen
 */
public class UnprocessedDocument {
	String name;
	ArrayList<String> lineArrayHTML;
	String rawHTML;
	
	public UnprocessedDocument(String name){
		this.name = name;
		rawHTML = "";
		lineArrayHTML = new ArrayList<String>();
	}
	
	public void addLine(String line){
		lineArrayHTML.add(line);
	}
	
	public void buildHtmlString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<lineArrayHTML.size();i++){
			sb.append(lineArrayHTML.get(i));
		}
		rawHTML = new String(sb);
	}

	public String getName() {
		return name;
	}
	
	public String getRawHTML() {
		return rawHTML;
	}
	public void setRawHTML(String html){
		rawHTML = html;
	}
}


