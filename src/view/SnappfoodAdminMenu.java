package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnappfoodAdminMenu {
    private static final Pattern addRestaurant = Pattern.compile("^\\s*add\\s+restaurant\\s+(?<name>\\S+)\\s+(?<password>\\S+)\\s+(?<type>\\S+)\\s*$");
    private static final Pattern showRestaurant = Pattern.compile("^\\s*show\\s+restaurant(\\s+-t\\s+(?<type>\\S+))?\\s*$");
    private static final Pattern removeRestaurant = Pattern.compile("^\\s*remove\\s+restaurant\\s+(?<name>\\S+)\\s*$");
    private static final Pattern setDiscount = Pattern.compile("^\\s*set\\s+discount\\s+(?<username>\\S+)\\s+(?<amount>-?[0-9]+)\\s+(?<code>\\S+)\\s*");

    public static void run(Scanner scanner) {
        String inputLine, message;
        Matcher matcher;

        while (true) {
            inputLine = scanner.nextLine();

            if (inputLine.matches("^\\s*logout\\s*$")) {
                LoginMenu.run(scanner);
                continue;
            }
            if (inputLine.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("Snappfood admin menu");
                continue;
            }
            if (inputLine.matches("\\s*show\\s+discounts\\s*")) {
                message = Controller.showDiscountsForAdmin();

                if (!message.isEmpty())
                    System.out.println(message);

                continue;
            }
            if ((matcher = addRestaurant.matcher(inputLine)).matches()) {
                System.out.println(Controller.addRestaurant(matcher.group("name"),
                        matcher.group("password"),
                        matcher.group("type")));
                continue;
            }
            if ((matcher = showRestaurant.matcher(inputLine)).matches()) {
                message = Controller.showRestaurant(matcher.group("type"));

                if (!message.isEmpty()) System.out.println(message);

                continue;
            }
            if ((matcher = removeRestaurant.matcher(inputLine)).matches()) {
                message = Controller.removeRestaurant(matcher.group("name"));

                if (!message.isEmpty())
                    System.out.println(message);

                continue;
            }
            if ((matcher = setDiscount.matcher(inputLine)).matches()) {
                System.out.println(Controller.setDiscount(matcher.group("username"),
                        Integer.parseInt(matcher.group("amount")),
                        matcher.group("code")));
                continue;
            }

            System.out.println("invalid command!");
        }
    }
}