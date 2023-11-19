import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//converts Grammar to CNF
public class CNFConverter {
	 private ArrayList<ArrayList<String>> grammar;

	    CNFConverter(ArrayList<ArrayList<String>> grammar) {
	        this.grammar = grammar;
	    }
	    ArrayList<ArrayList<String>> convertToCNF() {
	        eliminateNull();
	        eliminateUnit();
	        convertToBinary();
	        return grammar;
	    }
	    private void eliminateNull() {
	        // Implementation...
	    }
	    private void eliminateUnit() {
	        // Implementation...
	    }

	    private void convertToBinary() {
	        // Implementation...
	    
		}

}
