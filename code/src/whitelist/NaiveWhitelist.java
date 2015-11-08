package whitelist;
/**
 * 
 * @author 1337ago
 */
public class NaiveWhitelist implements Whitelist{

	@Override
	public boolean contains(String term) {
		return true;
	}

}
