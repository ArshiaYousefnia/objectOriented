package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestaurantAdminMenu {
    private static final Pattern chargeAccount = Pattern.compile("^\\s*charge\\s+account\\s+(?<amount>-?[0-9]+)\\s*$");
    private static final Pattern addFood = Pattern.compile("^\\s*add\\s+food\\s+(?<name>\\S+)\\s+(?<category>\\S+)\\s+(?<price>-?[0-9]+)\\s+(?<cost>-?[0-9]+)\\s*$");
    private static final Pattern removeFood = Pattern.compile("^\\s*remove\\s+food\\s+(?<name>\\S+)\\s*$");

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
                System.out.println("restaurant admin menu");
                continue;
            }
            if (inputLine.matches("^\\s*show\\s+balance\\s*$")) {
                System.out.println(Controller.showBalance());
                continue;
            }
            if ((matcher = chargeAccount.matcher(inputLine)).matches()) {
                System.out.println(Controller.chargeAccount(Integer.parseInt(matcher.group("amount"))));
                continue;
            }
            if ((matcher = addFood.matcher(inputLine)).matches()) {
                System.out.println(Controller.addFood(matcher.group("name"),
                        matcher.group("category"),
                        Integer.parseInt(matcher.group("price")),
                        Integer.parseInt(matcher.group("cost"))));
                continue;
            }
            if ((matcher = removeFood.matcher(inputLine)).matches()) {
                message = Controller.removeFood(matcher.group("name"));

                if (!message.isEmpty())
                    System.out.println(message);

                continue;
            }

            System.out.println("invalid command!");
        }
    }
}
