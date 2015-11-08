package processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import whitelist.NaiveWhitelist;
import whitelist.Whitelist;
import documents.Document;
import documents.RawDocument;
/**
 * 
 * @author 1337ago
 */
public class DocumentProcessor {

	private static final String regex = "[ \\\\\\-\\\n\r\"\t<>+*”“’&©{}\\[\\]%~#=$•·—– ,.?!'|();:/]+";
	private static DocumentProcessor INSTANCE;

	private Whitelist whitelist;

	private DocumentProcessor() {
		whitelist = new NaiveWhitelist();
	}

	/**
	 * Performs preprocessing on a document in order to retrieve the normalized
	 * term occurences.
	 * 
	 * @param updoc
	 *            The UnprocessedDocument object to process.
	 * @return A processed Document object.
	 */

	public Document process(RawDocument updoc) {
		Document result = null;

		TermProcessor termproc = TermProcessor.getInstance();
		Element body = Jsoup.parse(updoc.getRawHTML()).body();
		if (body == null) {
			return null;
		}
		String[] unprocessedTerms = body.text().split(regex);

		List<String> processedTerms = new ArrayList<>();

		for (String term : unprocessedTerms) {
			String processedTerm = termproc.process(term);
			if (processedTerm != null && !processedTerm.isEmpty()) {
				processedTerms.add(processedTerm);
			}
		}

		Map<String, Integer> termFrequencyMap = generateMap(whitelist,
				processedTerms);

		if (termFrequencyMap == null) {
			return null;
		}

		int wordCount = processedTerms.size();

		if (termFrequencyMap != null) {
			result = new Document(updoc.getName(), termFrequencyMap, wordCount);
		}
		return result;
	}

	/**
	 * Generates a term-frequency map from a string-array of terms.
	 * 
	 * @param whitelist
	 *            the allowed terms to accept
	 * @param terms
	 *            the string array of terms
	 * @return a term-frequency map, otherwise null if array is null or empty
	 */

	private Map<String, Integer> generateMap(Whitelist whitelist,
			List<String> terms) {
		if (terms == null || terms.size() == 0) {
			return null;
		}

		Map<String, Integer> result = new HashMap<>();
		for (String term : terms) {
			if (whitelist.contains(term)) {
				result.put(term,
						result.get(term) == null ? 1 : result.get(term) + 1);
			}
		}
		return result;
	}

	public void setWhitelist(Whitelist whitelist) {
		this.whitelist = whitelist;
	}

	public static DocumentProcessor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DocumentProcessor();
		}
		return INSTANCE;
	}
}
