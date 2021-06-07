import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        lexer.initialize("src/main/resources/Example.txt");
        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);
    }
    public static void printTokens(List<Token> tokens) {
        int i = 0;
        System.out.println(tokens.size());
        for (Token token : tokens) {
            System.out.println((++i) + "   " + token.toString() + "\n");
        }
    }
}