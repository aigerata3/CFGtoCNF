import java.util.ArrayList;
import java.util.HashMap;

public class NullProductionEliminator {
    private HashMap<String, ArrayList<ArrayList<String>>> grammar;
    // Constructor
    public NullProductionEliminator(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        this.grammar = grammar;
    }

    // Vicki's Function
    public HashMap<String, ArrayList<ArrayList<String>>> eliminateNull() {
        // Implementation...
        System.out.println("Eliminating nulls...");
        // A *-> lambda
        // 1. Find all nullable variables that derive lambda
        // Initialize a set for nullable variables
        // Initialize a prev set for variables
        // Do while variable set and prev variable set are different
            // Deepcopy variable set to prev variable set
            // For each lhs (variable) in the grammar
                // For each rhs of the production for lhs
                    // If the rhs is all nullable variables
                        // Add lhs to nullable variables list

        // 2. For each production A -> a, construct productions A -> x where x is obtained from 'a'
        // by removing one or multiple variables from step 1

        // 3. Combine the original productions with the results of step 2 and remove lambda productions
        return grammar;
    }
}
