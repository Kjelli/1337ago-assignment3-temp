package similarity;

import java.util.List;

public interface SimilarityStrategy<T> {
	public double similarity(List<T> t1, List<T> t2);
}
