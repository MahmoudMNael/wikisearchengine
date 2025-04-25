import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TFCalculator {
    private Map<String, List<DocumentTF>> _tfMap = new HashMap<>(); // Map of term data indexed by document ID

    public void calculateTF(Map<String, PostingList> invertedIndex) {
        // Iterate through the inverted index to calculate term frequency for each term
        for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            PostingList postingList = entry.getValue();
            Posting current = postingList.getHead();
            while (current != null) {
                Integer documentId = current.getDocumentId();
                Integer termFrequency = current.getTermFrequency();
                Double tfValue;
                // Calculate term frequency for the document
                if (termFrequency == 0 || termFrequency == null) {
                    tfValue = 0.0;
                } else {
                    tfValue = 1 + Math.log10(termFrequency);
                }
                DocumentTF tfDocId = new DocumentTF(documentId, tfValue);
                // Add the TF_DocID object to the map using if-else
                if (_tfMap.containsKey(term)) {
                    _tfMap.get(term).add(tfDocId);
                } else {
                    List<DocumentTF> tfDocIdList = new ArrayList<>();
                    tfDocIdList.add(tfDocId);
                    _tfMap.put(term, tfDocIdList);
                }
                // Move to the next posting in the list
                current = current.getNext();
            }
        }
    }

    public void printTF() {
        for (Map.Entry<String, List<DocumentTF>> entry : _tfMap.entrySet()) {
            String term = entry.getKey();
            List<DocumentTF> tfDocIdList = entry.getValue();
            System.out.println("Term: " + term);
            for (DocumentTF tfDocId : tfDocIdList) {
                System.out.println("Document ID: " + tfDocId.getDocumentId() + ", TF Value: " + tfDocId.getTFValue());
            }
        }
    }

    public Map<String, List<DocumentTF>> getTfMap() {
        return _tfMap;
    }
}
