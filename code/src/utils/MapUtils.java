package utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import documents.Document;

public class MapUtils {
	// Sort a map by the values.
	public static Map<Document, Double> sort(Map<Document, Double> map,
			boolean ascending) {

		List<Entry<Document, Double>> entries = new LinkedList<Entry<Document, Double>>(
				map.entrySet());

		Collections.sort(entries, new Comparator<Entry<Document, Double>>() {
			@Override
			public int compare(Entry<Document, Double> entry1,
					Entry<Document, Double> entry2) {
				return (ascending ? 1 : -1)
						* entry2.getValue().compareTo(entry1.getValue());
			}
		});

		Map<Document, Double> sorted = new LinkedHashMap<Document, Double>();

		for (Entry<Document, Double> entry : entries) {
			sorted.put(entry.getKey(), entry.getValue());
		}
		return sorted;
	}

}
