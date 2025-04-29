package Models;

public class PostingList {
	private Integer documentFrequency;
	private Integer termFrequency;
	private Posting head;
	private Posting tail;
	
	public PostingList() {
		this.documentFrequency = 0;
		this.termFrequency = 0;
		this.head = null;
		this.tail = null;
	}
	
	public PostingList(Integer documentFrequency, Integer termFrequency) {
		this.documentFrequency = documentFrequency;
		this.termFrequency = termFrequency;
		this.head = null;
		this.tail = null;
	}
	
	public Integer getDocumentFrequency() {
		return documentFrequency;
	}
	
	public void setDocumentFrequency(Integer documentFrequency) {
		this.documentFrequency = documentFrequency;
	}
	
	public void incrementDocumentFrequency(Integer value) {
		this.documentFrequency += value;
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
	
	public Posting getHead() {
		return head;
	}
	
	public void setHead(Posting head) {
		this.head = head;
	}
	
	public Posting getTail() {
		return tail;
	}
	
	public void setTail(Posting tail) {
		this.tail = tail;
	}
	
	public Boolean contains(Integer documentId) {
		Posting current = head;
		while (current != null) {
			if (current.getDocumentId().equals(documentId)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}
	
	public Posting getPostingItem(Integer documentId) {
		Posting current = head;
		while (current != null) {
			if (current.getDocumentId().equals(documentId)) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}
	
	public void addPostingItem(Posting posting) {
		if (head == null) {
			head = posting;
			tail = posting;
		} else {
			tail.setNext(posting);
			tail = posting;
		}
	}
	
	public void printPostingList() {
		Posting current = head;
		while (current != null) {
			System.out.println("DocumentId: " + current.getDocumentId() + " TermFrequency: " + current.getTermFrequency());
			current = current.getNext();
		}
	}
}
