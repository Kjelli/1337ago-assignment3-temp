package queries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author 1337ago
 */
public class QueryReader {

	public static Query read(File queryFile) throws IOException {
		FileReader fr = new FileReader(queryFile);
		BufferedReader br = new BufferedReader(fr);
		Integer id = Integer.parseInt(br.readLine());
		Integer wordCount = Integer.parseInt(br.readLine());

		Map<String, Integer> terms = new HashMap<>();

		String entry = null;
		while ((entry = br.readLine()) != null) {
			String[] elements = entry.split("\t");
			terms.put(elements[0], Integer.parseInt(elements[1]));
		}

		Query query = new Query(id, terms, wordCount);
		br.close();

		return query;
	}

	public static List<Query> readAll(File queryFolder) throws IOException {
		List<Query> queries = new ArrayList<Query>();
		File[] queryFiles = queryFolder.listFiles();
		for (File queryFile : queryFiles) {
			queries.add(read(queryFile));
		}
		return queries;
	}
}
