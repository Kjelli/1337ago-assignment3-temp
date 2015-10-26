package queries;
	import java.util.*;
	import java.io.File;
	import java.io.FileNotFoundException;
		
public class QueryReader {
	public QueryReader(){};
	public static List<UnprocessedQuery> readQuery(File file)throws FileNotFoundException{
	
		ArrayList<UnprocessedQuery> queryList = new ArrayList<UnprocessedQuery>();
							 
		//Reads queries from file and puts them in a query array.
		Scanner scan = new Scanner(file);
		String line;
		line = scan.nextLine();
		if (!line.equalsIgnoreCase("<queries>")){
			System.out.println("not a query file");
		}
		else{
			while (scan.hasNext()){
				line = scan.nextLine();
				if (line.equalsIgnoreCase("</Queries>")){
					break;
				}
					UnprocessedQuery rawQuery = new UnprocessedQuery();
					rawQuery.queries = line;
					queryList.add(rawQuery);
			}
		}
		scan.close();
		return queryList;	 
	}
}