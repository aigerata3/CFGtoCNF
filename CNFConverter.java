import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//converts Grammar to CNF
public class CNFConverter {
	//private ArrayList<ArrayList<String>> grammar;
	private HashMap<String,ArrayList<ArrayList<String>>> grammar;

	public CNFConverter(HashMap<String,ArrayList<ArrayList<String>>> grammar) {
		this.grammar = grammar;
	}
	
	HashMap<String,ArrayList<ArrayList<String>>> convertToCNF() {
		System.out.println("Eliminating null...");
	    grammar = new NullProductionEliminator(grammar).eliminateNull();
		printGrammar(grammar);
		System.out.println("Nulls eliminated. Eliminating Units.");
		grammar = new UnitProductionEliminator(grammar).eliminateUnit();
		printGrammar(grammar);
		System.out.println("Units eliminated. Converting to binary.");
		grammar = new BinaryProductionConverter(grammar).convertBinary();
		printGrammar(grammar);
		System.out.println("Binary finished. Converting single terminals.");
		grammar = new SingleTerminalConverter(grammar).convertTerminal();
		grammar = new RedundantRemover(grammar).removeRedundants();
		return grammar;
	}

	private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
		System.out.println("Parsed Grammar:");
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}
}
