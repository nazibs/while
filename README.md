# while
This work is a part of the course homework for Programming Languages. The task is to create an interpreter for the While that allows while loop, if condition, AND and OR logical operators, less than comparator and basic mathematical operations of integers and boolean.


### Requirements:
1. Java 1.8


Following are the requirements of the homework assignment:
1. An Abstract Syntax Tree (AST) of While.
2. A parser for While.
3. Interpreter for the AST.


Below is the flow and description of the Java code:
1. The program will take the input from the standard input and give the output result via standard output.
2. Lexer will go through the input string and tokenize it.
3. Parser will create the Abstract Syntax Tree based on the Grammar of the While.
4. Interpreter will evaluate the created AST and output the result.


### Features added:
Below are the additional features that I have added on top of the basic requirements:
1. There is no restriction of assuming exactly 1 whitespace between characters. The program will remove/ignore any number of whitespaces between each character.
2. The program also allows subtraction and division on top of the addition and multiplication. Although I haven't handled the special cases for the division like divide by 0. The program will return float when the division operand is involved in the input string.
3. The program also allows using greater comparator on top of the less than comparator


### References:
I first implemented the work done for the ARITH(programmed in Python) in Java and then started increasing the operators and features to meet the requirements of While.
My work is mainly based on the tutorial 'Let’s Build A Simple Interpreter' by Ruslan Spivak.

1. Blog's part 1 helped me to get started: https://ruslanspivak.com/lsbasi-part1/
2. Blog's part 4 helped me with precedence for multiplication and division: https://ruslanspivak.com/lsbasi-part4/
3. Blog's part 7 helped me in building the AST: https://ruslanspivak.com/lsbasi-part7/
4. I referred the git repo to get started with the parser for While: https://github.com/versey-sherry/while
5. I also referred the following book: https://craftinginterpreters.com/evaluating-expressions.html







