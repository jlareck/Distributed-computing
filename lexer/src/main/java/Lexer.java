import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class Lexer {
    String fileName;
    char[] inputText;
    int indexOfLetter = 0;
    States state = States.INITIAL;
    private StringBuilder buffer;
    private List<Token> tokens;

    public Lexer(String fileName) {
        this.fileName = fileName;
        tokens = new ArrayList<>();
        buffer = new StringBuilder();
    }

    public void initialize(String path) {
        File f = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream, Charset.defaultCharset());
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        String line = "";
        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(line);
        }

        inputText = stringBuilder.toString().toCharArray();

        System.out.println(inputText);
    }

    public List<Token> tokenize() {
        indexOfLetter = 0;
        while (indexOfLetter < inputText.length) {
            char c = inputText[indexOfLetter];
            switch (state) {
                case ERROR: {
                    invalidState(c);
                }
                case INITIAL: {
                    initialState(c);
                    break;
                }
                case IDENTIFIER_NOT_LITERAL: {
                    identifierState(c);
                }
            }
        }
    }
    private void identifierLiteralState(Character character) {
        if (character != '`') {
            addToBuffer(character, States.IDENTIFIER_LITERAL);
        }
        else {
            addToBuffer(character, States.IDENTIFIER_LITERAL);
            addToken(buffer.toString(), Type.IDENTIFIER);
            state = States.INITIAL;
        }
    }
    private void identifierNotLiteralState(Character character) {
        if (buffer.length() == 1 && buffer.charAt(0) == 's' && character == '\"') {
            addToBuffer(character, States.STRING_LITERAL);
        }
        else if (Character.isJavaIdentifierPart(character)) {
            addToBuffer(character, States.IDENTIFIER_NOT_LITERAL);
        }
        else if (Character.isWhitespace(character) || Utils.isOperator(character) ||
                Utils.isParentheses(character) || Utils.isSpecialCharacter(character)) {
            if (Utils.specialReservedWords(buffer.toString())) {
                addToken(buffer.toString(), Type.RESERVED_WORDS);
            }
            else if (Utils.isBooleanLiteral(buffer.toString())) {
                addToken(buffer.toString(), Type.BOOLEAN_LITERAL);
            }
            else if (character == '#') {
                addToken(buffer.toString(), Type.IDENTIFIER);
                addToBuffer(character, States.POUND_SIGN);
                return;
            }
            else if (character == '@') {
                addToken(buffer.toString(), Type.IDENTIFIER);
                addToBuffer(character, States.ANNOTATION_SIGN);
                return;
            }
            else {
                addToken(buffer.toString(), Type.IDENTIFIER);
            }
            state = States.INITIAL;
            indexOfLetter--;
        }
    }

    private void slashState(Character character) {
        if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (character == '/') {
            addToBuffer(character, States.SINGLE_LINE_COMMENT);
        }
        else if (character == '*') {
            addToBuffer(character, States.MULTI_LINE_COMMENT);
        }
        else if (Utils.isOperator(character)) {
            addToBuffer(character, States.ERROR);
            addToken(buffer.toString(), Type.ERROR);
            state = States.INITIAL;
        }
        else {
            addToken(buffer.toString(), Type.OPERATOR);
            state = States.INITIAL;
        }
    }


    private void invalidState(Character c) {
        addToBuffer(c, States.INITIAL);
        addToken(buffer.toString(), Type.ERROR);
        state = States.INITIAL;
        initialState(c);
    }

    private void

    private void initialState(Character character) {

        if (Character.isJavaIdentifierStart(character)) {
            addToBuffer(character, States.IDENTIFIER_NOT_LITERAL);
        }
        else if (character == '`') {
            addToBuffer(character, States.IDENTIFIER_LITERAL);
        }
        else if (character == '/') {
            addToBuffer(character, States.SLASH);
        }
        else if (character == '\"') {
            addToBuffer(character, States.STRING_LITERAL);

        }
        else if (character == '\'') {
            addToken(character, Type.DELIMITER_CHARACTER);
            state = States.CHAR_LITERAL;
        }
        else if (character == '.') {
            addToBuffer(character,States.DOT);
        }
        else if (character == ':') {
            addToBuffer(character, States.COLON);
        }
        else if (character == '>') {
            addToBuffer(character, States.GREATER);
        }
        else if (character == '<') {
            addToBuffer(character, States.LESS);
        }
        else if (character == '*' || character == '%' || character == '^' || character == '!' || character == '=') {
            addToBuffer(character, States.SINGLE_OPERATOR);
        }
        else if (character == '-' ) {
            addToBuffer(character, States.MINUS);
        }
        else if (character == '+') {
            addToBuffer(character, States.PLUS);
        }
        else if (Utils.isParentheses(character)) {
            addToken(character, Type.PARENTHESES);
        }
        else if (character == '?' || character == '~') {
            addToken(character, Type.OPERATOR);
            state = States.INITIAL;
        }
        else if (character == '|') {
            addToBuffer(character, States.PIPE);
        }
        else if (Character.isDigit(character)) {
            addToBuffer(character, States.DIGIT);
        }
        else if (character == ' ') {
            addToken(character, Type.WHITESPACE);
        }
        else if (character == '#') {
            addToBuffer(character, States.POUND_SIGN);
        }
        else {
            addToBuffer(character, States.ERROR);

        }
    }


    private void addToBuffer(char c, States state) {
        System.out.println("Add buffer " + c + " " + state);
        buffer.append(c);
        this.state = state;
    }
    private void addToken(char c, Type type) {
        System.out.println("Add token " + c + " " + type);
        tokens.add(new Token(Character.toString(c), type));
        buffer = new StringBuilder();
    }

    private void addToken(String c, Type type) {
        System.out.println("Add token " + c + " " + type);
        tokens.add(new Token(c, type));
        buffer = new StringBuilder();
    }


}

