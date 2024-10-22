package test.processing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import processing.DocumentProcessor;
import documents.Document;
import documents.RawDocument;

public class DocumentProcessorTest {

	Document doc;
	RawDocument updoc;

	@Before
	public void setUp() {
		try {
			String html = new String(Files.readAllBytes(Paths
					.get("bitesize/sample000.html")), StandardCharsets.UTF_8);
			updoc = new RawDocument("sample");
			updoc.setRawHTML(html);
			doc = DocumentProcessor.getInstance().process(updoc);
		} catch (IOException e) {
			throw new AssertionError(
					"Test failed; could not read sample html file");
		}
	}

	@Test
	public void existenceTest() {
		assertNotNull("Document should exist", doc);
	}

	@Test
	public void namePersistenceTest() {
		assertSame("Name of the document should persist", updoc.getName(),
				doc.getName());
	}

	@Test
	public void termFrequencyMapExistenceTest() {
		assertNotNull("Term-frequency map should exist", doc.getOccurenceMap());
	}

	@Test
	public void termFrequencyMapNotEmptyTest() {
		assertTrue("Term-frequency map should have entries", doc
				.getOccurenceMap().size() > 0);
	}

	@Test
	public void termFrequencyMapStopwordTest() {

		assertTrue("Term-frequency map should not contain stopwords", doc
				.getOccurenceMap().get("is") == null);
	}

	@Test
	public void termFormatTest() {

		for (Entry<String, Integer> entry : doc.getOccurenceMap().entrySet()) {
			assertTrue("Entries im the term-frequency map should be lowercase",
					entry.getKey().toLowerCase().equals(entry.getKey()));
			assertTrue("Entries in the term-frequency map should not be empty",
					!entry.getKey().isEmpty());
			assertTrue(
					"Entries in the term-frequency should have non-zero positive values",
					entry.getValue() > 0);
		}

	}
}
