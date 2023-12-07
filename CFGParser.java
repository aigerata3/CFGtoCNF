
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CFGParser {
    public static void main(String[] args) {
        String fileName = "input.txt";
        HashMap<String, ArrayList<ArrayList<String>>> grammar = new HashMap<>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Map.Entry<String, ArrayList<String>> production = parseProduction(line);
                grammar.computeIfAbsent(production.getKey(), k -> new ArrayList<>()).add(production.getValue());
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printGrammar(grammar);
        
        // Convert to CNF
        CNFConverter converter = new CNFConverter(grammar);
        HashMap<String,ArrayList<ArrayList<String>>> cnfGrammar = converter.convertToCNF();

        System.out.println("Grammar in CNF:");
        printGrammar(cnfGrammar);
    }

    // WRITE FUNCTION TO CHECK FOR WHITESPACE

    private static Map.Entry<String, ArrayList<String>> parseProduction(String line) {
        String[] parts = line.split("->");
        if (parts.length == 2) {
            String key = parts[0].trim();
            ArrayList<String> value = new ArrayList<>(Arrays.asList(parts[1].trim().split(",")));
            return new AbstractMap.SimpleEntry<>(key, value);
        }
        return null;
    }

    private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar) {
        System.out.println("Parsed Grammar:");
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

