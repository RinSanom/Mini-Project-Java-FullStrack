package view;

import controller.UserController;

import java.util.Scanner;

public class UI {
    public static final UserController userController = new UserController();
    private static void thumbnail(){
        System.out.println("============================");
        System.out.println("      Product Inventory     ");
        System.out.println("============================");
        System.out.println("""
                1. Register
                2. Login
                """);
    }
    public static void home(){
        if (userController.isUserLoggedIn()) {
            System.out.println("✅ You are already logged in.");
            userController.showLoggedInUser();
            return;
        }

        thumbnail();
        System.out.println("Please select an option: ");
        switch (new Scanner(System.in).nextInt()) {
            case 1 -> {
                System.out.println("Welcome to Register");
                System.out.print("Please enter your username: ");
                String username = new Scanner(System.in).next();
                System.out.print("Please enter your password: ");
                String password = new Scanner(System.in).next();
                System.out.print("Please enter your email: ");
                String email = new Scanner(System.in).next();
                userController.register(username, password , email);
            }
            case 2 -> {
                System.out.println("Welcome to Login");
                System.out.print("Please enter your email: ");
                String email = new Scanner(System.in).next();
                System.out.print("Please enter your password: ");
                String password = new Scanner(System.in).next();
                userController.login(email , password);
            }
        }
    }
}
