/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CFGSpecificFormatParser {
    public static void main(String[] args) {
    	//takes input.txt
        String fileName = "input.txt";
        ArrayList<ArrayList<String>> grammar = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<String> production = parseProduction(line);
                grammar.add(production);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       System.out.println("Original Grammar:");
        printGrammar(grammar);
        
     // Convert to CNF
        CNFConverter converter = new CNFConverter(grammar);
        ArrayList<ArrayList<String>> cnfGrammar = converter.convertToCNF();

        System.out.println("Grammar in CNF:");
        printGrammar(cnfGrammar);
    }

    private static ArrayList<String> parseProduction(String line) {
        String[] parts = line.split("->");
        ArrayList<String> production = new ArrayList<>();

        if (parts.length == 2) {
            production.add(parts[0].trim()); // Add the left-hand side
            String[] symbols = parts[1].split(",");
            for (String symbol : symbols) {
                production.add(symbol.trim()); // Add each symbol on the right-hand side
            }
        }

        return production;
    }

    private static void printGrammar(ArrayList<ArrayList<String>> grammar) {
        System.out.println("Parsed Grammar:");
        for (ArrayList<String> production : grammar) {
            System.out.println(production);
        }
    }
}*/
//Hashmap implimitation: 
//ArrayList<ArrayList<String>> to  HashMap<String, ArrayList<ArrayList<String>>> 

/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CFGSpecificFormatParser {
    public static void main(String[] args) {
    	//takes input.txt
        String fileName = "input.txt";
        ArrayList<ArrayList<String>> grammar = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<String> production = parseProduction(line);
                grammar.add(production);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       System.out.println("Original Grammar:");
        printGrammar(grammar);
        
     // Convert to CNF
        CNFConverter converter = new CNFConverter(grammar);
        ArrayList<ArrayList<String>> cnfGrammar = converter.convertToCNF();

        System.out.println("Grammar in CNF:");
        printGrammar(cnfGrammar);
    }

    private static ArrayList<String> parseProduction(String line) {
        String[] parts = line.split("->");
        ArrayList<String> production = new ArrayList<>();

        if (parts.length == 2) {
            production.add(parts[0].trim()); // Add the left-hand side
            String[] symbols = parts[1].split(",");
            for (String symbol : symbols) {
                production.add(symbol.trim()); // Add each symbol on the right-hand side
            }
        }

        return production;
    }

    private static void printGrammar(ArrayList<ArrayList<String>> grammar) {
        System.out.println("Parsed Grammar:");
        for (ArrayList<String> production : grammar) {
            System.out.println(production);
        }
    }
}*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CFGSpecificFormatParser {
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

