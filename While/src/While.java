import java.io.EOFException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Token{
    String type;
    String value;

    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }
}

class ParsingException extends Exception {
    public ParsingException(String errorMessage) {
        super(errorMessage);
    }
}

class ASTNode{
	Token token;
	ASTNode left, right, node, condition, trueStatement, falseStatement;
	
	public ASTNode(Token value) {
		this.token = value;
		left = right = null;
	}
	public ASTNode(ASTNode left, Token value, ASTNode right) {
		this.token = value;
		this.left = left;
		this.right = right;
	}
	public ASTNode(Token value, ASTNode node) {
		this.token = value;
		this.node = node;
	}
//	Statements (While and If)
	public ASTNode(Token value, ASTNode condition, ASTNode trueStatement, ASTNode falseStatement) {
		this.token = value;
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = falseStatement;
	}
}


class Lexer{
    String text;
    char currentChar;
    int pos;

    public Lexer(String text){
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    public static boolean isDigit(char charNum) {
    	if (charNum == '#')
            return false;
        try {
            int d = Integer.parseInt(""+charNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void goToNextCharacter(){
        this.pos++;
        if(this.pos > this.text.length()-1)
            this.currentChar = '#';
        else
            this.currentChar = text.charAt(pos);
    }

    public void skipWhiteSpaces(){
    	
        while(this.currentChar != '#' && this.currentChar == ' ')
            goToNextCharacter();
    }

    public int getCompleteInteger(){
        StringBuilder completeInt = new StringBuilder();
        while(this.currentChar != '#' && Character.isDigit(this.currentChar)) {
            completeInt.append(this.currentChar);
            goToNextCharacter();
        }
//        While.print("Complete Integer: " + completeInt.toString());
        return Integer.parseInt(completeInt.toString());
    }

    public Token getNextToken() throws ParsingException {
    	String text = this.text;
//    	char currenChar = this.currentChar;
    	
    	if(this.pos > text.length()-1) {
    		this.currentChar = '#';
            return new Token(While.EOF, null);
    	}
    	while(this.currentChar != '#') {
    		
    		While.print("Current Char: " + this.currentChar);
    		
    		if(Character.isDigit(this.currentChar))
    			return new Token(While.INTEGER, "" + getCompleteInteger());
    		if(this.currentChar == ' ') {
    			skipWhiteSpaces();
    			continue;
    		}
    		if(this.currentChar == '+') {
    			Token token = new Token(While.PLUS, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '-') {
    			Token token = new Token(While.MINUS, ""+this.currentChar);
    			goToNextCharacter();
//    			Handling Negative Numbers
    			if(Character.isDigit(this.currentChar))
    				return new Token(While.INTEGER, "" + -1*getCompleteInteger());
    			return token;
    		}
    		if(this.currentChar == '*') {
    			Token token = new Token(While.MUL, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '/') {
    			Token token = new Token(While.DIV, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		} 
    		if(this.currentChar == '(') {
    			Token token = new Token(While.LBRAC, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == ')') {
    			Token token = new Token(While.RBRAC, ""+this.currentChar);
    			goToNextCharacter();
//    			return token;
    			continue;
    		}
    		if(this.currentChar == '{') {
    			Token token = new Token(While.LCBRAC, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '}') {
    			Token token = new Token(While.RCBRAC, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '<') {
    			Token token = new Token(While.LESS, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '>') {
    			Token token = new Token(While.GREATER, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '∧') {
    			Token token = new Token(While.AND, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '=') {
    			Token token = new Token(While.EQUAL, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '¬') {
    			Token token = new Token(While.NOT, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == ':') {
    			goToNextCharacter();
    			Token token = null;
    			if(this.currentChar == '=')
    				token = new Token(While.ASSIGN, ":="); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '∨') {
    			Token token = new Token(While.OR, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == ';') {
    			Token token = new Token(While.END, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(Character.isLetter(this.currentChar)) {
    			StringBuilder completeWord = new StringBuilder();
    			while(this.currentChar != '#' && (Character.isLetter(this.currentChar) || Character.isDigit(this.currentChar))){
    				completeWord.append(this.currentChar);
    	            goToNextCharacter();
    	        }
    			
    			String actionWord =  completeWord.toString();
//    			While.print("Complete Word: " + actionWord);
    			
    			if(actionWord.toLowerCase().equals("if"))
    				return new Token(While.IF, "IF");
    			if(actionWord.toLowerCase().equals("then"))
    				return new Token(While.THEN, "THEN");
    			if(actionWord.toLowerCase().equals("else"))
    				return new Token(While.ELSE, "ELSE");
    			if(actionWord.toLowerCase().equals("skip"))
    				return new Token(While.SKIP, "SKIP");
    			if(actionWord.toLowerCase().equals("while"))
    				return new Token(While.WHILE, "WHILE");
    			if(actionWord.toLowerCase().equals("do"))
    				return new Token(While.DO, "DO");
    			if(actionWord.toLowerCase().equals("true"))
    				return new Token(While.TRUE, "TRUE");
    			if(actionWord.toLowerCase().equals("false"))
    				return new Token(While.FALSE, "FALSE");
    			else
    				return new Token(While.VARIABLE, actionWord);
    		}
    		
    		throw new ParsingException("Error in Parsing for character: " + this.currentChar);
    	}
    	return new Token(While.EOF, null);
    } 
}



class Parser{
	Lexer lexer;
	Token currentToken;
	
	public Parser(Lexer lexer) throws ParsingException {
		this.lexer = lexer;
		currentToken = lexer.getNextToken();
	}
	
	public void eat(String tokenType) throws ParsingException {
		
		While.print("Eating " + tokenType);
		
		if(this.currentToken.type == tokenType)
			this.currentToken = this.lexer.getNextToken();
		else
			throw new ParsingException("Error in Parsing"); 
		
		While.print("After eating " + this.currentToken.type + " :: " + this.currentToken.value);
	}
	
//	Factor
	public ASTNode evalNum() throws ParsingException {
		While.print("Entered evalNum");
		ASTNode node = null;
		
		While.print("Token Type: " + this.currentToken.type);
		if(this.currentToken.type == While.LBRAC || this.currentToken.type == While.RBRAC)
			eat(this.currentToken.type);
			
		if(this.currentToken.type == While.INTEGER) {
			node = new ASTNode(currentToken);
			eat(While.INTEGER);
		}
		else if(this.currentToken.type == While.VARIABLE) {
			node = new ASTNode(currentToken);
			eat(While.VARIABLE);
		}
		else if(this.currentToken.type == While.NOT) {
			node = new ASTNode(currentToken);
			eat(While.NOT);
			if(this.currentToken.type == While.LBRAC) {
				eat(While.LBRAC);
				ASTNode temp = computeAndOr();
				While.print("TESTing NOT || Node from computeAndOr: type: " + temp.token.type + " :: value: " + temp.token.value);
				node.right = temp;
			}
			else if(this.currentToken.type == While.TRUE){
				While.print("TESTing NOT || Node from True:");
				node = new ASTNode(new Token(While.FALSE, "FALSE"));
				eat(this.currentToken.type);
			}
			else if(this.currentToken.type == While.FALSE) {
				While.print("TESTing NOT || Node from False:");
				node = new ASTNode(new Token(While.TRUE, "TRUE"));
				eat(this.currentToken.type);
			}
			else
				throw new ParsingException("Error in Parsing");
			
//			node = new ASTNode(this.currentToken, node);
		}
		else if(this.currentToken.type == While.TRUE || this.currentToken.type == While.FALSE) {
			node = new ASTNode(currentToken);
			eat(this.currentToken.type);
		}
		else if(this.currentToken.type == While.LBRAC) {
			eat(this.currentToken.type);
			node = computeAndOr();
		}	
		else if(this.currentToken.type == While.RBRAC) {
			eat(this.currentToken.type);
		}	
		else if(this.currentToken.type == While.LCBRAC) {
			eat(this.currentToken.type);
			node = computeStatements();
		}	
		else if(this.currentToken.type == While.RCBRAC) {
			eat(this.currentToken.type);
		}	
		else if(this.currentToken.type == While.SKIP) {
			node = new ASTNode(currentToken);
		}
		else if(this.currentToken.type == While.WHILE) {
			Token whileToken = this.currentToken;
			eat(this.currentToken.type);
			ASTNode condition = computeAndOr();
			
			While.print("TEST | Inside While: condition token " + condition.token.type + " :: " + condition.token.value);
			
			
			ASTNode whileExit = new ASTNode(new Token("SKIP", While.SKIP));
			ASTNode whileTrue = null;
			
			if(this.currentToken.type == While.RBRAC)
				eat(this.currentToken.type);
			
			While.print("TEST | do check: " + this.currentToken.type);
			
			if(this.currentToken.type == While.DO) {
				eat(this.currentToken.type);
				
				if(this.currentToken.type == While.LCBRAC)
					whileTrue = computeStatements();
				else
					whileTrue = computeAssignments();
				
				While.print("TEST | While True assignment check: " + whileTrue.token.value);
			}
			return new ASTNode(whileToken, condition, whileTrue, whileExit);
		}
		
		else if(this.currentToken.type == While.IF) {
			Token ifToken = this.currentToken;
			While.print("TEST | Inside if: current token" + this.currentToken.type);
			eat(this.currentToken.type);
			ASTNode condition = computeAndOr();
			
			While.print("TEST | Inside if: condition token" + condition.token.type + " :: " + condition.token.value);
			
			ASTNode ifFalse = null;
			ASTNode ifTrue = null;
			
			While.print("TEST | after eating if: current token" + this.currentToken.type);
			if(this.currentToken.type == While.THEN && !condition.token.value.equals(While.FALSE)) {
				While.print("Computing the true statement of If");
				eat(this.currentToken.type);
				ifTrue = computeStatements();
			}
			While.print("TEST | after true: current token" + this.currentToken.type);
			if(this.currentToken.type == While.ELSE || condition.token.value.equals(While.FALSE)) {
				While.print("Computing the false statement of If");
				while(this.currentToken.type != While.ELSE)
					eat(this.currentToken.type);
				eat(this.currentToken.type);
				ifFalse = computeStatements();
			}
			return new ASTNode(ifToken, condition, ifTrue, ifFalse);
			
		}	
		else
			throw new ParsingException("Error in Parsing");
		
		While.print("Here near end of eating method");
//		eat(this.currentToken.type);
		While.print("Returning from evalNum with node: " + node.token.type);
		return node;
	}
	
//	aterm
	public ASTNode computeMulDiv() throws ParsingException {
		While.print("Entered computeMulDiv");
		ASTNode node = evalNum();
		Token token;
		while(this.currentToken.type == While.MUL || this.currentToken.type == While.DIV) {
			token = this.currentToken;
			if(this.currentToken.type == While.MUL) {
				eat(While.MUL);
			}
			else if(this.currentToken.type == While.DIV) {
				eat(While.DIV);
			}
			
			node = new ASTNode(node, token, evalNum());
		}
		
		While.print("Returning from computeMulDiv");
		return node;
	}
	
//	aexpr
	public ASTNode computeAddSub() throws ParsingException {
		While.print("Entered computeAddSub");
		ASTNode node = computeMulDiv();
		
//		While.print("Node Value after MULDIV: " + node.token.type + " :: " + node.token.value);
//		While.print("Current Token Value after MULDIV: " + this.currentToken.type + " :: " + this.currentToken.value);
		
		Token token;
		while(this.currentToken.type == While.PLUS || this.currentToken.type == While.MINUS) {
			token = this.currentToken;
			if(this.currentToken.type == While.PLUS) {
				eat(While.PLUS);
			}
			else if(this.currentToken.type == While.MINUS) {
				eat(While.MINUS);
			}
			
			node = new ASTNode(node, token, computeMulDiv());
		}
		
		While.print("Returning from computeAddSub");
		return node;
	}
	
	
//	BTerm
	public ASTNode computeCondition() throws ParsingException {
		While.print("Entered computeCondition");
		ASTNode node = computeAddSub();
		
		Token token;
		if(this.currentToken.type == While.EQUAL || this.currentToken.type == While.LESS || this.currentToken.type == While.GREATER) {
			token = this.currentToken;
			eat(this.currentToken.type);
			
			node = new ASTNode(node, token, computeAddSub());
		}
		
		While.print("Returning from computeCondition");
		return node;
	}
	

//	Bexpr
	public ASTNode computeAndOr() throws ParsingException {
		While.print("Entered computeAndOr");
		ASTNode node = computeCondition();
		
		Token token;
		while(this.currentToken.type == While.AND || this.currentToken.type == While.OR) {
			token = this.currentToken;
			eat(this.currentToken.type);
			
			node = new ASTNode(node, token, computeCondition());
		}
		While.print("Returning from computeAndOr");
		return node;
	}
	
//	CTerm
	public ASTNode computeAssignments() throws ParsingException {
		While.print("Entered computeAssignments");
		ASTNode node = computeAndOr();
		
		While.print("computeStatements :: left node: " + node.token.type + " :: left node.val: " + node.token.value);
		
		Token token;
		While.print("computeAssignments :: currentToken.type: " + this.currentToken.type + " :: this.currentToken val" + this.currentToken.value);
		if(this.currentToken.type == While.ASSIGN) {
			While.print("Inside assign condition");
			token = this.currentToken;
			eat(this.currentToken.type);
			
			While.print("computeStatements :: token: " + token.type + " :: token.val: " + token.value);
			
			
			node = new ASTNode(node, token, computeAndOr());
		}
		
		return node;
	}
	
//	Cexpr
	public ASTNode computeStatements() throws ParsingException {
		While.print("Entered computeStatements");
		ASTNode node = computeAssignments();
		
		Token token;
		while(this.currentToken.type == While.END) {
			token = this.currentToken;
			eat(this.currentToken.type);
			
			node = new ASTNode(node, token, computeAssignments());
		}
		
		return node;
	}
	
}

class Interpreter{
	ASTNode parserAST;
	
	public Interpreter(ASTNode ast) {
		this.parserAST = ast;
	}
	
	public String getResultString(HashMap<String, String> currentVariables) {
		StringBuilder result = new StringBuilder();
		result.append('{');
		
		for(Map.Entry<String, String> variable : currentVariables.entrySet()) {
			result.append(variable.getKey() + " → " + variable.getValue() + ", ");
		}
		While.print("TEST | in getResultString :: Result: " + result);
		
		if(!currentVariables.isEmpty())
			result.delete(result.length()-2,result.length());
		
		result.append('}');
		return result.toString();
	}
	
	public String interpret(ASTNode node, HashMap<String, String> currentVariables) throws ParsingException {
		
		While.print("Interpreting token.type: " + node.token.type);
		if(node.token.type == While.INTEGER) {
			return node.token.value;
		}
		else if(node.token.type == While.VARIABLE) {
			return currentVariables.getOrDefault(node.token.value, ""+0);
		}
		else if(node.token.type == While.SKIP) {
			return getResultString(currentVariables);
		}
		else if(node.token.type == While.END) {
			interpret(node.left, currentVariables);
			interpret(node.right, currentVariables);
			return getResultString(currentVariables);
		}
		else if(node.token.type == While.ASSIGN) {
			Token variable = node.left.token;
//			if(currentVariables.containsKey(variable.value))
				currentVariables.put(variable.value, interpret(node.right, currentVariables));
//			else
				return getResultString(currentVariables);
		}
		else if(node.token.type == While.PLUS) {
			return("" + (Integer.parseInt(interpret(node.left, currentVariables)) + Integer.parseInt(interpret(node.right, currentVariables))));
		}
		else if(node.token.type == While.MINUS) {
			return("" + (Integer.parseInt(interpret(node.left, currentVariables)) - Integer.parseInt(interpret(node.right, currentVariables))));
		}
		else if(node.token.type == While.MUL) {
			return("" + (Integer.parseInt(interpret(node.left, currentVariables)) * Integer.parseInt(interpret(node.right, currentVariables))));
		}
		else if(node.token.type == While.DIV) {
			return("" + (Integer.parseInt(interpret(node.left, currentVariables)) / Integer.parseInt(interpret(node.right, currentVariables))));
		}
		else if(node.token.type == While.NOT) {
			String notRightCalculation = interpret(node.right, currentVariables);
			While.print("NOT Right child calculation: " + notRightCalculation);
			if(notRightCalculation == While.TRUE)
				return While.FALSE;
			else
				return While.TRUE;
		}
		else if(node.token.type == While.EQUAL) {
			if(interpret(node.left, currentVariables).equals(interpret(node.right, currentVariables)))
				return While.TRUE;
			else
				return While.FALSE;
		}
		else if(node.token.type == While.LESS) {
			if(Integer.parseInt(interpret(node.left, currentVariables)) < Integer.parseInt(interpret(node.right, currentVariables)))
				return While.TRUE;
			else
				return While.FALSE;
		}
		else if(node.token.type == While.GREATER) {
			if(Integer.parseInt(interpret(node.left, currentVariables)) > Integer.parseInt(interpret(node.right, currentVariables)))
				return While.TRUE;
			else
				return While.FALSE;
		}
		else if(node.token.type == While.AND) {
			if(interpret(node.left, currentVariables) == While.TRUE && interpret(node.right, currentVariables) == While.TRUE)
				return While.TRUE;
			else
				return While.FALSE;
		}
		else if(node.token.type == While.OR) {
			if(interpret(node.left, currentVariables) == While.TRUE || interpret(node.right, currentVariables) == While.TRUE)
				return While.TRUE;
			else
				return While.FALSE;
		}
		
//		###################################################
//		TO BE CHECKED
		else if(node.token.type == While.TRUE) {
			return While.TRUE;
		}
		else if(node.token.type == While.FALSE) {
			return While.FALSE;
		}
//		###################################################
		
		else if(node.token.type == While.WHILE) {
			
			ASTNode conditionalNode = node.condition;
			
			String conditionCalculation = While.TRUE;
			int count=0;
			while(conditionCalculation == While.TRUE) {
				
				While.print("While loop Counter: " + count++);
				conditionCalculation = interpret(node.condition, currentVariables);
				
				While.print("TESTING conditionalNode: " + conditionalNode.token.type);
//				While.print("TESTING conditionalNode: " + conditionalNode.token.type);
				
				
				if(conditionCalculation == While.TRUE)
					While.print("conditionCalculation == While.TRUE");
				
				if(!(conditionalNode.token.type.equals(While.NOT)))
					While.print("!(conditionalNode.token.type.equals(While.NOT))");
				
				if(conditionCalculation == While.TRUE) {
					While.print("Reinterpreting True Statement");
//					return interpret(node.trueStatement, currentVariables);
					interpret(node.trueStatement, currentVariables);
				}
//				else {
//					While.print("Reinterpreting False Statement");
//					
//				}
			}
			if(count == 0) {
				While.print("While loop Counter 0 i.e. false condition in While");
				return interpret(node.falseStatement, currentVariables);
			}
			
			return getResultString(currentVariables);
		}
		else if(node.token.type == While.IF) {
			
			if(interpret(node.condition, currentVariables) == While.TRUE)
				return interpret(node.trueStatement, currentVariables);
			else
				return interpret(node.falseStatement, currentVariables);
		}
		else
			throw new ParsingException("Error in Parsing :: Token Type: " + node.token.type);
	}
	
	public String calculate() throws ParsingException {
		
		HashMap<String, String> currentVariables = new HashMap<String, String>();
		
		return interpret(this.parserAST, currentVariables);
	}
}
	
// Medium 10-Null Pointer, 15-error in getNextToken
// Hard 2-NullPointer, 3, 4-Parsing, 8-NullPointer, 10-NP, 11-Parsing, 12-NP, 13-NP, 15-Parsing, 16-NP, 18-NP

// Hard -3 --> Bracket Precedence



public class While{

    static String INTEGER="INTEGER", PLUS="PLUS", MINUS="MINUS", MUL="MUL", DIV="DIV", SPACE="SPACE", EOF="EOF";
    static String LBRAC="LBRAC", RBRAC="RBRAC", LCBRAC="LCBRAC", RCBRAC="RCBRAC";
    static String LESS="LESS", GREATER="GREATER", EQUAL="EQUAL", AND="AND", OR="OR", END="END", ASSIGN="ASSIGN", NOT="NOT";
    static String IF="IF", THEN="THEN", ELSE="ELSE", SKIP="SKIP", WHILE="WHILE", DO="DO", TRUE="TRUE", FALSE="FALSE", VARIABLE="VAR";
    
    static Boolean WannaPrint = false;
    
    public static void print(String message) {
    	if(WannaPrint)
    		System.out.println(message);
    }
    
    
    public static void main(String args[]) throws ParsingException, EOFException{

    	Scanner sc = new Scanner(System.in);
//    	while(true) {
    		String text = sc.nextLine();
			
    		if(text.equals("skip")) {
    			While.print("{}");
//    			continue;
    		}
    		
    		Lexer lexer = new Lexer(text);
			Parser parser = new Parser(lexer);
			ASTNode ast = parser.computeStatements();
			
			
            While.print("AST Root Node: " + ast.token.value);
            if(ast.left != null)
            	While.print("AST Root Left child: " + ast.left.token.value);
            if(ast.right != null)
            	While.print("AST Root Right child: " + ast.right.token.value);
            if(ast.condition != null) {
            	While.print("AST condition: " + ast.condition.token.value);
//            	While.print("AST condition left: " + ast.condition.left.token.value);
//            	While.print("AST condition right: " + ast.condition.right.token.value);
            }
            if(ast.trueStatement != null)
            	While.print("AST trueStatement: " + ast.trueStatement.token.value);
            if(ast.falseStatement != null)
            	While.print("AST falseStatement: " + ast.falseStatement.token.value);
            
            
			Interpreter interpreter = new Interpreter(ast);
			String result = interpreter.calculate();
			System.out.println(result);
//    	}

    }

}
