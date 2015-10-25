package test.processing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map.Entry;

import org.junit.Test;

import static org.junit.Assert.*;
import processing.DocumentProcessor;
import documents.Document;
import documents.UnprocessedDocument;

public class DocumentProcessorTest {

	@Test
	public void sampleDocumentProcessingTest() {
		try {
			// Sample html file to read
			String html = new String(Files.readAllBytes(Paths
					.get("bitesize/sample000.html")), StandardCharsets.UTF_8);
			UnprocessedDocument updoc = new UnprocessedDocument("sample", html);

			// Processing the sample
			Document doc = DocumentProcessor.process(updoc);

			assertNotNull("Document should exist", doc);

			assertSame("Name of the document should persist", updoc.getName(),
					doc.getName());

			assertNotNull("Term-frequency map should exist",
					doc.getOccurences());

			assertTrue("Term-frequency map should have entries", doc
					.getOccurences().size() > 0);

			assertTrue("Term-frequency map should not contain stopwords", doc
					.getOccurences().get("is") == null);

			for (Entry<String, Integer> entry : doc.getOccurences().entrySet()) {
				assertTrue(
						"Entries im the term-frequency map should be lowercase",
						entry.getKey().toLowerCase().equals(entry.getKey()));
				assertTrue(
						"Entries in the term-frequency map should not be empty",
						!entry.getKey().isEmpty());
				assertTrue(
						"Entries in the term-frequency should have non-zero positive values",
						entry.getValue() > 0);
				// Print occurences for visual confirmation
				System.out.println(entry);
			}

		} catch (IOException e) {
			throw new AssertionError(
					"Test failed; could not read sample html file");
		}
	}
}
