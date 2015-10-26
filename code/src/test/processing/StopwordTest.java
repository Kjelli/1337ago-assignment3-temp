package test.processing;

import org.junit.Test;

import processing.TermProcessor;
import static org.junit.Assert.*;

public class StopwordTest {

	@Test
	public void stopwordPopulationTest() {
		TermProcessor termproc = TermProcessor.getInstance();

		assertTrue("Stopword 'and' should be recognized",
				termproc.isStopWord("and"));
		assertFalse("Non-stopword 'cola' should not be recognized ",
				termproc.isStopWord("cola"));
	}
}
