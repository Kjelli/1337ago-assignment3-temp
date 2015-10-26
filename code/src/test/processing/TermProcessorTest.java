package test.processing;

import org.junit.Test;

import processing.TermProcessor;
import static org.junit.Assert.*;

public class TermProcessorTest {

	@Test
	public void termProcessingTest() {
		TermProcessor termproc = TermProcessor.getInstance();

		String term = "climbing";
		String expected = "climb";
		String stem = termproc.process(term);
		
		assertNotSame("Term and processed term should not be the same", term,
				stem);
		assertEquals("The expected stem and the actual stem should be equal",
				expected, stem);
	}
}
