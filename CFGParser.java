
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
import java.util.stream.Collectors;

public class CFGParser {
    // Input parser by Aigerim and Rachel
    public static void main(String[] args) {
        // Use the command line argument as the input file, or default to "input.txt"
        String fileName = "input.txt";
        boolean verbose = false;
        // Parse command line arguments
        for (String arg : args) {
            if (arg.equals("--verbose") || arg.equals("-v")) {
                verbose = true;
            } else {
                fileName = arg; // Assuming the other argument is the file name
            }
        }

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
        CNFConverter converter = new CNFConverter(grammar, verbose);
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
            // Joining inner lists with commas and outer lists with |
            String rules = entry.getValue().stream()
                .map(rule -> String.join(",", rule))
                .collect(Collectors.joining(" | "));
            System.out.println(entry.getKey() + " -> " + rules);
        }
    }

    private static void printGrammarToFile(HashMap<String, ArrayList<ArrayList<String>>> grammar,
            String outputFileName) {
        try {
            FileWriter fileWriter = new FileWriter(outputFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : grammar.entrySet()) {
                // Joining inner lists with commas and outer lists with |
                String rules = entry.getValue().stream()
                        .map(rule -> String.join(",", rule))
                        .collect(Collectors.joining(" | "));
                bufferedWriter.write(entry.getKey() + " -> " + rules + "\n");
            }

            bufferedWriter.close();
            System.out.println("Output written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

