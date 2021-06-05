import java.util.Arrays;
import java.util.List;

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
            "_", ":", "=", "=>", "<-",
            "<:", "<%", ">:", "#", "@"

    );
    public static boolean isParentheses(Character c) {
        // || c == ';' || c == ',' || c=='.'
        return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }
    public static boolean isDelimiterCharacters (Character c) {
        // || c == ';' || c == ',' || c=='.'
        return c == '`' || c == ';' || c == '\"'  || c == ',' || c =='.' || c == '\'';
    }

}
