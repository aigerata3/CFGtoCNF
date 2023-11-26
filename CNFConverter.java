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
        String fileName = "input3.txt";
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
		printGrammar(this.grammar);
    }

    private void eliminateNull() {
        // Implementation...
    }

    private void eliminateUnit() {
        // Implementation...
    }

	// If a production's right hand side has more than two symbols, break it up
    private void convertToBinary() {
		// Create new grammar
		HashMap<String, ArrayList<ArrayList<String>>> newGrammar = new HashMap<>();
		int suffix = 0;

		// For Variable
		for (String lhs : grammar.keySet()) {
			System.out.println("LHS: " + lhs);
			// 
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				System.out.println("RHS: " + rhs);
				// If the right hand side is longer than two terms
				if (rhs.size() > 2) {
					// Create a new left hand side
					String newLhs = lhs;
					while (rhs.size() > 1) {
						// Make a new variable for the production
						String newRhsFirstSymbol = rhs.remove(0);

						// Make sure the new variable is unique
						String newVariable;
						do {
							newVariable = newLhs + suffix++;
						} while (newGrammar.containsKey(newVariable));

						// Create new right hand side list
						ArrayList<String> newProductionRhs = new ArrayList<>();
						newProductionRhs.add(newRhsFirstSymbol);
						newProductionRhs.add(newVariable);

						// Add new production
						newGrammar.putIfAbsent(newLhs, new ArrayList<>());
						newGrammar.get(newLhs).add(newProductionRhs);

						newLhs = newVariable;
					}
					newGrammar.putIfAbsent(newLhs, new ArrayList<>());
					newGrammar.get(newLhs).add(rhs);
				} else {
					newGrammar.putIfAbsent(lhs, new ArrayList<>());
					newGrammar.get(lhs).add(rhs);
				}
			}
			System.out.println();
		}

		grammar = newGrammar;
	}


	// Prints the hashmap grammar
	private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
		for (String key : grammar.keySet()) {
			System.out.println("Key: " + key + ", Value: " + grammar.get(key));
		}
	}
}
