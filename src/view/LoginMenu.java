package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    private static final Pattern register = Pattern.compile("^\\s*register\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*$");
    private static final Pattern login = Pattern.compile("^\\s*login\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*$");
    private static final Pattern changePassword = Pattern.compile("^\\s*change\\s+password\\s+(?<username>\\S+)\\s+(?<oldPassword>\\S+)\\s+(?<newPassword>\\S+)\\s*$");
    private static final Pattern removeAccount = Pattern.compile("^\\s*remove\\s+account\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*$");

    public static void run(Scanner scanner) {
        String inputLine, message;
        Matcher matcher;

        while (true) {
            inputLine = scanner.nextLine();

            if (inputLine.matches("^\\s*exit\\s*$"))
                System.exit(0);
            if (inputLine.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("login menu");
                continue;
            }
            if ((matcher = register.matcher(inputLine)).matches()) {
                System.out.println(Controller.register(matcher.group("username"), matcher.group("password")));
                continue;
            }
            if ((matcher = login.matcher(inputLine)).matches()) {
                message = Controller.login(matcher.group("username"), matcher.group("password"));
                System.out.println(message);
                if (message.equals("login successful"))
                    MainMenu.run(scanner);

                continue;
            }
            if ((matcher = changePassword.matcher(inputLine)).matches()) {
                System.out.println(Controller.changePassword(matcher.group("username"),
                        matcher.group("oldPassword"),
                        matcher.group("newPassword")));
                continue;
            }
            if ((matcher = removeAccount.matcher(inputLine)).matches()) {
                System.out.println(Controller.removeAccount(matcher.group("username"),
                        matcher.group("password")));
                continue;
            }

            System.out.println("invalid command!");
        }
    }
}
