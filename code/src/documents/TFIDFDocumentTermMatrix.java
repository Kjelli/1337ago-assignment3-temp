package documents;

import java.util.Map;

public class TFIDFDocumentTermMatrix {
	private final Map<Document, Map<String, Double>> matrix;
	
	public TFIDFDocumentTermMatrix(Map<Document, Map<String, Double>> matrix){
		this.matrix = matrix;
	}
}
