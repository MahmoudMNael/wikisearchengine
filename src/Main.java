import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        Crawler crawler = new Crawler(Arrays.asList(
                "https://en.wikipedia.org/wiki/List_of_pharaohs",
                "https://en.wikipedia.org/wiki/Pharaoh"),
                10);

        List<SourceData> sourceDataList = crawler.getDocs();

        IndexBuilder indexBuilder = new IndexBuilder();

        indexBuilder.buildIndex(sourceDataList);

        // indexBuilder.printIndex();

        // ====================== TF-Calculations =========================
        TFCalculator tfCalc = new TFCalculator();
        tfCalc.calculateTF(indexBuilder.getInvertedIndex());
        // tfCalc.printTF();

        // ====================== IDF-Calculations =========================
        IDFCalculator idfCalc = new IDFCalculator();
        idfCalc.calculateIDF(indexBuilder.getInvertedIndex(), crawler.getNumberOfDoc());
        // idfCalc.printIDF();

        // ======================= TF-IDF Calculations =========================
        DocumentVector docVector = new DocumentVector();
        docVector.calculateTF_IDF(tfCalc.getTfMap(), idfCalc.getIDFMap());
        // docVector.printDocumentVectors();
    }
}