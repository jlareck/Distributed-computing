public class Token {

    public String token;
    public Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String toString() {
        return "Token{" +
                "type=" + type +
                ", tokenString='" + token + '\'' +
                '}';
    }

    public Type getType() {
        return type;
    }
}