package model;

import java.util.ArrayList;

public class SnappFood {
    private final static ArrayList<User> users = new ArrayList<>();
    private static Admin admin;
    private static User currentUser;

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getUserByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name))
                return user;
        }

        if (admin.getUsername().equals(name))
            return admin;

        return null;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        SnappFood.admin = admin;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SnappFood.currentUser = currentUser;
    }
}