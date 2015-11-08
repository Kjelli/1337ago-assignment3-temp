package test.ranking;

/*import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import processing.QueryProcessor;
import documents.Document;
import documents.DocumentOccurenceMap;
import documents.DocumentReader;
import queries.Query;
import queries.RawQuery;
import ranking.DocumentQueryResult;
import ranking.Ranker;
import ranking.SimpleRanker;
import similarity.CosineSimilarity;
import static org.junit.Assert.*;

public class RankTest {
	static Query q;
	static Document doc1, doc2, doc3;
	static String fileLocationString = "output/preprocessed/test";
	static Ranker ranker;

	@BeforeClass
	public static void setup() {
		String header = "a b c 1 ";
		String trail = " d";
		String queries = "energi ocean";

		RawQuery rq = new RawQuery();
		rq.queries = header + queries + trail;
		q = QueryProcessor.process(rq);

		String sampleQuery = queries.split(" ")[0];
		assertTrue(q.getTerms().containsKey(sampleQuery));

		try {
			doc1 = DocumentReader.read(new File(fileLocationString + "/1.txt"));
			doc2 = DocumentReader.read(new File(fileLocationString + "/2.txt"));
			doc3 = DocumentReader.read(new File(fileLocationString + "/3.txt"));
		} catch (IOException e) {
			throw new AssertionError();
		}

		assertNotNull(doc1);
		assertNotNull(doc2);

		DocumentOccurenceMap dom;
		try {
			dom = DocumentOccurenceMap.fromFile(new File(fileLocationString
					+ "/dom.txt"));
		} catch (IOException e) {
			throw new AssertionError();
		}
		ranker = new SimpleRanker(dom, new CosineSimilarity());
	}

	@Test
	public void shouldGiveDifferentRank() {
		System.out.println("\n\nShould give different rank:\n\n");
		List<Document> list = new ArrayList<>();
		list.add(doc1);
		list.add(doc2);
		DocumentQueryResult ranks = ranker.rank(q, list);
		
		double rank1 = ranks.getRankMap().get(doc1);
		double rank2 = ranks.getRankMap().get(doc2);

		double difference = Math.abs(rank1 - rank2);
		System.out.println("Difference should be > 0");
		System.out.printf("\nRank 1: %.4f\tRank 2: %.4f\tDifference: %.4f\n", rank1, rank2, difference);

		assertTrue("Difference should be greater than small epsilon",
				difference > 0.000000001f);
	}

	@Test
	public void shouldGiveSameRank() {
		System.out.println("\n\nShould give same rank:\n\n");
		List<Document> list = new ArrayList<>();
		list.add(doc2);
		list.add(doc3);
		DocumentQueryResult ranks = ranker.rank(q, list);

		double rank1 = ranks.getRankMap().get(doc2);
		double rank2 = ranks.getRankMap().get(doc3);

		double difference = Math.abs(rank1 - rank2);
		System.out.println("Difference should be ~= 0");
		System.out.printf("\nRank 1: %.4f\tRank 2: %.4f\tDifference: %.4f\n", rank1, rank2, difference);
		assertTrue("Difference should be smaller than small epsilon",
				difference < 0.000000001f);
	}

	@AfterClass
	public static void teardown() {

	}
}*/
