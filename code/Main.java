package assignment3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		File file = new File("csiro-corpus/CSIRO000");
		Scanner scan = new Scanner(file);
		ArrayList<Document> docs = new ArrayList<Document>();
		int index = -1;
		boolean htmlFlag = false;
		//printLines(1000); //printer valgt antall linjer fra input fila
		while(scan.hasNext()){
			String nxl = scan.nextLine();

			if(nxl.equalsIgnoreCase("<DOC>")){
				String unTrimmedHeader = scan.nextLine();
				docs.add(new Document(unTrimmedHeader));
				System.out.println("created new doc with header: "+ unTrimmedHeader);
				index++;
			}
			else if(nxl.equalsIgnoreCase("</DOCHDR>")){
				htmlFlag = true;
			}
			else if(nxl.equalsIgnoreCase("</DOC>")){
				htmlFlag = false;
				System.out.println("Doc completed");
			}
			if(htmlFlag){
				docs.get(index).addLine(nxl);
			}
		}
		//docs.get(0).printHtml(); //Printer et dokument
	}
	private static void printLines(int nrOfLines) throws IOException{
		String[] lines = readFile("csiro-corpus/CSIRO000", StandardCharsets.UTF_8).split(System.getProperty("line.separator"));
		ArrayList<Document> docs = new ArrayList<Document>();
		int index = -1;
		for(int i=0; i<nrOfLines; i++){
			System.out.println(lines[i]);
		}
	}
	private static String readFile(String path, Charset encoding) 
			throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
