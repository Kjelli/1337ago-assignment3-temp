package processing;

/**
 * 
 * @author Kjell Arne Hellum
 *
 */
public class PorterStemmer {

	private static PorterStemmer instance;
	private static Stemmer stemmer;

	/**
	 * Private construction only, disallows the construction of objects of this
	 * class in other contexts. Initiates the stemmer.
	 */
	private PorterStemmer() {
		stemmer = new Stemmer();
	}

	/**
	 * Implementation of Stemmer downloaded from
	 * http://tartarus.org/martin/PorterStemmer/java.txt
	 * 
	 * @param term
	 *            the term to stem
	 * @return a stemmed version of the term.
	 */
	public String stemTerm(String term) {
		stemmer.add(term.toCharArray(), term.length());
		stemmer.stem();
		return new String(stemmer.getResultBuffer(), 0,
				stemmer.getResultLength());
	}

	/**
	 * Retrieves the instance of the singleton.
	 * 
	 * @return the PorterStemmer singleton instance
	 */
	public static PorterStemmer getInstance() {
		if (instance == null) {
			instance = new PorterStemmer();
		}
		return instance;
	}
}
