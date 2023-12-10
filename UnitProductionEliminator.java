import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UnitProductionEliminator {
    private HashMap<String, ArrayList<ArrayList<String>>> grammar;
    // Constructor
    public UnitProductionEliminator(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

	// Finds all the units productions in a set of variables and adds them to the set
	private void addUnitsToSet(HashSet<String> unitSet) {
		// For each variable B in the set
		for (String lhs : unitSet) {
			// Add any unit productions to the set
			getUnitsFromRhs(lhs, unitSet);
		}
	}

	// Gets all the unit productions for a variable and adds them to a set
	private void getUnitsFromRhs(String lhs, HashSet<String> unitSet) {
		// For each production from the RHS, add to the set
		for (ArrayList<String> rhs : grammar.get(lhs)) {
			// If it is a nonterminal
			if (rhs.size() == 1 && Character.isUpperCase(rhs.get(0).charAt(0))) {
				unitSet.add(rhs.get(0));
			}
		}
	}

	// Returns all the productions for a variable that are not unit productions
	private ArrayList<ArrayList<String>> getNonUnits(String lhs) {
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
	// Get rid of all unit prods 
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
			} while (!currUnitSet.equals(prevUnitSet));
			// Remove original variable A from the set
			currUnitSet.remove(lhs);
			// Add set to unit prods hashmap
			unitProds.put(lhs, currUnitSet);
		}

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
			// Add new rhs to the new grammar if it's not empty
			if (newRhs.size() > 0) {
				newGrammar.put(lhs, newRhs);
			}
		}

        return newGrammar;
	}
}
