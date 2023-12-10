
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CFGParser {
    // Input parser by Aigerim
    public static void main(String[] args) {
        // Use the first command line argument as the input file, or default to "input.txt"
        String fileName = (args.length > 0) ? args[0] : "input.txt"; 
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

        printGrammar(grammar, "Parsed input grammar: ");
        
        // Convert to CNF
        CNFConverter converter = new CNFConverter(grammar);
        HashMap<String,ArrayList<ArrayList<String>>> cnfGrammar = converter.convertToCNF();
        // Output new grammar to output file
        printGrammarToFile(cnfGrammar, "output.txt");

        printGrammar(cnfGrammar, "Grammar in CNF:");
    }

    private static Map.Entry<String, ArrayList<String>> parseProduction(String line) {
        String[] parts = line.split("->");
        if (parts.length == 2) {
            String key = parts[0].trim();
            ArrayList<String> value = new ArrayList<>(Arrays.asList(parts[1].trim().split(",")));
            return new AbstractMap.SimpleEntry<>(key, value);
        }
        return null;
    }

    private static void printGrammar(HashMap<String, ArrayList<ArrayList<String>>> grammar, String heading) {
        System.out.println(heading);
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Output function by Rachel
    private static void printGrammarToFile(HashMap<String, ArrayList<ArrayList<String>>> grammar, String outputFileName) {
        try {
            FileWriter fileWriter = new FileWriter(outputFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
                bufferedWriter.write(entry.getKey() + " -> " + entry.getValue() + "\n");
            }

            bufferedWriter.close();
            System.out.println("Output written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

