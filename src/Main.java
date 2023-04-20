import controller.Controller;
import view.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Controller.setAdmin(scanner.nextLine(), scanner.nextLine());
        LoginMenu.run(scanner);
    }
}