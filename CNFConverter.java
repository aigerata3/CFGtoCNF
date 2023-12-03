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
	    grammar = new NullProductionEliminator(grammar).eliminateNull();
		grammar = new UnitProductionEliminator(grammar).eliminateUnit();
		grammar = new BinaryProductionConverter(grammar).convertBinary();
		grammar = new SingleTerminalConverter(grammar).convertTerminal();
		return grammar;
	}

	private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
		System.out.println("Parsed Grammar:");
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}
}
