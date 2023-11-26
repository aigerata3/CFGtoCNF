import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//converts Grammar to CNF
public class CNFConverter {
	 //private ArrayList<ArrayList<String>> grammar;
	 private HashMap<String,ArrayList<ArrayList<String>>> grammar;

	/*
	CNFConverter(ArrayList<ArrayList<String>> grammar) {
		this.grammar = grammar;
	}*/


	public CNFConverter(HashMap<String,ArrayList<ArrayList<String>>> grammar) {
		this.grammar = grammar;
	}
	
	HashMap<String,ArrayList<ArrayList<String>>> convertToCNF() {
	        eliminateNull();
	        eliminateUnit();
	        convertToBinary();
	        return grammar;
	}
		
	/*ArrayList<ArrayList<String>> convertToCNF() {
		eliminateNull();
		eliminateUnit();
		convertToBinary();
		return grammar;
	}*/

	private void eliminateNull() {
		// Implementation...
	}
	private void eliminateUnit() {
		// Implementation...
	}

	private void convertToBinary() {
		// Implementation...
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
