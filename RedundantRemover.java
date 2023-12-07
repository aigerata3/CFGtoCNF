import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SingleTerminalConverter {
    private HashMap<String,ArrayList<ArrayList<String>>> grammar;

	public SingleTerminalConverter(HashMap<String,ArrayList<ArrayList<String>>> grammar) {
		this.grammar = grammar;
	}
