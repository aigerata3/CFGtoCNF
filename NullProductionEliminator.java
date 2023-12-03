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
        System.out.println("Eliminating...");
        return grammar;
    }
}
