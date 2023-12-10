import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//converts Grammar to CNF
public class CNFConverter {
	//private ArrayList<ArrayList<String>> grammar;
	private HashMap<String,ArrayList<ArrayList<String>>> grammar;
	private static boolean verbose;

	public CNFConverter(HashMap<String,ArrayList<ArrayList<String>>> grammar, boolean verbose) {
		this.grammar = grammar;
		CNFConverter.verbose = verbose;
	}
	
	HashMap<String,ArrayList<ArrayList<String>>> convertToCNF() {
		printGrammar(grammar, "\nInput grammar:");
	    grammar = new NullProductionEliminator(grammar).eliminateNull();
		printGrammar(grammar, "Null productions eliminated:");

		grammar = new UnitProductionEliminator(grammar).eliminateUnit();
		printGrammar(grammar, "Unit productions eliminated:");

		grammar = new BinaryProductionConverter(grammar).convertBinary();
		printGrammar(grammar, "Break up productions longer than two symbols:");
		
		grammar = new SingleTerminalConverter(grammar).convertTerminal();
		printGrammar(grammar, "Make a dedicated production for each terminal:");
		return grammar;
	}

	private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar, String heading) {
		if (verbose) {
			System.out.print(heading + "\n");
			for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}
			System.out.print("\n\n");
		}
	}
}
