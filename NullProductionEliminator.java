import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NullProductionEliminator {
    private HashMap<String, ArrayList<ArrayList<String>>> grammar;
    // Constructor
    public NullProductionEliminator(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

    // Vicki's Function's
	// Gets all the unit productions for a variable and adds them to a set
	private void getVarsThatLeadToNull(HashSet<String> nullSet) {
        // For each variable A in the grammar
        for (String lhs : grammar.keySet()) {
            // If that variable A leads to a null variable in the set or lambda
            for (ArrayList<String> rhs : grammar.get(lhs)) {
                // If lambda
                if (rhs.get(0).equals("lambda")) {
                    nullSet.add(lhs);
                // Or if null variable
                } else if (rhs.size() == 1 && nullSet.contains(rhs.get(0))) {
                    nullSet.add(lhs);
                }
            }
        }
	}

    // Returns true if the production contains a nullable variable
    private boolean containsNullVariables(ArrayList<String> rhs, HashSet<String> nullSet) {
        System.out.println();
        System.out.println("IN CONTAINS NULLABLE VARIABLES. CHECKING");
        System.out.println(rhs);
        for (String symbol : rhs) {
            System.out.println(symbol);
            if (nullSet.contains(symbol) || symbol.equals("lambda")) {
                System.out.println("SYMBOL IS NULLABLE");
                return true;
            }
        }
        return false;
    }

    // Returns list of productions with all combinations of nullables removed
    private ArrayList<ArrayList<String>> getNullifications(ArrayList<String> rhs, HashSet<String> nullSet) {
        ArrayList<ArrayList<String>> nullies = new ArrayList<>();
        // Get the indices of all nullable variables in production
        // Add set for indices of nullables
        HashSet<Integer> nullIndices = new HashSet<>();
        for (int i = 0; i < rhs.size(); i++) {
            String symbol = rhs.get(i);
            if (nullSet.contains(symbol)) {
                // Add index to index set
                nullIndices.add(i);
            }
        }

        // Get all subsets of indices
        HashSet<HashSet<Integer>> indexSubsets = getAllSubsets(nullIndices);

        // Iterate over each subset of indices
        for (HashSet<Integer> indexSubset : indexSubsets) {
            // Create a new list for this subset
            ArrayList<String> currentNullification = new ArrayList<>();
            // Iterate over the original rhs list
            for (int i = 0; i < rhs.size(); i++) {
                // If the index is not in the current subset, add the element
                if (!indexSubset.contains(i)) {
                    currentNullification.add(rhs.get(i));
                }
            }
            // Add the new list to nullies
            if (currentNullification.size() > 0) {
                nullies.add(currentNullification);
            }
        }

        return nullies;
    }

    // Shamelessly stolen from the innernet
    private HashSet<HashSet<Integer>> getAllSubsets(HashSet<Integer> originalSet) {
        HashSet<HashSet<Integer>> allSubsets = new HashSet<>();
        ArrayList<Integer> elementList = new ArrayList<>(originalSet);
        int numberOfSubsets = 1 << originalSet.size(); // 2^n subsets

        for (int subsetMask = 0; subsetMask < numberOfSubsets; subsetMask++) {
            HashSet<Integer> subset = new HashSet<>();
            for (int i = 0; i < elementList.size(); i++) {
                // Check if the ith element is in this subset
                if ((subsetMask & (1 << i)) != 0) {
                    subset.add(elementList.get(i));
                }
            }
            allSubsets.add(subset);
        }
        return allSubsets;
    }

	// Get rid of all lambda prods
	public HashMap<String, ArrayList<ArrayList<String>>> eliminateNull() {
		// Create new grammar
		HashMap<String, ArrayList<ArrayList<String>>> newGrammar = new HashMap<>();

        // Make a set prev
        HashSet<String> prevNullProdSet = new HashSet<>();
        // Make set current
        HashSet<String> currNullProdSet = new HashSet<>();
        // do while prev set is different from current set
        do {
            // Deep copy current set, assign to prev set
            prevNullProdSet = new HashSet<String>(currNullProdSet);
            // Add next group of null productions
            getVarsThatLeadToNull(currNullProdSet);
        } while (!currNullProdSet.equals(prevNullProdSet));

        System.out.println();
        System.out.println("Nullable variables");
        System.out.println(currNullProdSet);
        System.out.println();

        // For each production
        for (String lhs : grammar.keySet()) {
			// Create a new rhs list
			ArrayList<ArrayList<String>> newRhs = new ArrayList<>();
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// If that production contains null variables
                if (containsNullVariables(rhs, currNullProdSet)) {
                    // Add unit production that account for all possible nullifications
                    ArrayList<ArrayList<String>> nullifications = getNullifications(rhs, currNullProdSet);
                    // Add nullifications to the new rhs list
                    System.out.println("Adding nullifications:");
                    newRhs.addAll(nullifications);
                    System.out.println(nullifications);
                } else {
                    System.out.println("Adding old rhs");
                    newRhs.add(rhs);
                    System.out.println(rhs);
                }
                System.out.println("Updated new rhs for");
                System.out.println(lhs);
                System.out.println(newRhs);
                
			}
            System.out.println();
			// Add new rhs to the new grammar 
			newGrammar.put(lhs, newRhs);
		}

        // Remove all single nullable productions
        System.out.println("REMOVING LAST NULLS");
        HashMap<String, ArrayList<ArrayList<String>>> newNewGrammar = new HashMap<>();
        // For each variable A in the grammar
        for (String lhs : newGrammar.keySet()) {
            System.out.println("FOR LHS");
            System.out.println(lhs);
            // Create a new rhs list
            ArrayList<ArrayList<String>> newRhs = new ArrayList<>();
            // For each production from the RHS, add to the set
            for (ArrayList<String> rhs : newGrammar.get(lhs)) {
                System.out.println("FOR RHS");
                System.out.println(rhs);
                // If it is NOT a lambda production or single nullable variable
                if (!rhs.get(0).equals("lambda") && (rhs.size() > 1 || 
                    Character.isLowerCase(rhs.get(0).charAt(0)))) {
                    System.out.println("PUT BACK IN");
                    // Add production to the new rhs
                    newRhs.add(rhs);
                }
            }
            // Add new rhs to the new grammar if it's not empty
            if (newRhs.size() > 0) {
                newNewGrammar.put(lhs, newRhs);
            }         
            System.out.println();
        }

        return newNewGrammar;
	}
}
