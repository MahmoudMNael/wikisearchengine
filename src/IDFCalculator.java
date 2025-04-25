import java.util.Map;
import java.util.HashMap;

public class IDFCalculator {
    // Note about the IDF calculation:
    // IDF is calculated using the formula: IDF = log10(totalDocuments / documentFrequency)
    // The higher the IDF value, the more important the term is in the document collection.
    // A term that appears in many documents will have a lower IDF value,
    // while a term that appears in fewer documents will have a higher IDF value.
    private Map <String, Double> _idfMap = new HashMap<>(); // Map of IDF values indexed by term

    public void calculateIDF(Map<String, PostingList> invertedIndex, Integer totalDocuments) {
        // Iterate through the inverted index to calculate IDF for each term
        for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            PostingList postingList = entry.getValue();
            Integer documentFrequency = postingList.getDocumentFrequency(); // Get the document frequency for the term
            Double idfValue;
            // Calculate IDF using the formula: IDF = log10(totalDocuments / documentFrequency)
            if (documentFrequency == 0 || documentFrequency == null) {
                idfValue = 0.0;
            } else {
                idfValue = Math.log10((double) totalDocuments / documentFrequency);
            }
            // Store the IDF value in the idfMap
            _idfMap.put(term, idfValue);
        }
    }

    public Map<String, Double> getIDFMap() {
        return _idfMap;
    }

    public void printIDF() {
        for (Map.Entry<String, Double> entry : _idfMap.entrySet()) {
            String term = entry.getKey();
            Double idfValue = entry.getValue();
            System.out.println("Term: " + term + ", IDF Value: " + idfValue);
        }
    }
}
