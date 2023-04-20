package model;

public class Food {
    private final String name;
    private final Category category;
    private final int price;
    private final int cost;
    private final Restaurant restaurant;

    public Food(String name, Category category, int price, int cost, Restaurant restaurant) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getCost() {
        return cost;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}