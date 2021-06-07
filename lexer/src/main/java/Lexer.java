import jdk.jshell.execution.Util;

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
                    identifierNotLiteralState(c);
                    break;
                }
                case SLASH: {
                    slashState(c);
                    break;
                }
                case POUND_SIGN: {
                    poundSignState(c);
                    break;
                }
                case IDENTIFIER_LITERAL: {
                    identifierLiteralState(c);
                    break;
                }
                case ZERO_FIRST: {
                    zeroFirstState(c);
                    break;
                }
                case CHAR_LITERAL: {
                    characterLiteralState(c);
                    break;
                }
                case STRING_LITERAL: {
                    stringLiteralState(c);
                    break;
                }
                case DOT: {
                    dotState(c);
                    break;
                }
                case GREATER: {
                    greaterState(c);
                    break;
                }
                case LESS: {
                    lessState(c);
                    break;
                }
                case AT_SIGN: {
                    atSignState(c);
                    break;
                }
                case  END_OF_TRIPLE_QUOTES_1:{
                    tripleQuotesEnd1State(c);
                    break;
                }
                case END_OF_TRIPLE_QUOTES_2: {
                    tripleQuotesEnd2State(c);
                    break;
                }
                case DOUBLE_QUOTES: {
                    doubleQuotesState(c);
                    break;
                }
                case TRIPLE_QUOTES: {
                    tripleQuotesState(c);
                    break;
                }
                case SINGLE_OPERATOR: {
                    singleOperatorState(c);
                    break;
                }
                case PLUS: {
                    plusState(c);
                    break;
                }
                case MINUS: {
                    minusState(c);
                    break;
                }
                case PIPE: {
                    pipeState(c);
                    break;
                }
                case SINGLE_LINE_COMMENT: {
                    singleLineCommentState(c);
                    break;
                }
                case MULTI_LINE_COMMENT: {
                    multilineCommentState(c);
                    break;
                }
                case HEX_DIGITS: {
                    hexDigitState(c);
                    break;
                }
                case INTEGER_SUFFIX: {
                    integerSuffixState(c);
                    break;
                }
                case NON_ZERO_DIGIT: {
                    nonZeroDigitState(c);
                    break;
                }
                case DEFINE_METHOD: {
                    defineMethodState(c);
                    break;
                }
                case POINT_IN_DIGIT: {
                    pointInDigitState(c);
                    break;
                }
                case POSSIBLE_ESCAPE_SEQUENCE: {
                    possibleEscapeSequenceState(c);
                    break;
                }
                case POSSIBLE_ESCAPE_SEQUENCE_CHAR: {
                    possibleEscapeSequenceCharState(c);
                    break;
                }
                case EXPECT_END_OF_CHAR: {
                    expectEndOfCharState(c);
                    break;
                }
                case DOUBLE_GREATER: {
                    doubleGreaterState(c);
                    break;
                }
                case OPERATOR_AND_EQUAL: {
                    operatorAndEqualState(c);
                    break;
                }
                case STAR_IN_MULTI_LINE_COMMENT: {
                    starInMultilineCommentState(c);
                    break;
                }
                case FLOAT_SUFFIX: {
                    floatSuffixState(c);
                    break;
                }
                case DOUBLE_LESS: {
                    doubleLessState(c);
                    break;
                }
                case TRIPLE_GREATER: {
                    tripleGreaterState(c);
                    break;
                }
                case TRIPLE_LESS: {
                    tripleLessState(c);
                    break;
                }

            }
            indexOfLetter++;
        }
        return tokens;
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
                addToBuffer(character, States.AT_SIGN);
                return;
            }
            else {
                addToken(buffer.toString(), Type.IDENTIFIER);
            }
            state = States.INITIAL;
            indexOfLetter--;
        }
    }
    // #
    private void poundSignState(Character character) {
        if (Character.isJavaIdentifierStart(character)) {
            addToken(buffer.toString(), Type.RESERVED_WORDS);
            addToBuffer(character, States.IDENTIFIER_LITERAL);
        }
        else if (character == '`') {
            addToken(buffer.toString(), Type.RESERVED_WORDS);
            addToBuffer(character, States.IDENTIFIER_LITERAL);
        }
        else if (Character.isWhitespace(character)) {
            addToken(buffer.toString(), Type.RESERVED_WORDS);
            initialState(character);
        }
        else if(Utils.isSpecialCharacter(character) || Utils.isOperator(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
    }
    // in Scala we can define methods like def ++@++=-=():Unit = {}
    // and we need also to handle them
    private void defineMethodState(Character character) {
        if (Utils.isSpecialCharacter(character) || Utils.isOperator(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else if (Character.isLetter(character) || Character.isDigit(character)) {
            // def +-=+letters  is not valid
            addToBuffer(character, States.INITIAL);
            addToken(buffer.toString(), Type.ERROR);
        }
        else {
            addToken(buffer.toString(), Type.DEFINED_METHOD);
            initialState(character);
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

    private void stringLiteralState(Character character) {
        if(character=='\\') {
            state = States.POSSIBLE_ESCAPE_SEQUENCE;
        }
        else if (character=='\"') {
            addToBuffer(character, States.DOUBLE_QUOTES);
        }
        else if (Character.isWhitespace(character)&&
                character!='\t' && character != ' ') {
            addToken(buffer.toString(), Type.ERROR);
            state = States.INITIAL;
            initialState(character);
        }
        else {
            addToBuffer(character, States.STRING_LITERAL);
        }
    }
    // case: ""some info
    private void doubleQuotesState(Character character) {
        if(character == '"') {
            addToBuffer(character, States.TRIPLE_QUOTES);
        }
        else {
            addToken(buffer.toString(), Type.STRING_LITERAL);
            initialState(character);
        }
    }
    // case: """
    private void tripleQuotesState(Character character) {
        if (character == '"') {
            addToBuffer(character, States.END_OF_TRIPLE_QUOTES_1);
        }
        else {
            addToBuffer(character, States.TRIPLE_QUOTES);
        }
    }
    // case: """"someInfo
    private void tripleQuotesEnd1State(Character character) {
        if (character == '"') {
            addToBuffer(character, States.END_OF_TRIPLE_QUOTES_2);
        }
        else {
            addToBuffer(character, States.INITIAL);
            addToken(buffer.toString(), Type.ERROR);
        }
    }
    private void tripleQuotesEnd2State(Character character) {
        if (character == '"') {
            addToBuffer(character, States.INITIAL);
            addToken(character,Type.STRING_LITERAL);
        }
        else {
            addToBuffer(character, States.INITIAL);
            addToken(buffer.toString(), Type.ERROR);
        }
    }

    private void hexDigitState(Character character) {
        if(Utils.isHex(character)) {
            addToBuffer(character, States.HEX_DIGITS);
        }
        else if(character=='l' || character=='L') {
            addToBuffer(character, States.INTEGER_SUFFIX);
        }
        else if (character=='f' || character=='F') {
            addToBuffer(character, States.FLOAT_SUFFIX);
        }
        else if (Character.isJavaIdentifierPart(character)) {
            invalidState(character);
        }
        else {
            addToken(buffer.toString(), Type.INT_LITERAL);
            state = States.INITIAL;
            initialState(character);
        }
    }

    private void possibleEscapeSequenceState(Character character) {
        if(Utils.isEscapeCharacter("\\" + character)) {
            addToBuffer(character, States.STRING_LITERAL);
            state = States.STRING_LITERAL;
        } else {
            addToBuffer(character, States.INITIAL);
            addToken(buffer.toString(), Type.ERROR);
        }
    }
    private void characterLiteralState(Character character) {
        if (character == '\\') {
            addToBuffer(character, States.POSSIBLE_ESCAPE_SEQUENCE_CHAR);
        }
        else {
            addToBuffer(character, States.EXPECT_END_OF_CHAR);
        }
    }
    private void possibleEscapeSequenceCharState(Character character) {
        if (Utils.isEscapeCharacter("\\" + character)) {
            addToBuffer(character, States.EXPECT_END_OF_CHAR);
        } else {
            addToBuffer(character, States.INITIAL);
            addToken(character, Type.ERROR);
        }
    }
    private void expectEndOfCharState(Character character) {
        if(character=='\'') {
            addToBuffer(character, States.INITIAL);
            addToken(character, Type.CHAR_LITERAL);
            state = States.INITIAL;
        } else {
            addToken(character, Type.ERROR);
            state = States.INITIAL;
        }
    }

    private void dotState(Character character) {
        if (Character.isDigit(character)) {
            addToBuffer(character, States.POINT_IN_DIGIT);
        }
        else {
            addToken(character, Type.DELIMITER_CHARACTER);
            indexOfLetter--;
            state = States.INITIAL;
        }
    }

    private void greaterState(Character character) {
        if (character == ':') {
            addToBuffer(character, States.INITIAL);
            addToken(buffer.toString(), Type.RESERVED_WORDS);
            state = States.INITIAL;
        }
        else if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (character == '>') {
            addToBuffer(character, States.DOUBLE_GREATER);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else  {
            addToken(buffer.toString(), Type.OPERATOR);
            state = States.INITIAL;
            indexOfLetter--;
        }
    }

    private void doubleGreaterState(Character character) {
        if(character=='>') {
            addToBuffer(character, States.TRIPLE_GREATER);
        }
        else if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else {
            addToken(buffer.toString(), Type.OPERATOR);
            state = States.INITIAL;
            initialState(character);
        }
    }
    private void tripleGreaterState(Character character) {
        if (Character.isDigit(character) || Character.isWhitespace(character)
                || Character.isJavaIdentifierStart(character) || Utils.isDelimiterCharacters(character)) {
            addToken(buffer.toString(), Type.OPERATOR);
            initialState(character);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
    }


    private void lessState(Character character) {
        if (character == ':' || character == '-') {
            addToBuffer(character, States.INITIAL);
            addToken(buffer.toString(), Type.RESERVED_WORDS);
            state = States.INITIAL;
        }
        else if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (character == '<') {
            addToBuffer(character, States.DOUBLE_LESS);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else  {
            addToken(buffer.toString(), Type.OPERATOR);
            state = States.INITIAL;
            indexOfLetter--;
        }
    }
    private void doubleLessState(Character character) {
        if(character=='<') {
            addToBuffer(character, States.TRIPLE_LESS);
        }
        else if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else {
            addToken(buffer.toString(), Type.OPERATOR);
            state = States.INITIAL;
            initialState(character);
        }
    }
    private void tripleLessState(Character character) {
        if (Character.isDigit(character) || Character.isWhitespace(character)
                || Character.isJavaIdentifierStart(character) || Utils.isDelimiterCharacters(character)) {
            addToken(buffer.toString(), Type.OPERATOR);
            initialState(character);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
    }

    private void singleOperatorState(Character character) {
        if(character=='=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        } else if (Utils.isOperator(character)) {
            addToBuffer(character, States.ERROR);
            addToken(buffer.toString(), Type.ERROR);
            state = States.INITIAL;
        } else {
            addToken(buffer.toString(), Type.OPERATOR);
            state = States.INITIAL;
            indexOfLetter--;
        }
    }

    private void plusState(Character character) {
        if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else {
            addToken(buffer.toString(), Type.OPERATOR);
            indexOfLetter--;
            state = States.INITIAL;
        }
    }
    private void minusState(Character character) {
        if (character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else if (Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else {
            addToken(buffer.toString(), Type.OPERATOR);
            indexOfLetter--;
            state = States.INITIAL;
        }
    }

    private void pipeState(Character character) {
        if (character == '|' || character == '=') {
            addToBuffer(character, States.OPERATOR_AND_EQUAL);
        }
        else {
            addToken(character, Type.OPERATOR);
            indexOfLetter--;
            state = States.INITIAL;
        }
    }

    private void operatorAndEqualState(Character character) {
        if(Utils.isOperator(character) || Utils.isSpecialCharacter(character)) {
            addToBuffer(character,States.DEFINE_METHOD);
        }
        else {
            addToken(buffer.toString(), Type.OPERATOR);
            addToBuffer(character, States.INITIAL);
        }
    }

    private void pointInDigitState(Character character) {
        if (Character.isDigit(character)) {
            addToBuffer(character, States.POINT_IN_DIGIT);
        }
        else if(Utils.isDoubleOrFloat(character)) {
            addToBuffer(character, States.FLOAT_SUFFIX);
        }
        else if (Character.isJavaIdentifierPart(character) || character=='.') {
            addToBuffer(character, States.INITIAL);
            addToken(character, Type.ERROR);
        }
        else {
            addToken(character, Type.FLOAT_LITERAL);
            state = States.INITIAL;
            initialState(character);
        }
    }

    private void zeroFirstState(Character character) {
        if (character == '.') {
            addToBuffer(character, States.POINT_IN_DIGIT);
        }
        else if (Utils.isDoubleOrFloat(character)) {
            addToBuffer(character, States.FLOAT_SUFFIX);
        }
        else if (character == 'x' || character == 'X') {
            addToBuffer(character, States.HEX_DIGITS);
        }
        else if (character == 'l' || character == 'L') {
            addToBuffer(character, States.INTEGER_SUFFIX);
        }
        else if (Character.isJavaIdentifierPart(character) || Character.isDigit(character)) {
            invalidState(character);
        }
        else {
            addToken(buffer.toString(), Type.INT_LITERAL);
        }
    }
    private void integerSuffixState(Character character) {
        if (Character.isJavaIdentifierPart(character)) {
            addToBuffer(character, States.ERROR);

        } else {
            addToken(buffer.toString(), Type.INT_LITERAL);
            state = States.INITIAL;
            initialState(character);
        }
    }

    private void floatSuffixState(Character character) {
        if (Character.isJavaIdentifierPart(character)) {
            addToBuffer(character, States.ERROR);
        } else {
            addToken(character, Type.FLOAT_LITERAL);
            state = States.INITIAL;
            initialState(character);
        }
    }
    private void nonZeroDigitState(Character character) {
        if (Character.isDigit(character)) {
            addToBuffer(character, States.NON_ZERO_DIGIT);
        }
        else if (character == '.') {
            addToBuffer(character, States.POINT_IN_DIGIT);
        }
        else if (character == 'l' || character == 'L') {
            addToBuffer(character, States.INTEGER_SUFFIX);
        }
        else if (character == 'f' || character == 'F') {
            addToBuffer(character, States.FLOAT_SUFFIX);
        }
        else if (Character.isJavaIdentifierPart(character)) {
            invalidState(character);
        }
        else {
            addToken(buffer.toString(), Type.INT_LITERAL);
        }
    }

    private void atSignState(Character character) {
        if (character == '@') {
            addToBuffer(character, States.DEFINE_METHOD);
        }
        else {
            if (!Character.isDigit(character) && !Character.isLetter(character)
            && !Character.isWhitespace(character)) {
                addToBuffer(character, States.DEFINE_METHOD);
            }
            else {
                addToken(character, Type.RESERVED_WORDS);
                initialState(character);
            }
        }
    }

    private void singleLineCommentState(Character character) {
        if(Character.isWhitespace(character) && character!= '\t' && character!=' ') {
            addToken(buffer.toString(), Type.COMMENT);
            state = States.INITIAL;
            initialState(character);
        } else {
            addToBuffer(character, States.SINGLE_LINE_COMMENT);
        }
    }

    private void multilineCommentState(Character character) {
        if(character=='*') {
            addToBuffer(character, States.STAR_IN_MULTI_LINE_COMMENT);
        } else {
            addToBuffer(character, States.MULTI_LINE_COMMENT);
        }
    }

    private void starInMultilineCommentState(Character character) {
        if(character=='/') {
            addToBuffer(character, States.INITIAL);
            addToken(character, Type.COMMENT);
        } else {
            addToBuffer(character, States.MULTI_LINE_COMMENT);
        }
    }

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
        else if (character == '"') {
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
            addToken(character, Type.RESERVED_WORDS);
            state = States.INITIAL;
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
        else if (character == '0') {
            addToBuffer(character, States.ZERO_FIRST);
        }
        else if (Character.isDigit(character)) {
            addToBuffer(character, States.NON_ZERO_DIGIT);
        }
        else if (Character.isWhitespace(character)) {
            addToken(character, Type.WHITESPACE);
        }
        else if (character == '#') {
            invalidState(character);
        }
        else if (character == '@') {
            addToBuffer(character, States.AT_SIGN);
        }
        else if (character == '_') {
            addToken(character, Type.RESERVED_WORDS);
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

