package model;

public class User {
    private final String username;
    private String password;
    private int balance;

    protected User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String string) {
        return password.equals(string);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void increaseBalance(int amount) {
        this.balance += amount;
    }
}