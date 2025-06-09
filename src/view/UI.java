package view;

import controller.ProductController;
import controller.UserController;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;

import java.util.Scanner;

public class UI {
    private static final ProductController productController = new ProductController();
    public static final UserController userController = new UserController();
    public static final Scanner scanner = new Scanner(System.in);

    // ✅ Clear console for Unix-based systems (like your Fedora)
    public static void clearConsole() {
        try {
            Thread.sleep(300); // Small delay for better UX
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (InterruptedException ignored) {}
    }

    private static void thumbnailAuth() {
        System.out.println("============================");
        System.out.println("    Authentication Logic    ");
        System.out.println("============================");
        System.out.println("""
                1. Register
                2. Login
                """);
    }

    private static void thumbnailProduct() {
        System.out.println("============================");
        System.out.println("      Product Inventory     ");
        System.out.println("============================");
        System.out.println("""
                1. Get all Products
                2. Insert New Product
                3. Search Products
                4. Log out
                """);
    }

    private static void productMenu() throws InterruptedException {
        clearConsole();
        System.out.println("📦 Welcome to the Product Menu");
        thumbnailProduct();

        System.out.print("Please select an option: ");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                productController.getAllProducts()
                        .forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("[+] Insert Product Name : ");
                String productName = scanner.next();
                System.out.print("[+] Insert Product Price : ");
                float productPrice = scanner.nextFloat();
                System.out.println("[+] Insert Product Quantity : ");
                int productQuantity = scanner.nextInt();
                ProductCreateDto productCreateDto
                        = new ProductCreateDto(productName, productPrice, productQuantity);
                ProductResponDto product = productController.insertNewProduct(productCreateDto);
                System.out.println(product);
            }
            default -> System.out.println("❌ Invalid option.");
        }
    }

    public static void home() throws InterruptedException {
        if (userController.isUserLoggedIn()) {
//            System.out.println("✅ You are already logged in.");
            userController.showLoggedInUser();
            productMenu();
            return;
        }

        thumbnailAuth();
        System.out.print("Please select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        switch (option) {
            case 1 -> {
                clearConsole();
                System.out.println("📝 Register");
                System.out.print("Username: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                System.out.print("Email: ");
                String email = scanner.nextLine();

                userController.register(username, password, email);

                // Try logging in after registration
                Thread.sleep(1000);
                clearConsole();
                System.out.println("🔐 Login after registration");

                System.out.print("Email: ");
                String loginEmail = scanner.nextLine();

                System.out.print("Password: ");
                String loginPassword = scanner.nextLine();

                if (userController.login(loginEmail, loginPassword)) {
                    productMenu();
                }
            }

            case 2 -> {
                clearConsole();
                System.out.println("🔐 Login");

                System.out.print("Email: ");
                String loginEmail = scanner.nextLine();

                System.out.print("Password: ");
                String loginPassword = scanner.nextLine();

                if (userController.login(loginEmail, loginPassword)) {
                    productMenu();
                }
            }

            default -> System.out.println("❌ Invalid option. Please try again.");
        }
    }
}
