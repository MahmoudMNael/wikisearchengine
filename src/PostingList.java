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
	
	public static PostingList intersectPostingLists(PostingList list1, PostingList list2) {
		Posting current1 = list1.getHead();
		Posting current2 = list2.getHead();
		Posting resultHead = null;
		Posting resultTail = null;
		
		while (current1 != null && current2 != null) {
			if (current1.getDocumentId().equals(current2.getDocumentId())) {
				Posting newPosting = new Posting(current1.getDocumentId(), current1.getTermFrequency() + current2.getTermFrequency());
				if (resultHead == null) {
					resultHead = newPosting;
					resultTail = newPosting;
				} else {
					resultTail.setNext(newPosting);
					resultTail = newPosting;
				}
				current1 = current1.getNext();
				current2 = current2.getNext();
			} else if (current1.getDocumentId() < current2.getDocumentId()) {
				current1 = current1.getNext();
			} else {
				current2 = current2.getNext();
			}
		}
		
		return new PostingList(list1.documentFrequency + list2.documentFrequency, list1.termFrequency + list2.termFrequency);
	}
}
