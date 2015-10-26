package documents;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DocumentReader {
	Scanner scan;

	public DocumentReader(){
	}

	public ArrayList<UnprocessedDocument> readFile(File file){
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<UnprocessedDocument> docs = new ArrayList<UnprocessedDocument>();
		int index = -1;
		boolean htmlFlag = false;
		while(scan.hasNext()){
			String nxl = scan.nextLine();

			if(nxl.equalsIgnoreCase("<DOC>")){
				String unTrimmedHeader = scan.nextLine();
				docs.add(new UnprocessedDocument(trimName(unTrimmedHeader)));
				index++;
			}
			else if(nxl.equalsIgnoreCase("</DOCHDR>")){
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
