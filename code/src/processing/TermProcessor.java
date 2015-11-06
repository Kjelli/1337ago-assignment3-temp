package processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO To be implemented.
 * 
 * @author Kjell Arne Hellum
 * 
 *         Author note: Maybe the documentprocessor and queryprocessor should
 *         use the same strategy for processing? That is, the stemming and
 *         stopword removal process should be identical in both cases.
 *
 */
public class TermProcessor {

	private static TermProcessor instance = null;

	private static String stopwordsFilePath = "stopwords/list.txt";
	private Map<String, Boolean> stopwords;

	private TermProcessor() {
		// Disallow construction of this class in other contexts.

		populateStopwords();
	}

	/**
	 * Populate the stopword map.
	 */
	private void populateStopwords() {
		try {
			FileReader read = new FileReader(new File(stopwordsFilePath));
			BufferedReader br = new BufferedReader(read);

			stopwords = new HashMap<String, Boolean>();

			String line;
			while ((line = br.readLine()) != null) {
				stopwords.put(line, true);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes a single term. Returns a (stemmed) term, or empty string if
	 * term is a stopword.
	 * 
	 * @param term
	 *            the term to process
	 * @return either a (stemmed) term, or an empty string if term is stopword
	 */

	public String process(String term) {
		String result = null;
		
		term = term.toLowerCase();

		if (isStopWord(term)) {
			result = "";
		} else {
			result = Porter2Stemmer.getInstance().stemTerm(term);
		}

		return result;
	}

	/**
	 * Determines wether term is a stopword or not.
	 * 
	 * @param term
	 *            the term to check against list of known stopwords
	 * @return boolean indicating if term is stopword
	 */

	public boolean isStopWord(String term) {
		return stopwords.get(term) != null;
	}

	public static TermProcessor getInstance() {
		if (instance == null) {
			instance = new TermProcessor();
		}
		return instance;
	}
}
