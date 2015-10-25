package documents;

/**
 * TODO To be implemented
 * @author Kjell Arne Hellum
 */
public class UnprocessedDocument {
	private final String name;
	private final String rawHTML;
	
	public UnprocessedDocument(String name, String rawHTML){
		this.name = name;
		this.rawHTML = rawHTML;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRawHTML() {
		return rawHTML;
	}

}
