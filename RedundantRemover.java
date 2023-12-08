import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RedundantRemover {
    private HashMap<String,ArrayList<ArrayList<String>>> grammar;

	public RedundantRemover(HashMap<String,ArrayList<ArrayList<String>>> grammar) {
		this.grammar = grammar;
	}

 	public HashMap<String,ArrayList<ArrayList<String>>> removeRedundants() {
	    	
		//Store first non-terminal for each terminal
		HashMap<String, String> terminalToNonTerminal = new HashMap<>();

		// List for redundant non-terminals
		ArrayList<String> copyNonTerminals = new ArrayList<>();

		// Iterate over the grammar 
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
			
			String nonTerminal = entry.getKey();
			
			for (ArrayList<String> production : entry.getValue()) {
				
				// Check if the productio is single terminal
				if (production.size() == 1 && isLower(production.get(0))) {
					String terminal = production.get(0);

					// Check if this terminal is already exist
					if (terminalToNonTerminal.containsKey(terminal)) {
						
						// Mark the current non-terminal as existed
						copyNonTerminals.add(nonTerminal);
						
						// Replace current non-terminal with the first found
						replaceNonTerminal(nonTerminal, terminalToNonTerminal.get(terminal));
					} else {
						// Store this non-terminal as the first producer of the terminal
						terminalToNonTerminal.put(terminal, nonTerminal);
					}
				}
			}
		}

		// Remove copy of non-terminals from the grammar
		for (String nonTerminal : copyNonTerminals) {
			grammar.remove(nonTerminal);
		}

		return grammar;
	}
	
	 
	private void replaceNonTerminal(String oldNonTerminal, String newNonTerminal) {
		
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
			
			for (ArrayList<String> production : entry.getValue()) {
				
				for (int i = 0; i < production.size(); i++) {
					if (production.get(i).equals(oldNonTerminal)) {
						production.set(i, newNonTerminal);
					}
				}
			}
		}
	}

	//Need to check if letter is lower case i. e terminal
	private boolean isLower(String letter) {
		if (letter.toLowerCase().equals(letter)==true) {
			return true;
		}
		else
			return false;
	}
}
