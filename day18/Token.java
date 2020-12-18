public class Token {
    private String token;
    
    public Token(char token) {
        this.token = Character.toString(token);
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return getToken();
    }
}
