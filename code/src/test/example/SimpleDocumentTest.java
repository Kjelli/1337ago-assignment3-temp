package test.example;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

import documents.Document;

public class SimpleDocumentTest {
	@Test
	public void exampleTest() {

		Document d;

		// Dummy document name
		String name = "CSIRO666";

		// Consider the string "Hello World", make term occurence map
		Map<String, Integer> occurences = new HashMap<>();
		occurences.put("Hello", 1);
		occurences.put("World", 1);

		d = new Document(name, occurences, 2);

		/*
		 * Test if not null
		 */

		assertNotNull(d.getName());
		assertNotNull(d.getOccurenceMap());

		/*
		 * Equality testing
		 */

		// Object and string
		assertEquals(occurences, d.getOccurenceMap());
		assertEquals(name, d.getName());

		// Integer
		assertEquals(occurences.size(), d.getOccurenceMap().size());

		/*
		 * Condition testing
		 */

		assertTrue(d.getOccurenceMap().size() == 2);
		assertTrue(d.getName().length() > 0);
	}
}
