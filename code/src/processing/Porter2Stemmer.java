package processing;
/**
 * 
 * @author 1337ago
 */
public class Porter2Stemmer {

	private static Porter2Stemmer instance;
	private static stemmer.SnowballStemmer stemmer;

	/**
	 * Private construction only, disallows the construction of objects of this
	 * class in other contexts. Initiates the stemmer.
	 */

	private Porter2Stemmer() {
		 stemmer = new stemmer.ext.englishStemmer();
	}

	/**
	 * Implementation of Stemmer downloaded from
	 * http://snowball.tartarus.org/dist/libstemmer_java.tgz
	 * 
	 * @param term
	 *            the term to stem
	 * @return a stemmed version of the term.
	 */
	public String stemTerm(String term) {
		stemmer.setCurrent(term);
		stemmer.stem();
		return new String(stemmer.getCurrent());
	}

	/**
	 * Retrieves the instance of the singleton.
	 * 
	 * @return the PorterStemmer singleton instance
	 */
	public static Porter2Stemmer getInstance() {
		if (instance == null) {
			instance = new Porter2Stemmer();
		}
		return instance;
	}
}
