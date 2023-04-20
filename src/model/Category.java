package model;

public enum Category {
    STARTER("starter"),
    ENTREE("entree"),
    DESSERT("dessert");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}