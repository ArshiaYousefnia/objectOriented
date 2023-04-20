package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private final HashMap<Food, Integer> cart;
    private final ArrayList<Discount> discounts;
    private final ArrayList<Food> orderofFoods;

    public Customer(String username, String password) {
        super(username, password);
        cart = new HashMap<>();
        discounts = new ArrayList<>();
        orderofFoods = new ArrayList<>();
    }

    public HashMap<Food, Integer> getCart() {
        return cart;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public Discount getDiscountByCode(String code) {
        for (Discount discount : discounts) {
            if (discount.getCode().equals(code))
                return discount;
        }

        return null;
    }

    public ArrayList<Food> getOrderofFoods() {
        return orderofFoods;
    }

    public void addToCart(Food food, int number) {
        cart.put(food, number);
        orderofFoods.add(food);
    }

    public void removeFromCart(Food food) {
        cart.remove(food);
        orderofFoods.remove(food);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public void removeDiscount(Discount discount) {
        discounts.remove(discount);
    }

    public void clearCart() {
        cart.clear();
        orderofFoods.clear();
    }

    public void replaceNumber(Food food, int number) {
        cart.replace(food, cart.get(food), number);
    }
}