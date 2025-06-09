package view;

import controller.ProductController;
import controller.UserController;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;

import java.util.List;
import java.util.Scanner;

public class UI {
    private static final ProductController productController = new ProductController();
    public static final UserController userController = new UserController();
    public static final Scanner scanner = new Scanner(System.in);

    // ANSI Color codes for beautiful UI
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String CYAN = "\033[96m";
    private static final String GREEN = "\033[92m";
    private static final String RED = "\033[91m";
    private static final String YELLOW = "\033[93m";
    private static final String BLUE = "\033[94m";
    private static final String PURPLE = "\033[95m";

    // ✅ Clear console for Unix-based systems
    public static void clearConsole() {
        try {
            Thread.sleep(200);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (InterruptedException ignored) {}
    }

    // 🎨 Beautiful banner
    private static void printWelcomeBanner() {
        System.out.println(CYAN + BOLD +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║                    🚀 INVENTORY SYSTEM 🚀                   ║\n" +
                "║                     Welcome to Management                    ║\n" +
                "╚══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    // 🎨 Section headers
    private static void printSectionHeader(String title, String icon) {
        String border = "═".repeat(50);
        System.out.println(BLUE + BOLD + "╔" + border + "╗" + RESET);
        System.out.println(BLUE + BOLD + "║" + centerText(icon + " " + title + " " + icon, 50) + "║" + RESET);
        System.out.println(BLUE + BOLD + "╚" + border + "╝" + RESET);
        System.out.println();
    }

    // 🎨 Menu options
    private static void printMenuOption(int number, String option, String description) {
        System.out.printf(YELLOW + "  %d. " + RESET + BOLD + "%-20s" + RESET +
                CYAN + " %s" + RESET + "\n", number, option, description);
    }

    // 🎨 Status messages
    private static void printSuccess(String message) {
        System.out.println(GREEN + BOLD + "✅ " + message + RESET);
    }

    private static void printError(String message) {
        System.out.println(RED + BOLD + "❌ " + message + RESET);
    }

    private static void printWarning(String message) {
        System.out.println(YELLOW + BOLD + "⚠️  " + message + RESET);
    }

    private static void printInfo(String message) {
        System.out.println(CYAN + BOLD + "ℹ️  " + message + RESET);
    }

    private static void printDivider() {
        System.out.println(PURPLE + "─".repeat(60) + RESET);
    }

    private static void printPrompt(String prompt) {
        System.out.print(BOLD + "➤ " + prompt + ": " + RESET);
    }

    // 🛠️ Utility functions
    private static String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

    private static void pauseForUser() {
        System.out.println(YELLOW + "\nPress Enter to continue..." + RESET);
        try {
            System.in.read();
        } catch (Exception ignored) {}
    }

    // 🔒 Input handling with validation
    private static int getIntInput(String prompt) {
        printPrompt(prompt);
        while (!scanner.hasNextInt()) {
            printError("Please enter a valid number!");
            scanner.next(); // Clear invalid input
            printPrompt(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return input;
    }

    private static float getFloatInput(String prompt) {
        printPrompt(prompt);
        while (!scanner.hasNextFloat()) {
            printError("Please enter a valid number!");
            scanner.next(); // Clear invalid input
            printPrompt(prompt);
        }
        float input = scanner.nextFloat();
        scanner.nextLine(); // Clear buffer
        return input;
    }

    private static String getStringInput(String prompt) {
        printPrompt(prompt);
        return scanner.nextLine().trim();
    }

    // 🔐 Authentication Menu
    private static void renderAuthMenu() {
        printSectionHeader("Authentication", "🔐");
        printMenuOption(1, "Register", "Create a new account");
        printMenuOption(2, "Login", "Access your account");
        printMenuOption(3, "Exit", "Close the application");
        System.out.println();
        printDivider();
    }

    private static boolean handleRegistration() throws InterruptedException {
        clearConsole();
        printSectionHeader("User Registration", "📝");

        String username = getStringInput("Username");
        String password = getStringInput("Password");
        String email = getStringInput("Email");

        try {
            userController.register(username, password, email);
            printSuccess("Registration successful! 🎉");
            Thread.sleep(2000);

            // Auto-login after registration
            clearConsole();
            printInfo("Logging you in automatically...");
            Thread.sleep(1000);

            if (userController.login(email, password)) {
                return true; // Successfully logged in
            }
        } catch (Exception e) {
            printError("Registration failed: " + e.getMessage());
            pauseForUser();
        }
        return false;
    }

    private static boolean handleLogin() throws InterruptedException {
        clearConsole();
        printSectionHeader("User Login", "🔑");

        String email = getStringInput("Email");
        String password = getStringInput("Password");

        try {
            if (userController.login(email, password)) {
                printSuccess("Login successful! Welcome back! 🎉");
                Thread.sleep(1500);
                return true; // Successfully logged in
            } else {
                printError("Invalid credentials! Please try again.");
                pauseForUser();
            }
        } catch (Exception e) {
            printError("Login failed: " + e.getMessage());
            pauseForUser();
        }
        return false;
    }

    private static void authMenu() throws InterruptedException {
        while (true) {
            renderAuthMenu();

            int choice = getIntInput("Select an option");

            switch (choice) {
                case 1 -> {
                    if (handleRegistration()) return; // Successfully registered and logged in
                }
                case 2 -> {
                    if (handleLogin()) return; // Successfully logged in
                }
                case 3 -> {
                    printInfo("Thank you for using Inventory System! Goodbye! 👋");
                    System.exit(0);
                }
                default -> {
                    printError("Invalid option! Please choose 1, 2, or 3.");
                    Thread.sleep(1500);
                    clearConsole();
                    printWelcomeBanner();
                }
            }
        }
    }

    // 📦 Product Menu
    private static void renderProductMenu() {
        printSectionHeader("Product Management", "📦");
        printMenuOption(1, "Add New Product", "Create a new product entry");
        printMenuOption(2, "Search Products", "Find specific products");
        printMenuOption(3, "Logout", "Return to login screen");
        System.out.println();
        printDivider();
    }

    private static void handleViewAllProducts() {
        clearConsole();
        printSectionHeader("All Products", "📋");

        try {
            List<ProductResponDto> products = productController.getAllProducts();

            if (products.isEmpty()) {
                printWarning("No products found in inventory.");
            } else {
                printInfo("Found " + products.size() + " product(s):");
                printDivider();

                products.forEach(product -> {
                    System.out.println(CYAN + "🏷️  " + product + RESET);
                });
            }
        } catch (Exception e) {
            printError("Failed to retrieve products: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void handleAddNewProduct() {
        clearConsole();
        printSectionHeader("Add New Product", "➕");

        try {
            String productName = getStringInput("Product Name");
            float productPrice = getFloatInput("Product Price");
            int productQuantity = getIntInput("Product Quantity");

            ProductCreateDto productCreateDto = new ProductCreateDto(productName, productPrice, productQuantity);
            ProductResponDto product = productController.insertNewProduct(productCreateDto);

            printSuccess("Product added successfully! 🎉");
            System.out.println(GREEN + "📦 " + product + RESET);

        } catch (Exception e) {
            printError("Failed to add product: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void handleSearchProducts() {
        clearConsole();
        printSectionHeader("Search Products", "🔍");

        String searchTerm = getStringInput("Enter search term");

        try {
            // Get all products and filter by search term
            List<ProductResponDto> allProducts = productController.getAllProducts();
            List<ProductResponDto> filteredProducts = allProducts.stream()
                    .filter(product -> product.toString().toLowerCase().contains(searchTerm.toLowerCase()))
                    .toList();

            if (filteredProducts.isEmpty()) {
                printWarning("No products found matching: '" + searchTerm + "'");
            } else {
                printInfo("Found " + filteredProducts.size() + " product(s) matching '" + searchTerm + "':");
                printDivider();
                filteredProducts.forEach(product -> {
                    System.out.println(CYAN + "🔍 " + product + RESET);
                });
            }
        } catch (Exception e) {
            printError("Search failed: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void handleUpdateProduct() {
        clearConsole();
        printSectionHeader("Update Product", "✏️");

        printInfo("Update functionality - Coming Soon!");
        printWarning("This feature will allow you to modify existing products.");
        pauseForUser();
    }

    private static void handleDeleteProduct() {
        clearConsole();
        printSectionHeader("Delete Product", "🗑️");

        printInfo("Delete functionality - Coming Soon!");
        printWarning("This feature will allow you to remove products from inventory.");
        pauseForUser();
    }

    private static void productMenu() throws InterruptedException {
        while (true) {
            clearConsole();
            renderProductMenu();

            int choice = getIntInput("Select an option");

            switch (choice) {
                case 1 -> handleViewAllProducts();
                case 2 -> handleAddNewProduct();
                case 3 -> handleSearchProducts();
                case 4 -> handleUpdateProduct();
                case 5 -> handleDeleteProduct();
                case 6 -> {
                    printInfo("Logging out... See you next time! 👋");
                    Thread.sleep(1500);
                    return; // Return to auth menu
                }
                default -> {
                    printError("Invalid option! Please choose 1-6.");
                    Thread.sleep(1500);
                }
            }
        }
    }

    // 🏠 Main home method
    public static void home() throws InterruptedException {
        while (true) {
            clearConsole();
            printWelcomeBanner();

            if (userController.isUserLoggedIn()) {
                userController.showLoggedInUser();
                productMenu();
                // If we return from product menu, user logged out
                continue;
            }

            authMenu();
            // If we return from auth menu, user logged in
        }
    }

    // 🚀 Main method
    public static void main(String[] args) {
        try {
            home();
        } catch (Exception e) {
            printError("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}