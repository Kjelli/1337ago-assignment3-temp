package documents;

import java.util.Map;

/**
 * Class representing a processed document, containing the name of the document,
 * and the occurences of the terms within the document
 * 
 * @author Kjell Arne Hellum
 *
 */

public class Document {
	private final String name;
	private final Map<String, Integer> occurences;

	public Document(String name, Map<String, Integer> occurences) {
		this.name = name;
		this.occurences = occurences;
	}

	public String getName() {
		return name;
	}

	public Map<String, Integer> getOccurences() {
		return occurences;
	}
}
