package model;

import java.util.ArrayList;

public class Admin extends User {
    private ArrayList<Discount> discounts;

    private Admin(String username, String password) {
        super(username, password);
        discounts = new ArrayList<>();
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public void removeDiscount(Discount discount) {
        discounts.remove(discount);
    }

    public static Admin initiateAdmin(String username, String password) {
        return new Admin(username, password);
    }
}