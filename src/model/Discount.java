package model;

public class Discount {
    private final String code;
    private final int amount;
    private final Customer owner;
    public Discount(String code, int amount ,Customer customer) {
        this.code = code;
        this.amount = amount;
        this.owner = customer;
    }

    public int getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }

    public Customer getOwner() {
        return owner;
    }
}