public class Token{
    String type;
    String value;

    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }
}

public class ParsingException extends Exception {
    public ParsingException(String errorMessage) {
        super(errorMessage);
        System.out.println("Error in Parsing");
    }
}

public class Lexer{
    String text;
    char currentChar;
    int pos;

    public Lexer(String text){
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    public static boolean isDigit(char charNum) {
        if (strNum == null)
            return false;
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void goToNextCharacter(){
        this.pos++;
        if(this.pos > this.text.length()-1)
            this.currentChar = null;
        else
            this.currentChar = text.charAt(pos);
    }

    public skipWhiteSpaces(){
        while(this.currentChar != null && this.currentChar == ' ')
            goToNextCharacter();
    }

    public getCompleteInteger(){
        StringBuilder completeInt = new StringBuilder();
        while(this.currentChar != null && Character.isDigit(this.currentChar)) {
            completeInt.append(this.currentChar);
            goToNextCharacter();
        }
        return Integer.parseInt(completeInt.toString())
    }

}




public class While{

    static String INTEGER="INTEGER", PLUS="PLUS", MINUS="MINUS", MUL="MUL", DIV="DIV", SPACE="SPACE", EOF="EOF";

    public static void main(String args[]){

        String text = "4+5";
    }




}




