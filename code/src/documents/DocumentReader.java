package documents;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DocumentReader {
	Scanner scan;
	File file;

	public DocumentReader(){
	}

	public ArrayList<UnprocessedDocument> readFile(String path){
		file = new File(path);
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
				docs.add(new UnprocessedDocument(unTrimmedHeader));
				//System.out.println("created new doc with header: "+ unTrimmedHeader);
				index++;
			}
			else if(nxl.equalsIgnoreCase("</DOCHDR>")){
				htmlFlag = true;
			}
			else if(nxl.equalsIgnoreCase("</DOC>")){
				htmlFlag = false;
				System.out.println("Doc completed");
				docs.get(index).buildBodyText();
			}
			if(htmlFlag){
				docs.get(index).addLine(nxl);
			}
		}
		return docs;
	}
}
