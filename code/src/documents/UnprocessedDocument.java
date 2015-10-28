package documents;


/**
 * 
 * @author Fredrik Wæhre Severinsen
 */
public class UnprocessedDocument {
	String name;
	StringBuilder sb;
	String rawHTML;
	
	public UnprocessedDocument(String name){
		this.name = name;
		rawHTML = "";
		sb = new StringBuilder();
	}
	
	public void addLine(String line){
		sb.append(line);
	}
	
	public void buildHtmlString(){
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


