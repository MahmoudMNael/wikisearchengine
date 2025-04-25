package Models;

public class TermFrequencyWeight {
    private Integer Id; // Document ID
    private Double tfValue; // Term Frequency in the document

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