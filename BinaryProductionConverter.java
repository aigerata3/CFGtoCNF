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

		// For each variable
		for (String lhs : grammar.keySet()) {
			// For each production for this variable
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// If the right hand side is longer than two terms
				if (rhs.size() > 2) {
					int suffix = 1;
					// Create a new left hand side
					String newLhs = lhs;
					while (rhs.size() > 2) {
						// Make a new variable for the production
						String newRhsFirstSymbol = rhs.remove(0);

						// Make sure the new variable is unique
						String newVariable;
						do {
							newVariable = newLhs.charAt(0) + String.valueOf(suffix++);
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
