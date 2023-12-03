import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnitProductionEliminator {
    private HashMap<String, ArrayList<ArrayList<String>>> grammar;
    // Constructor
    public UnitProductionEliminator(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

    // Erin's Functions
	// Get rid of all unit prods (run the eliminateUnit function until the output doesn't change)
	public HashMap<String, ArrayList<ArrayList<String>>> eliminateUnit() {
		HashMap<String, ArrayList<ArrayList<String>>> previousGrammar;
		do {
			previousGrammar = deepCopy(grammar); // Deep copy the current state of grammar
			eliminateUnitLevel(); // Apply the unit elimination
			// printGrammar(previousGrammar);
			// printGrammar(grammar);
			// System.out.println();
		} while (!grammar.equals(previousGrammar)); // Continue until no change
        return grammar;
	}

	// Creates a deep copy of a grammar for comparison to function output
	private HashMap<String, ArrayList<ArrayList<String>>> deepCopy(HashMap<String, ArrayList<ArrayList<String>>> original) {
		// Create copy
		HashMap<String, ArrayList<ArrayList<String>>> copy = new HashMap<>();
		// For each variable
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : original.entrySet()) {
			String key = entry.getKey();
			// Make a copy of the right hand sides
			ArrayList<ArrayList<String>> valueCopy = new ArrayList<>();

			for (ArrayList<String> list : entry.getValue()) {
				// Strings are immutable, so direct copy is fine
				ArrayList<String> listCopy = new ArrayList<>(list); 
				valueCopy.add(listCopy);
			}

			copy.put(key, valueCopy);
		}

		return copy;
	}

	// Eliminate unit productions
	private void eliminateUnitLevel() {
		// New grammar to replace the old one
		HashMap<String, ArrayList<ArrayList<String>>> newGrammar = new HashMap<>();

		// For each variable in the original grammar (We'll call this variable A)
		for (String lhs : grammar.keySet()) {
			// Create a new list of productions
			ArrayList<ArrayList<String>> newProductions = new ArrayList<>();
			// For production for this variable 
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// Check for unit production (single uppercase character)
				if (rhs.size() == 1 && Character.isUpperCase(rhs.get(0).charAt(0))) {
					// Unit production A -> B found, add B's productions to A
					// Ger variable as string
					String rhsVariable = rhs.get(0);
					// Get all productions for B (rhsVariable)
					ArrayList<ArrayList<String>> rhsProductions = grammar.getOrDefault(rhsVariable,
							new ArrayList<ArrayList<String>>());

					// Add all productions of B to A's new productions
					newProductions.addAll(rhsProductions);
				} else {
					// Keep the original non-unit production
					newProductions.add(rhs);
				}
			}
			// Update the new grammar with the new productions for A
			newGrammar.put(lhs, newProductions);
		}
		// Replace the old grammar with the new one
		grammar = newGrammar;
	}
}
