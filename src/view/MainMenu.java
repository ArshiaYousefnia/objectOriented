package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    private static final Pattern enter = Pattern.compile("^\\s*enter\\s+(?<menuName>\\S.*?)\\s*$");

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
                System.out.println("main menu");
                continue;
            }
            if ((matcher = enter.matcher(inputLine)).matches()) {
                message = Controller.enterMenu(matcher.group("menuName"));
                System.out.println(message);

                if (message.startsWith("enter menu successful: You are in"))
                    Controller.runCurrentRolesMenu(scanner);

                continue;
            }

            System.out.println("invalid command!");
        }
    }
}