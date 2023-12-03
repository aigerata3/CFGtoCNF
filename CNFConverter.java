import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//converts Grammar to CNF
public class CNFConverter {
	//private ArrayList<ArrayList<String>> grammar;
	private HashMap<String,ArrayList<ArrayList<String>>> grammar;

	public CNFConverter(HashMap<String,ArrayList<ArrayList<String>>> grammar) {
		this.grammar = grammar;
	}
	
	HashMap<String,ArrayList<ArrayList<String>>> convertToCNF() {
	        // eliminateNull();
	        eliminateUnit();
	        // convertToBinary();
			// convertPairToBinary();
	        return grammar;
	}

	// Vicki's Function
	private void eliminateNull() {
		// Implementation...
	}

	// Erin's Function
	private void eliminateUnit() {
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

	// Rachel's function
	private void convertToBinary() {
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
		grammar = newGrammar;
	}
	
	private void convertPairToBinary() {
	    	//idea: Convert S->a,B to S->AB and A ->a
	    	
	    	//NewProduc to Store new production 
	    	HashMap<String,ArrayList<ArrayList<String>>> newProduc = new HashMap<>();
	    	
	    	int counter =1;
	    	
	    	//iteration through grammar
	    	//Each production: key(left hand side)and value(right hand side)
	    	for(Map.Entry<String,ArrayList<ArrayList<String>>> entry: grammar.entrySet()) {
	    		
	    		ArrayList<ArrayList<String>> updateProd = new ArrayList<>();
	    		 
	    		//check pattern if :S->a,B
	    		for (ArrayList<String> production : entry.getValue()) {
	    			
	    			//check production size is 2 and first letter is lower case
	    		    if (production.size() == 2 && isLower(production.get(0))) {
	    		    	
	    		    	//if yes, generate new nonterminal letter
	    		    	//Convert S->a, B to S->AB, i.e. made from "a" to "A"
	    		    	String newLetter = "X" + counter++; 
	    		    	ArrayList<String> newProduction = new ArrayList<>();
	    		    	newProduction.add(production.get(0));
	    		    	
	    		    	//replace the original production to
	    		    	//S ->AB
	    		        updateProd.add(new ArrayList<>(Arrays.asList(newLetter, production.get(1))));
	    		        
	    		        //add new production that we removed i.e. 
	    		        //add A ->a
	    		        newProduc.computeIfAbsent(newLetter, k -> new ArrayList<>()).add(newProduction);
	                } else {
	                	//if no need to change leave it as it is
	                	updateProd.add(production);
	    		    }
	    		}
	    		//replace the original productions for each variable 
	    		//with what we updated 
	    		entry.setValue(updateProd);
	    
	    	}
	    	//Add all new original we updated to our grammar
	    	grammar.putAll(newProduc);
		}
	//Need to check if letter is lower case i. e terminal
	private boolean isLower(String letter) {
		if (letter.toLowerCase().equals(letter)==true) {
			return true;
		}
	    	else{
	    		return false;
		}
	}
		

}
