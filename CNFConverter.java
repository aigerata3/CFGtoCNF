import java.util.ArrayList;
import java.util.HashMap;

//converts Grammar to CNF
public class CNFConverter {
    HashMap<String, ArrayList<ArrayList<String>>> grammar;

	// Parses input file, assigns to grammar
    public CNFConverter(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

    public static void main(String[] args) {
        String fileName = "input2.txt";
        CFGHashMapParser parser = new CFGHashMapParser();
        HashMap<String, ArrayList<ArrayList<String>>> grammar = parser.parseFile(fileName);

        CNFConverter converter = new CNFConverter(grammar);
        converter.convertToCNF();
    }

    public void convertToCNF() {
        eliminateNull();
        eliminateUnit();
        convertToBinary();
        // save to output txt file
    }

    private void eliminateNull() {
        // Implementation...
    }

    private void eliminateUnit() {
        // Implementation...
    }

    private void convertToBinary() {
        // Implementation...
    }
}
