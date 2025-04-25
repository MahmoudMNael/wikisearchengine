package Util;

import Models.DocumentTF;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DocumentVector {
	private Map<Integer , Map<String , Double>> _documentVectorMap = new HashMap<>(); // Map of document vectors indexed by document ID
	
	public void calculateTF_IDF (Map<String, List<DocumentTF>> tfMap, Map<String, Double> idfMap) {
		Double tf_idfValue = 0.0;
		for (Map.Entry<String, List<DocumentTF>> entry : tfMap.entrySet()) {
			String term = entry.getKey();
			List<DocumentTF> tfDocIdList = entry.getValue();
			for (DocumentTF tfDocId : tfDocIdList) {
				Integer documentId = tfDocId.getDocumentId();
				Double tfValue = tfDocId.getTFValue();
				if (idfMap.containsKey(term)) {
					Double idfValue = idfMap.get(term);
					tf_idfValue = tfValue * idfValue;
					// Add the TF-IDF value to the document vector map
					if (_documentVectorMap.containsKey(documentId)) {
						Map<String, Double> vector = _documentVectorMap.get(documentId);
						vector.put(term, tf_idfValue);
					} else {
						Map<String, Double> vector = new HashMap<>();
						vector.put(term, tf_idfValue);
						_documentVectorMap.put(documentId, vector);
					}
				}
			}
		}
	}
	
	public void printDocumentVectors() {
		for (Map.Entry<Integer, Map<String, Double>> entry : _documentVectorMap.entrySet()) {
			Integer documentId = entry.getKey();
			Map<String, Double> vector = entry.getValue();
			System.out.println("Document ID: " + documentId);
			for (Map.Entry<String, Double> termEntry : vector.entrySet()) {
				String term = termEntry.getKey();
				Double tf_idfValue = termEntry.getValue();
				System.out.println("Term: " + term + ", TF-IDF Value: " + tf_idfValue);
			}
			System.out.println("--------------------------------------------------");
		}
	}
	
	public Map<Integer, Map<String, Double>> getDocumentVectorMap() {
		return _documentVectorMap;
	}
}
