public enum States {
    INITIAL,
    ERROR,
    SLASH,
    POUND_SIGN,
    ANNOTATION_SIGN,
    IDENTIFIER_NOT_LITERAL,
    IDENTIFIER_LITERAL,
    IDENTIFIER_OPERATOR,
    ZERO_FIRST,
    DIGIT,
    CHAR_LITERAL,
    STRING_LITERAL,
    DOT,
    GREATER,
    LESS,
    AT_SIGN,
    DOUBLE_PIPE,
    PIPE_AND_EQ,
    AMPERSAND,
    SINGLE_OPERATOR,
    COLON,
    PLUS,
    MINUS,
    PIPE,
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT,
    BINARY_DIGITS,
    HEX_DIGITS,
    INTEGER_SUFFIX,
    POINT_IN_DIGIT,
    POSSIBLE_ESCAPE_SEQUENCE_CHAR,
    EXPECT_END_OF_CHAR,
    POSSIBLE_ESCAPE_SEQUENCE,
    DOUBLE_GREATER,
    OPERATOR_AND_EQUAL,
    STAR_IN_MULTI_LINE_COMMENT,
    FLOAT_SUFFIX,
    DOUBLE_DOT,
}
