package documents;
/**
 * 
 * @author 1337ago
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RawDocumentReader {
	Scanner scan;

	public RawDocumentReader(){
	}

	public ArrayList<RawDocument> readFile(File file){
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<RawDocument> docs = new ArrayList<RawDocument>();
		int index = -1;
		boolean htmlFlag = false;
		while(scan.hasNext()){
			String nxl = scan.nextLine();

			if(nxl.equalsIgnoreCase("<DOC>")){
				String unTrimmedName = scan.nextLine();
				docs.add(new RawDocument(trimName(unTrimmedName)));
				index++;
			}
			else if(nxl.equalsIgnoreCase("</DOCHDR>")){
				nxl = scan.next();
				htmlFlag = true;
			}
			else if(nxl.equalsIgnoreCase("</DOC>")){
				htmlFlag = false;
				docs.get(index).buildHtmlString();
			}
			if(htmlFlag){
				docs.get(index).addLine(nxl);
			}
		}
		return docs;
	}
	private String trimName(String name){
		return name.replaceAll("<DOCNO>", "").replaceAll("</DOCNO>", "");
		
	}
}
