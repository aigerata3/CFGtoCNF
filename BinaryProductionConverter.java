import java.util.ArrayList;
import java.util.HashMap;

public class BinaryProductionConverter {
    private HashMap<String, ArrayList<ArrayList<String>>> grammar;

    public BinaryProductionConverter(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

    // Rachel's function
	public HashMap<String, ArrayList<ArrayList<String>>> convertBinary() {
		// Create new grammar
		HashMap<String, ArrayList<ArrayList<String>>> newGrammar = new HashMap<>();
		int suffix = 0;

		// For each variable
		for (String lhs : grammar.keySet()) {
			// System.out.println("LHS: " + lhs);
			// For every variable's production
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// System.out.println("RHS: " + rhs);
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
			// System.out.println();
		}
		return newGrammar;
	}
}
