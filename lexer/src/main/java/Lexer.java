import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class Lexer {
    String fileName;
    char[] inputText;
    int letter = 0;
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
        int indexOfLetter = 0;
        while (indexOfLetter < inputText.length) {
            char c = inputText[indexOfLetter];
            switch (state) {
                case INITIAL: {
                    initialState(c);
                    break;
                }
                case : {

                }
            }
        }
    }
    private void initialState(Character character) {

        if (Character.isJavaIdentifierStart(character) || character == '`') {
            addToBuffer(character, States.IDENTIFIER);
        }
        else if (character == '/') {
            addToBuffer(character, States.SLASH);
        }
        else if (character == '\"') {
            addToken(character, Type.DELIMITER_CHARACTER);
            state = States.STRING_LITERAL;
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

