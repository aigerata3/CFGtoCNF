import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SingleTerminalConverter {
    private HashMap<String,ArrayList<ArrayList<String>>> grammar;

	public SingleTerminalConverter(HashMap<String,ArrayList<ArrayList<String>>> grammar) {
		this.grammar = grammar;
	}

	// Aigerim
    public HashMap<String,ArrayList<ArrayList<String>>> convertTerminal() {
		// Make Hash set of single, dedicated terminal productions, and their variables
		// This is to keep track of single terminals that were already handled (have dedicated productions)
		// Note: Productions of the form A -> a | b are not handled. Only dedicated
		// productions like A -> a.
		HashMap<String, String> handledTerminals = new HashMap<>();
		// Keep track of variables to delete (because they are redundant dedicated terminal productions)
		HashSet<String> varsToDelete = new HashSet<>();

		System.out.println("Finding terms");
		// Find all terminals that are already handled
		String prod;
		// For each variable
		for (String lhs : grammar.keySet()) {
			// If the variable only has one production (i.e. A -> x, rather than A -> b | cd)
			if (grammar.get(lhs).size() == 1) {
				// Get production
				prod = grammar.get(lhs).get(0).get(0);
				// If that production is a single terminal (test first character since
				// terminals may be in the form a12) and it hasn't been handled
				if (Character.isLowerCase(prod.charAt(0))) {
					// If it already has a dedicated production, add variable to the toDelete set
					if (handledTerminals.containsKey(prod)) {
						varsToDelete.add(lhs);
					} else {
						// Otherwise, this terminal has been handled
						handledTerminals.put(prod, lhs);
					}
				}
			}
		}

		System.out.println("Handling rest of terms");
		// Create new grammar
		HashMap<String, ArrayList<ArrayList<String>>> newGrammar = new HashMap<>();

		// Then handle all terminals that don't already have a dedicated production
		ArrayList<ArrayList<String>> newRhs;
		// Check all productions
		for (String lhs : grammar.keySet()) {
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				// Check each symbol in the production. 
				for (String symbol : rhs) {
					// If it's a terminal, and it's not been handled yet
					if (Character.isLowerCase(symbol.charAt(0)) && !handledTerminals.containsKey(symbol)) {
						// Make a new productions, add it to the grammar
						int suffix = 1;

						// Make sure the new variable is unique
						String newVariable;
						do {
							newVariable = symbol.toUpperCase().charAt(0) + String.valueOf(suffix++);
						} while (grammar.containsKey(newVariable));

						// Make new rhs
						newRhs = new ArrayList<>();
						newRhs.add(new ArrayList<>());
						newRhs.get(0).add(symbol);

						// Add production to the newGrammar
						newGrammar.put(newVariable, newRhs);
						// And add new terminal production to the handled set
						handledTerminals.put(symbol, newVariable);
					}
				}
			}
		}

		System.out.println("Replace terms with vars");
		// Replace terminals with their variables
		ArrayList<ArrayList<String>> newProdList;
		ArrayList<String> newProd;
		// For each variable, create a new a new production list
		for (String lhs : grammar.keySet()) {
			newProdList = new ArrayList<>();
			// For each production, constuct a new one without terminals
			for (ArrayList<String> rhs : grammar.get(lhs)) {
				newProd = new ArrayList<>();
				// Check each symbol in the production. 
				for (String symbol : rhs) {
					// If it's a terminal in a string, replace with that terminal's variable
					if (Character.isLowerCase(symbol.charAt(0)) && rhs.size() > 1) {
						newProd.add(handledTerminals.get(symbol));
					} else {
						// Otherwise, keep it in the production
						newProd.add(symbol);
					}
				}
				// Add new prodution to list
				newProdList.add(newProd);
			}
			// Add productions to new grammar
			newGrammar.put(lhs, newProdList);
		}

		return newGrammar;
	}
}
