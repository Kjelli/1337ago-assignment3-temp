package queries;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
	/**
	 * 
	 * @author 1337ago
	 */
public class RawQueryReader {
	public RawQueryReader(){};
	public List<RawQuery> readQueries(File file)throws FileNotFoundException{
	
		ArrayList<RawQuery> queryList = new ArrayList<RawQuery>();
							 
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
					RawQuery rawQuery = new RawQuery();
					rawQuery.queries = line;
					queryList.add(rawQuery);
			}
		}
		scan.close();
		return queryList;	 
	}
}