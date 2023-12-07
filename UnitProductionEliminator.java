import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class UnitProductionEliminator {
    private HashMap<String, ArrayList<ArrayList<String>>> grammar;
    // Constructor
    public UnitProductionEliminator(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

	// Finds all the units productions in a set of variables and adds them to the set
	public void addUnitsToSet(HashSet<String> unitSet) {
		// For each variable B in the set
		for (String lhs : unitSet) {
			// Add any unit productions to the set
			getUnitsFromRhs(lhs, unitSet);
		}
	}

	// Gets all the unit productions for a variable and adds them to a set
	public void getUnitsFromRhs(String lhs, HashSet<String> unitSet) {
		// For each production from the RHS, add to the set
		for (ArrayList<String> rhs : grammar.get(lhs)) {
			// If it is a nonterminal
			if (rhs.size() == 1 && Character.isUpperCase(rhs.get(0).charAt(0))) {
				unitSet.add(rhs.get(0));
			}
		}
	}

	// Removes all unit productions from the rhs of the given variable
	public void deleteUnits(String lhs) {
		// For each production from the RHS
		for (ArrayList<String> rhs : grammar.get(lhs)) {
			// If the symbol is a variable (nonterminal)
			if (rhs.size() == 1 && Character.isUpperCase(rhs.get(0).charAt(0))) {
				// remove rhs from grammar.get(lhs)
			}
		}
	}

	// Returns all the productions for a variable that are not unit productions
	public ArrayList<ArrayList<String>> getNonUnits(String lhs) {
		ArrayList<ArrayList<String>> nonUnits = new ArrayList<>();
		// For each production for this variable lhs
		for (ArrayList<String> rhs : grammar.get(lhs)) {
			// If the production longer than one symbol
			if (rhs.size() > 1) {
				// Add it to the list
				nonUnits.add(rhs);
			}
		}
		return nonUnits;
	}

    // Erin's Functions
	// Get rid of all unit prods (run the eliminateUnit function until the output doesn't change)
	public HashMap<String, ArrayList<ArrayList<String>>> eliminateUnit() {
		// Create new grammar
		HashMap<String, ArrayList<ArrayList<String>>> newGrammar = new HashMap<>();
		// Make hashmap of Variable: set of unit production (chains)
		HashMap<String, HashSet<String>> unitProds = new HashMap<>();

		// For each variable A in the grammar
		for (String lhs : grammar.keySet()) {
			// Make a set prev
			HashSet<String> prevUnitSet = new HashSet<>();
			// For each unit variable from the RHS, add to the set
			getUnitsFromRhs(lhs, prevUnitSet);

			// Make set current
			HashSet<String> currUnitSet = new HashSet<>();
			// do while prev set is different from current set
			do {
				// Deep copy current set, assign to prev set
				prevUnitSet = new HashSet<String>(currUnitSet);
				// Add next group of unit productions
				addUnitsToSet(currUnitSet);
			} while (currUnitSet.equals(prevUnitSet));
			// Remove original variable A from the set
			currUnitSet.remove(lhs);
			// Add set to unit prods hashmap
			unitProds.put(lhs, currUnitSet);
		}

		// THEN
		ArrayList<ArrayList<String>> nonUnitProds;
		// For each variable A in the unit prods hashmap
		for (String lhs : unitProds.keySet()) {
			// For each variable B in A's set
			for (String rhs : unitProds.get(lhs)) {
				// Replace B with any string that B produces (that is not a unit production)
				// Get B's productions
				nonUnitProds = getNonUnits(rhs);
				// Add those productions to A's productions
				grammar.get(lhs).addAll(nonUnitProds);
			}
		}

		// Remove all unit productions
		// For each variable A in the grammar
		for (String lhs : grammar.keySet()) {
			// Create a new rhs list
			ArrayList<ArrayList<String>> newRhs = new ArrayList<>();
			// For each production from the RHS, add to the set
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// If it is NOT a unit production
				if (rhs.size() > 1 || Character.isLowerCase(rhs.get(0).charAt(0))) {
					// Add production to the new rhs
					newRhs.add(rhs);
				}
			}
			// Add new rhs to the new grammar
			newGrammar.put(lhs, newRhs);
		}

		// System.out.println("UNITS");
		// HashMap<String, ArrayList<ArrayList<String>>> previousGrammar;
		// do {
		// 	previousGrammar = deepCopy(grammar); // Deep copy the current state of grammar
		// 	eliminateUnitLevel(); // Apply the unit elimination
		// 	System.out.println();
		// 	printGrammar(previousGrammar);
		// 	printGrammar(grammar);
		// 	System.out.println();
		// } while (!grammar.equals(previousGrammar)); // Continue until no change
        return newGrammar;
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

		// 1. Find a unit production A -> B
		// 2. Get the right hand side for the B
		// 3. Add the right hand side from B to A
		// 4. Delete A -> B

		// For each variable in the original grammar (We'll call this variable A)
		for (String lhs : grammar.keySet()) {
			// Create a new list of productions
			ArrayList<ArrayList<String>> newProductions = new ArrayList<>();
			// For each production for this variable 
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// Check for unit production (single uppercase character)
				if (rhs.size() == 1 && Character.isUpperCase(rhs.get(0).charAt(0))) {
					// Unit production A -> B found, add B's productions to A
					// Ger variable as string
					String rhsVariable = rhs.get(0);
					// Get all productions for B (rhsVariable)
					ArrayList<ArrayList<String>> rhsProductions = grammar.getOrDefault(rhsVariable,
							new ArrayList<ArrayList<String>>());

					// Add all productions of B to A's new productions if not already present
					for (ArrayList<String> prod : rhsProductions) {
						if (!newProductions.contains(prod)) {
							newProductions.add(prod);
						}
					}		
					// Add all productions of B to A's new productions
					// newProductions.addAll(rhsProductions);
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

	private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
		System.out.println("Parsed Grammar:");
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}
}
