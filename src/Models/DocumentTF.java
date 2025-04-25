package Models;

public class DocumentTF {
	private Integer documentId; // Document ID
	private Double tfValue; // Term Frequency in the document
	
	public DocumentTF(Integer documentId, Double tfValue) {
		this.documentId = documentId;
		this.tfValue = tfValue;
	}
	
	public Integer getDocumentId() {
		return documentId;
	}
	
	public Double getTFValue() {
		return tfValue;
	}
}