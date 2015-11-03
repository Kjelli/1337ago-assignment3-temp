package whitelist;

public class NaiveWhitelist implements Whitelist{

	@Override
	public boolean contains(String term) {
		return true;
	}

}
