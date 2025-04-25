package Models;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DocumentPriorityQueue {
	private PriorityQueue<Document> queue;
	
	public DocumentPriorityQueue() {
		// Comparator to sort by similarity in descending order
		this.queue = new PriorityQueue<>(Comparator.comparingDouble(Document::getSimilarity).reversed());
	}
	
	public void addDocument(Integer documentId, Double similarity) {
		queue.add(new Document(documentId, similarity));
	}
	
	public Document poll() {
		return queue.poll(); // Retrieves and removes the head of the queue
	}
	
	public Boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public static class Document {
		private Integer documentId;
		private Double similarity;
		
		public Document(Integer documentId, Double similarity) {
			this.documentId = documentId;
			this.similarity = similarity;
		}
		
		public Integer getDocumentId() {
			return documentId;
		}
		
		public Double getSimilarity() {
			return similarity;
		}
	}
}
