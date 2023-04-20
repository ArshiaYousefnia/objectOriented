package model;

import java.util.ArrayList;

public class Restaurant extends User {
    private final String type;
    private final ArrayList<Food> menu;

    public Restaurant(String username, String password, String type) {
        super(username, password);
        this.type = type;
        this.menu = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public Food getFoodByName(String name) {
        for (Food food : menu) {
            if (food.getName().equals(name))
                return food;
        }

        return null;
    }

    public void addFood(Food food) {
        menu.add(food);
    }

    public void removeFood(Food food) {
        menu.remove(food);
    }
}