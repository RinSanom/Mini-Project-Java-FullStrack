package view;

import controller.UserController;

import java.util.Scanner;

public class UI {
    public static final UserController userController = new UserController();

    public static void clearConsole() {
        try {
            Thread.sleep(300); // Short delay for better terminal sync
        } catch (InterruptedException ignored) {}

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(); // Shift cursor down just in case
    }

    private static void thumbnail() {
        System.out.println("============================");
        System.out.println("      Product Inventory     ");
        System.out.println("============================");
        System.out.println("""
                1. Register
                2. Login
                """);
    }

    public static void home() {
        if (userController.isUserLoggedIn()) {
            System.out.println("✅ You are already logged in.");
            userController.showLoggedInUser();
            return;
        }

        thumbnail();
        System.out.print("Please select an option: ");
        int option = new Scanner(System.in).nextInt();

        switch (option) {
            case 1 -> {
                System.out.println("📝 Welcome to Register");
                System.out.print("Please enter your username: ");
                String username = new Scanner(System.in).next();

                System.out.print("Please enter your password: ");
                String password = new Scanner(System.in).next();

                System.out.print("Please enter your email: ");
                String email = new Scanner(System.in).next();

                userController.register(username, password, email);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
                clearConsole();

                System.out.println("🔐 Welcome to Login");
                System.out.print("Please enter your email: ");
                String emailLogin = new Scanner(System.in).next();

                System.out.print("Please enter your password: ");
                String passwordLogin = new Scanner(System.in).next();

                userController.login(emailLogin, passwordLogin);


                System.out.println("============================");
                System.out.println("""
                        1. View all products
                        2. Add product
                        3. Search products
                        """);
                System.out.println("============================");
            }
            case 2 -> {
                System.out.println("🔐 Welcome to Login");
                System.out.print("Please enter your email: ");
                String emailLogin = new Scanner(System.in).next();

                System.out.print("Please enter your password: ");
                String passwordLogin = new Scanner(System.in).next();

                userController.login(emailLogin, passwordLogin);
            }

            default -> System.out.println("❌ Invalid option. Please try again.");
        }
    }
}
