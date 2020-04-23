JC = Java
JFLAGS = -g
.SUFFIXES: .java .class
# .java.class: $(JC) $(JFLAGS) $*.java

Default: While.class ASTNode.class Interpreter.class Lexer.class Parser.class ParsingException.class Token.class

While.class: While.java $(JCC) $(JFLAGS) While.java

ASTNode.class: While.java $(JCC) $(JFLAGS) While.java

Interpreter.class: While.java $(JCC) $(JFLAGS) While.java

Lexer.class: While.java $(JCC) $(JFLAGS) While.java

Parser.class: While.java $(JCC) $(JFLAGS) While.java

ParsingException.class: While.java $(JCC) $(JFLAGS) While.java

Token.class: While.java $(JCC) $(JFLAGS) While.java

clean: $(RM) *.class