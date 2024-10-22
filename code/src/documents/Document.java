package documents;

import java.util.Map;

/**
 * Class representing a processed document, containing the name of the document,
 * and the occurences of the terms within the document
 * 
 * @author 1337ago
 *
 */

public class Document {
	private final String name;
	private final Map<String, Integer> occurences;
	private final int wordCount;

	public Document(String name, Map<String, Integer> occurences, int wordCount) {
		this.name = name;
		this.occurences = occurences;
		this.wordCount = wordCount;
	}

	public String getName() {
		return name;
	}

	public Integer getOccurence(String term) {
		Integer occurence = occurences.get(term);
		if (occurence == null) {
			return 0;
		} else {
			return occurence;
		}
	}

	public int getWordCount() {
		return wordCount;
	}

	@Override
	public String toString() {
		return name;
	}

	public Map<String, Integer> getOccurenceMap() {
		return occurences;
	}
}
