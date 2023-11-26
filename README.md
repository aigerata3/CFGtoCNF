# CFGtoCNF
Converting CFG to CNF

## Input format
This program accepts an input.txt. Each variable may be a capital letter with any number of digits after it (e.g. B or G232). 
Each terminal may be a lowercase letter with any number of digits after it (e.g. c or a1).
The left hand side and right hand side should be separated by a an arrow ("->") and the symbols on the right hand side should be separated by commas (',').

## Example input
S -> a,S,a\
S -> b,S,b\
S -> lambda\
A -> c,A,c\
A -> lambda\
B -> d\
A -> l, A\
S -> f, C\
C -> d, S\

## To Do
Add eliminate null function
Add eliminate unit function
fix convert to binary function
fix convert pair to binary function
Add input requirements to README
Come up with good test cases
Test on all test cases

## To Do (optional)
Add remove nested start variable function
Add comman line argument parsing for input .txt file
