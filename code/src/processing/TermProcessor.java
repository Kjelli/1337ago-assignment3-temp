package processing;

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
public abstract class TermProcessor {

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
		/*
		 * TODO replace string with stemmed term, or empty string if stopword
		 */
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
		boolean result = false;
		/*
		 * TODO check if the term is stopword. (hint: maybe read in list of
		 * stopwords and store them in memory)
		 */
		return result;
	}
}
