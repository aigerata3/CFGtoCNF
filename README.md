# CFGtoCNF
**Converting Context-Free Grammar (CFG) to Chomsky Normal Form (CNF).**

## Usage
#### **Compiling the Program:**
Compile all Java files in the directory:
```bash
javac *.java
```
#### **Running the Program:**
Run the program with an input file:
```bash
java CFGParser [input-file-name]
```
Replace [input-file-name] with the name of your input file, for example, input.txt.

#### **Verbose Mode:**
To run the program in verbose mode, which prints out intermediate steps:
```bash
java CFGParser [input-file-name] --verbose
```

## Input Format
The program accepts input in a .txt file format. They should be placed in the input directory and the expected format is as follows:

- Variables: Any capital letter followed by any reasonable number of digits (e.g., B or G232).
- Terminals: Any lowercase letter followed by any reasonable number of digits (e.g., c or a1).
- Productions: Only production per line, please. The left-hand side (LHS) and right-hand side (RHS) of a production should be separated by an arrow (->). Symbols on the RHS should be separated by commas (,). Each production should be on a new line. Please avoid extra spaces.

## Example Input:
```
S -> a,S,a
S -> b,S,b
S -> lambda
A -> c,A,c
A -> lambda
B -> d
A -> l,A
S -> f,C
C -> d,S
```
