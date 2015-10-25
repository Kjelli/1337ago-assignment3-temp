package processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

import documents.Document;
import documents.UnprocessedDocument;

public class DocumentProcessor {

	private static String regex = "[ \\\\\\-\\\n\r\"\t<>+*”“’&©{}\\[\\]%~#=$•·—– ,.?!'|();:/]+";

	/**
	 * Performs preprocessing on a document in order to retrieve the normalized
	 * term occurences.
	 * 
	 * @param updoc
	 *            The UnprocessedDocument object to process.
	 * @return A processed Document object.
	 */
	public static Document process(UnprocessedDocument updoc) {
		Document result = null;

		TermProcessor termproc = TermProcessor.getInstance();

		String[] unprocessedTerms = Jsoup.parse(updoc.getRawHTML()).body()
				.text().split(regex);

		List<String> processedTerms = new ArrayList<>();

		for (String term : unprocessedTerms) {
			String processedTerm = termproc.process(term);
			if (processedTerm != null && !processedTerm.isEmpty()) {
				processedTerms.add(processedTerm);
			}
		}

		Map<String, Integer> termFrequencyMap = generateMap(processedTerms);
		if (termFrequencyMap != null) {
			result = new Document(updoc.getName(), termFrequencyMap);
		}
		return result;
	}

	/**
	 * Generates a term-frequency map from a string-array of terms.
	 * 
	 * @param terms
	 *            the string array of terms
	 * @return a term-frequency map, otherwise null if array is null or empty
	 */
	private static Map<String, Integer> generateMap(List<String> terms) {
		if (terms == null || terms.size() == 0) {
			return null;
		}

		Map<String, Integer> result = new HashMap<>();
		for (String term : terms) {
			result.put(term, result.get(term) == null ? 1
					: result.get(term) + 1);
		}
		return result;
	}
}
