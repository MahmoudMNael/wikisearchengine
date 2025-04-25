import Models.Index;
import Services.Crawler;
import Util.*;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
	public static void main(String[] args) throws Exception {
		Crawler crawler = new Crawler(Arrays.asList(
				"https://en.wikipedia.org/wiki/List_of_pharaohs",
				"https://en.wikipedia.org/wiki/Pharaoh"),
				10);
		
		Index index = IndexFactory.build(crawler.getDocs());
		
		// index.printIndex();
		
		// ====================== TF-Calculations =========================
		TFCalculator tfCalc = new TFCalculator();
		tfCalc.calculateTF(index.getInvertedIndex());
		// tfCalc.printTF();
		
		// ====================== IDF-Calculations =========================
		IDFCalculator idfCalc = new IDFCalculator();
		idfCalc.calculateIDF(index.getInvertedIndex(), index.getSources().size());
		// idfCalc.printIDF();
		
		// ======================= TF-IDF Calculations =========================
		DocumentVector docVector = new DocumentVector();
		docVector.calculateTF_IDF(tfCalc.getTfMap(), idfCalc.getIDFMap());
		// docVector.printDocumentVectors();
		
		// ======================= UI ======================= //
		
		Scanner scanner = new Scanner(System.in);
		
		outer:
		while (true) {
			System.out.println("\033[34mB\033[31mo\033[33mo\033[34mb\033[32ml\033[31me\033[0m");
			System.out.println("\033[36m1. Enter a query\033[0m");
			System.out.println("\033[36m0. Exit\033[0m");
			System.out.print("\033[35mChoose an option: \033[0m");
			Integer choice;
			
			try{
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception e){
				System.out.println("\033[31mInvalid choice. Please try again using a valid NUMBER.\033[0m");
				System.out.println("-----------------------------------------------------------------------------------");
				scanner.nextLine();
				continue outer;
			}
			
			if (choice.equals(1)) {
				System.out.print("\033[32mEnter your query: \033[0m");
				String query = scanner.nextLine();
				QueryHandler.handle(query);
			} else if (choice == 0) {
				System.out.println("Exiting...");
				scanner.close();
				exit(0);
			} else {
				System.out.println("\033[31mInvalid choice. Please try again using a valid NUMBER.\033[0m");
			}
			System.out.println("-----------------------------------------------------------------------------------");
		}
		
		// ======================= End UI ======================= //
	}
}