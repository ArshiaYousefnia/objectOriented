package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerMenu {
    private static final Pattern showRestaurant = Pattern.compile("^\\s*show\\s+restaurant(\\s+-t\\s+(?<type>\\S+))?\\s*$");
    private static final Pattern chargeAccount = Pattern.compile("^\\s*charge\\s+account\\s+(?<amount>-?[0-9]+)\\s*$");
    private static final Pattern showMenu = Pattern.compile("^\\s*show\\s+menu\\s+(?<name>\\S+)(\\s+-c\\s+(?<category>\\S+))?\\s*$");
    private static final Pattern addToCart = Pattern.compile("^\\s*add\\s+to\\s+cart\\s+(?<name>\\S+)\\s+(?<foodName>\\S+)(\\s+-n\\s+(?<number>-?[0-9]+))?\\s*$");
    private static final Pattern removeFromCart = Pattern.compile("^\\s*remove\\s+from\\s+cart\\s+(?<name>\\S+)\\s+(?<foodName>\\S+)(\\s+-n\\s+(?<number>-?[0-9]+))?\\s*$");
    private static final Pattern purchaseCart = Pattern.compile("^\\s*purchase\\s+cart(\\s+-d\\s+(?<code>\\S+))?\\s*$");

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
                System.out.println("customer menu");
                continue;
            }
            if (inputLine.matches("^\\s*show\\s+balance\\s*$")) {
                System.out.println(Controller.showBalance());
                continue;
            }
            if (inputLine.matches("^\\s*show\\s+cart\\s*$")) {
                message = Controller.showCart();

                if (!message.isEmpty())
                    System.out.println(message);

                continue;
            }
            if (inputLine.matches("^\\s*show\\s+discounts\\s*$")) {
                message = Controller.showDiscountsForCustomer();

                if (!message.isEmpty())
                    System.out.println(message);

                continue;
            }
            if ((matcher = chargeAccount.matcher(inputLine)).matches()) {
                System.out.println(Controller.chargeAccount(Integer.parseInt(matcher.group("amount"))));
                continue;
            }
            if ((matcher = showRestaurant.matcher(inputLine)).matches()) {
                message = Controller.showRestaurant(matcher.group("type"));

                if (!message.isEmpty()) System.out.println(message);

                continue;
            }
            if ((matcher = purchaseCart.matcher(inputLine)).matches()) {
                System.out.println(Controller.purchaseCart(matcher.group("code")));
                continue;
            }
            if ((matcher = showMenu.matcher(inputLine)).matches()) {
                message = Controller.showMenu(matcher.group("name"), matcher.group("category"));

                if (!message.isEmpty())
                    System.out.println(message);

                continue;
            }
            if ((matcher = addToCart.matcher(inputLine)).matches()) {
                if (matcher.group("number") == null || matcher.group("number").isEmpty())
                    System.out.println(Controller.addToCart(matcher.group("name"),
                            matcher.group("foodName")
                            , 1));
                else
                    System.out.println(Controller.addToCart(matcher.group("name"),
                            matcher.group("foodName"),
                            Integer.parseInt(matcher.group("number"))));

                continue;
            }
            if ((matcher = removeFromCart.matcher(inputLine)).matches()) {
                if (matcher.group("number") == null || matcher.group("number").isEmpty())
                    System.out.println(Controller.removeFromCart(matcher.group("name"),
                            matcher.group("foodName"),
                            1));
                else
                    System.out.println(Controller.removeFromCart(matcher.group("name"),
                            matcher.group("foodName"),
                            Integer.parseInt(matcher.group("number"))));

                continue;
            }

            System.out.println("invalid command!");
        }
    }
}