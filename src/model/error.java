package model;

public class error {
    private String error;
    private String Token;

    public error(String error, String token) {
        this.error = error;
        Token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
    public String toString() {
        return error + "  出错中断的token为 " + Token;
    }
}
