package Models;

// This class represents the term frequency weight for a document.
public class TermFrequencyWeight {
    private Integer Id; // Document ID
    private Double tfValue; // Term Frequency in the document

    // Constructor 
    public TermFrequencyWeight(Integer Id, Double tfValue) {
        this.Id = Id;
        this.tfValue = tfValue;
    }

    public Integer getIdValue() {
        return Id;
    }

    public Double getTFValue() {
        return tfValue;
    }
}