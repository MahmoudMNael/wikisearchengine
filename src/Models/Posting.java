package Models;

public class Posting {
	private Posting next = null;
	private Integer documentId;
	private Integer termFrequency;
	
	public Posting (Integer documentId, Integer termFrequency) {
		this.documentId = documentId;
		this.termFrequency = termFrequency;
	}
	
	public Posting (Integer documentId) {
		this.documentId = documentId;
		this.termFrequency = 1;
	}
	
	public Posting getNext() {
		return next;
	}
	
	public void setNext(Posting next) {
		this.next = next;
	}
	
	public Integer getDocumentId() {
		return documentId;
	}
	
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	
	public Integer getTermFrequency() {
		return termFrequency;
	}
	
	public void setTermFrequency(Integer termFrequency) {
		this.termFrequency = termFrequency;
	}
	
	public void incrementTermFrequency(Integer value) {
		this.termFrequency += value;
	}
}
