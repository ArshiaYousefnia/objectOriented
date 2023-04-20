package controller;

import model.*;
import view.CustomerMenu;
import view.RestaurantAdminMenu;
import view.SnappfoodAdminMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    public static String register(String username, String password) {
        if (username.matches(".*\\W.*") || !username.matches(".*[a-zA-Z].*"))
            return "register failed: invalid username format";
        if (SnappFood.getUserByName(username) != null)
            return "register failed: username already exists";
        if (password.matches(".*\\W.*"))
            return "register failed: invalid password format";
        if (password.length() < 5 ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*[A-Z].*"))
            return "register failed: weak password";

        SnappFood.addUser(new Customer(username, password));
        return "register successful";
    }

    public static String login(String username, String password) {
        User user;

        if ((user = SnappFood.getUserByName(username)) == null)
            return "login failed: username not found";
        if (!user.checkPassword(password))
            return "login failed: incorrect password";

        SnappFood.setCurrentUser(user);
        return "login successful";
    }

    public static String changePassword(String username, String password, String newPassword) {
        User user;

        if ((user = SnappFood.getUserByName(username)) == null)
            return "password change failed: username not found";
        if (!user.checkPassword(password))
            return "password change failed: incorrect password";
        if (newPassword.matches(".*\\W.*"))
            return "password change failed: invalid new password";
        if (newPassword.length() < 5 ||
                !newPassword.matches(".*[0-9].*") ||
                !newPassword.matches(".*[a-z].*") ||
                !newPassword.matches(".*[A-Z].*"))
            return "password change failed: weak new password";

        user.setPassword(newPassword);
        return "password change successful";
    }

    public static String removeAccount(String username, String password) {
        User user;

        if ((user = SnappFood.getUserByName(username)) == null)
            return "remove account failed: username not found";
        if (!user.checkPassword(password))
            return "remove account failed: incorrect password";

        SnappFood.removeUser(user);

        if (user instanceof Customer) {
            for (Discount discount : SnappFood.getAdmin().getDiscounts()) {
                if (discount.getOwner() == user)
                    SnappFood.getAdmin().removeDiscount(discount);
            }
        } else {
            for (User buyer : SnappFood.getUsers()) {
                if (buyer instanceof Customer) {
                    for (Food food : ((Customer) buyer).getCart().keySet()) {
                        if (food.getRestaurant() == user)
                            ((Customer) buyer).removeFromCart(food);
                    }
                }
            }
        }

        return "remove account successful";
    }

    public static String enterMenu(String menuName) {
        if (menuName.matches("customer\\s+menu")) {
            if (SnappFood.getCurrentUser() instanceof Customer)
                return "enter menu successful: You are in the customer menu!";

            return "enter menu failed: access denied";
        }
        if (menuName.matches("restaurant\\s+admin\\s+menu")) {
            if (SnappFood.getCurrentUser() instanceof Restaurant)
                return "enter menu successful: You are in the restaurant admin menu!";

            return "enter menu failed: access denied";
        }
        if (menuName.matches("Snappfood\\s+admin\\s+menu")) {
            if (SnappFood.getCurrentUser() instanceof Admin)
                return "enter menu successful: You are in the Snappfood admin menu!";

            return "enter menu failed: access denied";
        }

        return "enter menu failed: invalid menu name";
    }

    public static String addRestaurant(String username, String password, String type) {
        if (username.matches(".*\\W.*") || !username.matches(".*[a-zA-Z].*"))
            return "add restaurant failed: invalid username format";
        if (SnappFood.getUserByName(username) != null)
            return "add restaurant failed: username already exists";
        if (password.matches(".*\\W.*"))
            return "add restaurant failed: invalid password format";
        if (password.length() < 5 ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*[A-Z].*"))
            return "add restaurant failed: weak password";
        if (type.matches(".*[^\\-a-z].*"))
            return "add restaurant failed: invalid type format";

        SnappFood.addUser(new Restaurant(username, password, type));
        return "add restaurant successful";
    }

    public static String showRestaurant(String type) {
        String output = "";
        int index = 0;

        if (SnappFood.getCurrentUser() instanceof Admin) {
            for (User user : SnappFood.getUsers()) {
                if (user instanceof Restaurant && (type == null || type.isEmpty()
                        || ((Restaurant) user).getType().equals(type))) {
                    index++;
                    if (!output.isEmpty()) output += "\n";

                    output += index + ") " + user.getUsername() + ": type="
                            + ((Restaurant) user).getType()
                            + " balance=" + user.getBalance();
                }
            }
        } else {
            for (User user : SnappFood.getUsers()) {
                if (user instanceof Restaurant && (type == null || type.isEmpty()
                        || ((Restaurant) user).getType().equals(type))) {
                    index++;
                    if (!output.isEmpty()) output += "\n";

                    output += index + ") " + user.getUsername()
                            + ": type=" + ((Restaurant) user).getType();
                }
            }
        }

        return output;
    }

    public static String removeRestaurant(String username) {
        User user;

        if ((user = SnappFood.getUserByName(username)) == null || !(user instanceof Restaurant))
            return "remove restaurant failed: restaurant not found";

        SnappFood.removeUser(user);

        for (User buyer : SnappFood.getUsers()) {
            if (buyer instanceof Customer) {
                for (Food food : ((Customer) buyer).getCart().keySet()) {
                    if (food.getRestaurant() == user)
                        ((Customer) buyer).removeFromCart(food);
                }
            }
        }

        return "";
    }

    public static String setDiscount(String username, int amount, String code) {
        User user;
        Discount discount;

        if ((user = SnappFood.getUserByName(username)) == null || !(user instanceof Customer))
            return "set discount failed: username not found";
        if (amount <= 0)
            return "set discount failed: invalid amount";
        if (code.matches(".*[^a-zA-Z0-9].*"))
            return "set discount failed: invalid code format";

        discount = new Discount(code, amount, (Customer) user);
        ((Customer) user).addDiscount(discount);
        ((Admin) SnappFood.getCurrentUser()).addDiscount(discount);
        return "set discount successful";
    }

    public static String showDiscountsForAdmin() {
        String output = "";
        int index = 0;

        for (Discount discount : ((Admin) SnappFood.getCurrentUser()).getDiscounts()) {
            index++;
            if (!output.isEmpty()) output += "\n";

            output += index + ") " + discount.getCode() + " | amount=" + discount.getAmount()
                    + " --> user=" + discount.getOwner().getUsername();
        }

        return output;
    }

    public static String chargeAccount(int amount) {
        if (amount <= 0)
            return "charge account failed: invalid cost or price";

        SnappFood.getCurrentUser().increaseBalance(amount);
        return "charge account successful";
    }

    public static String showBalance() {
        return Integer.toString(SnappFood.getCurrentUser().getBalance());
    }

    public static String addFood(String name, String category, int price, int cost) {
        Category foodCategory;

        if (Category.DESSERT.getCategory().equals(category))
            foodCategory = Category.DESSERT;
        else if (Category.ENTREE.getCategory().equals(category))
            foodCategory = Category.ENTREE;
        else if (Category.STARTER.getCategory().equals(category))
            foodCategory = Category.STARTER;
        else return "add food failed: invalid category";

        if (name.matches(".*[^-a-z].*"))
            return "add food failed: invalid food name";
        if (((Restaurant) SnappFood.getCurrentUser()).getFoodByName(name) != null)
            return "add food failed: food already exists";
        if (price <= 0 || cost <= 0)
            return "add food failed: invalid cost or price";

        ((Restaurant) SnappFood.getCurrentUser()).addFood(new Food(name, foodCategory,
                price,
                cost,
                (Restaurant) SnappFood.getCurrentUser()));
        return "add food successful";
    }

    public static String removeFood(String name) {
        Food food;

        if ((food = ((Restaurant) SnappFood.getCurrentUser()).getFoodByName(name)) == null)
            return "remove food failed: food not found";

        ((Restaurant) SnappFood.getCurrentUser()).removeFood(food);
        for (User user : SnappFood.getUsers()) {
            if (user instanceof Customer && ((Customer) user).getCart().containsKey(food))
                ((Customer) user).removeFromCart(food);
        }

        return "";
    }

    public static String showMenu(String restaurantName, String category) {
        Restaurant restaurant;
        Category foodCategory = null;
        ArrayList<Food> starter = new ArrayList<>(),
                entree = new ArrayList<>(),
                dessert = new ArrayList<>();
        String output = "";

        if ((restaurant = (Restaurant) SnappFood.getUserByName(restaurantName)) == null)
            return "show menu failed: restaurant not found";
        if (Category.DESSERT.getCategory().equals(category))
            foodCategory = Category.DESSERT;
        else if (Category.ENTREE.getCategory().equals(category))
            foodCategory = Category.ENTREE;
        else if (Category.STARTER.getCategory().equals(category))
            foodCategory = Category.STARTER;
        else if (category != null)
            return "show menu failed: invalid category";

        if (category == null) {
            for (Food food : restaurant.getMenu()) {
                if (food.getCategory().equals(Category.DESSERT))
                    dessert.add(food);
                else if (food.getCategory().equals(Category.ENTREE)) {
                    entree.add(food);
                } else starter.add(food);
            }

            output += "<< STARTER >>";

            for (Food food : starter)
                output += "\n" + food.getName() + " | price=" + food.getPrice();

            output += "\n<< ENTREE >>";

            for (Food food : entree)
                output += "\n" + food.getName() + " | price=" + food.getPrice();

            output += "\n<< DESSERT >>";

            for (Food food : dessert)
                output += "\n" + food.getName() + " | price=" + food.getPrice();
        } else {
            for (Food food : restaurant.getMenu()) {
                if (food.getCategory().equals(foodCategory)) {
                    if (!output.isEmpty()) output += "\n";

                    output += food.getName() + " | price=" + food.getPrice();
                }
            }
        }

        return output;
    }

    public static String addToCart(String restaurantName, String foodName, int number) {
        Restaurant restaurant;
        Food food;
        Customer currentCustomer = (Customer) SnappFood.getCurrentUser();
        int newAmount;

        if ((restaurant = (Restaurant) SnappFood.getUserByName(restaurantName)) == null)
            return "add to cart failed: restaurant not found";
        if ((food = restaurant.getFoodByName(foodName)) == null)
            return "add to cart failed: food not found";
        if (number <= 0)
            return "add to cart failed: invalid number";

        if (currentCustomer.getCart().containsKey(food)) {
            newAmount = currentCustomer.getCart().get(food) + number;

            currentCustomer.replaceNumber(food, newAmount);
        } else
            currentCustomer.addToCart(food, number);

        return "add to cart successful";
    }

    public static String removeFromCart(String restaurantName, String foodName, int number) {
        Food currentFood = null;
        int newAmount;
        Customer currentCustomer = (Customer) SnappFood.getCurrentUser();

        for (Food food : currentCustomer.getCart().keySet()) {
            if (food.getRestaurant().getUsername().equals(restaurantName)
                    && food.getName().equals(foodName))
                currentFood = food;
        }

        if (currentFood == null)
            return "remove from cart failed: not in cart";
        if (number <= 0) return "remove from cart failed: invalid number";
        if (number > currentCustomer.getCart().get(currentFood))
            return "remove from cart failed: not enough food in cart";

        newAmount = currentCustomer.getCart().get(currentFood) - number;

        if (newAmount == 0)
            currentCustomer.removeFromCart(currentFood);
        else
            currentCustomer.replaceNumber(currentFood, newAmount);

        return "remove from cart successful";
    }

    public static String showCart() {
        String output = "";
        Customer currentCustomer = (Customer) SnappFood.getCurrentUser();
        int index = 0, totalPrice = 0, currentPrice;

        for (Food food : currentCustomer.getOrderofFoods()) {
            index++;
            currentPrice = food.getPrice() * currentCustomer.getCart().get(food);
            totalPrice += currentPrice;

            output += index + ") " + food.getName() + " | restaurant=" + food.getRestaurant().getUsername()
                    + " price=" + currentPrice + "\n";
        }

        output += "Total: " + totalPrice;

        return output;
    }

    public static String showDiscountsForCustomer() {
        String output = "";
        Customer currentCustomer = (Customer) SnappFood.getCurrentUser();
        int index = 0;

        for (Discount discount : currentCustomer.getDiscounts()) {
            index++;
            if (!output.isEmpty()) output += "\n";

            output += index + ") " + discount.getCode() + " | amount=" + discount.getAmount();
        }

        return output;
    }

    public static String purchaseCart(String code) {
        Customer currentCustomer = (Customer) SnappFood.getCurrentUser();
        Discount discount = currentCustomer.getDiscountByCode(code);
        boolean discountStatus;
        int totalPrice = 0, discountAmount = 0;
        discountStatus = (code == null) || (discount != null);

        if (!discountStatus)
            return "purchase failed: invalid discount code";
        if (discount != null)
            discountAmount = discount.getAmount();

        for (Food food : currentCustomer.getCart().keySet())
            totalPrice += food.getPrice() * currentCustomer.getCart().get(food);

        if (currentCustomer.getBalance() + discountAmount < totalPrice)
            return "purchase failed: inadequate money";

        currentCustomer.increaseBalance(discountAmount - totalPrice);

        for (Food food : currentCustomer.getCart().keySet())
            food.getRestaurant().increaseBalance((food.getPrice() - food.getCost()) * currentCustomer.getCart().get(food));

        currentCustomer.clearCart();

        if (discount != null) {
            currentCustomer.removeDiscount(discount);
            SnappFood.getAdmin().removeDiscount(discount);
        }

        return "purchase successful";
    }

    public static void setAdmin(String username, String password) {
        SnappFood.setAdmin(Admin.initiateAdmin(username, password));
    }

    public static void runCurrentRolesMenu(Scanner scanner) {
        if (SnappFood.getCurrentUser() instanceof Admin)
            SnappfoodAdminMenu.run(scanner);
        else if (SnappFood.getCurrentUser() instanceof Restaurant)
            RestaurantAdminMenu.run(scanner);
        else CustomerMenu.run(scanner);
    }
}