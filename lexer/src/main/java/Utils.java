import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
public class Utils {
    private static final List<String> specialReservedWords = Arrays.asList(
            "abstract", "case", "catch", "class", "def",
            "do", "else", "extends", "final",
            "finally", "for", "forSome", "if", "implicit",
            "import", "lazy", "macro", "match", "new",
            "null", "object", "override", "package", "private",
            "protected", "return", "sealed", "super", "this",
            "throw", "trait", "try", "type",
            "val", "var", "while", "with", "yield",
            "_", ":", "=>", "<-",
            "<:", "<%", ">:", "#", "@"
    );
    public static boolean isParentheses(Character c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }
    public static boolean isDelimiterCharacters (Character c) {
        return c == '`' || c == ';' || c == '\"'  || c == ',' || c =='.' || c == '\'';
    }
    public static boolean isBinary(Character c) {
        return c == '0' || c == '1';
    }
    public static boolean isOperator(Character c) {
        return c == '=' || c == '>' || c == '<' || c == '!' || c == '~' || c == ':' || c == '?' || c == '&' || c == '|' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
    }
    public static boolean isSpecialCharacter(Character c) {
        return c == '#' || c == '@';
    }
    public static boolean isHex(Character c) {
        return Pattern.matches("\\d|[a-fA-F]", c.toString());
    }

    public static boolean isDoubleOrFloat(Character c) {
        return c == 'f' || c == 'F' || c == 'd' || c == 'D';
    }

    public static boolean isBooleanLiteral(String str) {
        return "true".equals(str) || "false".equals(str);
    }

    public static boolean specialReservedWords(String str) {
        return specialReservedWords.contains(str);
    }
}
