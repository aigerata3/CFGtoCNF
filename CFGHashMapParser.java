import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CFGHashMapParser {
    // Parses file, prints it out, returns grammar as hashmap
    public HashMap<String, ArrayList<ArrayList<String>>> parseFile(String fileName) {
        HashMap<String, ArrayList<ArrayList<String>>> grammar = new HashMap<>();
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("->");
                if (parts.length == 2) {
                    String lhs = parts[0].trim();
                    ArrayList<String> rhs = parseProduction(parts[1]);
                    grammar.putIfAbsent(lhs, new ArrayList<>());
                    grammar.get(lhs).add(rhs);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Parsed Grammar:");
        printGrammar(grammar);
        return grammar;
    }        

    // Parses right hand side, returns list of terms
    private static ArrayList<String> parseProduction(String part) {
        String[] symbols = part.split(",");
        ArrayList<String> production = new ArrayList<>();
        for (String symbol : symbols) {
            production.add(symbol.trim());
        }
        return production;
    }

    // Prints the hashmap grammar
    private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        for (String key : grammar.keySet()) {
            System.out.println("Key: " + key + ", Value: " + grammar.get(key));
        }
    }
}
