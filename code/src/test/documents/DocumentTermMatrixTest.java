package test.documents;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import processing.DocumentProcessor;
import documents.Document;
import documents.DocumentReader;
import documents.DocumentTermMatrix;
import documents.TFIDFDocumentTermMatrix;
import documents.UnprocessedDocument;

public class DocumentTermMatrixTest {

	static DocumentTermMatrix matrix;
	static List<Document> documents;

	@BeforeClass
	public static void setUp() {
		System.out.println("SETUP");
		matrix = new DocumentTermMatrix();

		DocumentReader reader = new DocumentReader();
		List<UnprocessedDocument> updocs = reader.readFile(new File(
				"bitesize/CSIRO000"));
		documents = new ArrayList<Document>();
		for (UnprocessedDocument updoc : updocs) {
			documents.add(DocumentProcessor.process(updoc));
		}
	}

	@Before
	public void beforeTests() {
		matrix.clear();
	}

	@Test
	public void documentAdditionTest() {
		matrix.addDocument(documents.get(0));
		String someTerm = documents.get(0).getOccurences().keySet().iterator()
				.next();
		assertNotNull(matrix.get(documents.get(0), someTerm));
	}

	@Test
	public void documentVocabularySizeTest() {
		int initialCount = matrix.getVocabulary().size();
		matrix.addDocument(documents.get(1));
		assertNotEquals(initialCount, matrix.getVocabulary().size());
	}

	@Test
	public void tfTest() {
		// If order of tests runs addition and vocabulary tests first, these
		// will already be added
		matrix.addDocument(documents.get(0));
		matrix.addDocument(documents.get(1));
		String someTerm = documents.get(0).getOccurences().keySet().iterator()
				.next();
		double tf = matrix.tf(documents.get(0), someTerm);
		assertNotEquals(0, tf);

	}

	@Test
	public void idfTest() {
		// If order of tests runs addition and vocabulary tests first, these
		// will already be added
		matrix.addDocument(documents.get(0));
		matrix.addDocument(documents.get(1));
		matrix.addDocument(documents.get(2));
		String someTerm = documents.get(0).getOccurences().keySet().iterator()
				.next();
		double idf = matrix.idf(someTerm);
		assertNotEquals(0.0f, idf);

	}

	@Test
	public void tfidfMapTest() {
		matrix.addDocuments(documents.get(0), documents.get(1),
				documents.get(2));

		TFIDFDocumentTermMatrix tfidfMap = matrix.generateTFIDFMap();

		// TODO figure what to test.
	}
}
