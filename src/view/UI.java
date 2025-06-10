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

    // Console width for centering (adjust based on your terminal size)
    private static final int CONSOLE_WIDTH = 150;

    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String CYAN = "\033[96m";
    private static final String GREEN = "\033[92m";
    private static final String RED = "\033[91m";
    private static final String YELLOW = "\033[93m";
    private static final String BLUE = "\033[94m";
    private static final String PURPLE = "\033[95m";

    public static void clearConsole() {
        try {
            Thread.sleep(200);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (InterruptedException ignored) {}
    }

    private static void printWelcomeBanner() {
        String[] bannerLines = {
                "╔══════════════════════════════════════════════════════════════╗",
                "                    🚀 INVENTORY SYSTEM 🚀                     ",
                "                     Welcome to Management                     ",
                "╚══════════════════════════════════════════════════════════════╝"
        };

        for (String line : bannerLines) {
            System.out.println(centerText(CYAN + BOLD + line + RESET, CONSOLE_WIDTH));
        }
        System.out.println();
    }

    private static void printSectionHeader(String title, String icon) {
        String border = "═".repeat(50);
        String headerLine1 = "╔" + border + "╗";
        String headerLine2 = "" + centerText(icon + " " + title + " " + icon, 10) + "";
        String headerLine3 = "╚" + border + "╝";

        System.out.println(centerText(BLUE + BOLD + headerLine1 + RESET, CONSOLE_WIDTH));
        System.out.println(centerText(BLUE + BOLD + headerLine2 + RESET, CONSOLE_WIDTH));
        System.out.println(centerText(BLUE + BOLD + headerLine3 + RESET, CONSOLE_WIDTH));
        System.out.println();
    }

    private static void printMenuOption(int number, String option, String description) {
        String menuLine = String.format("  %d. %-20s %s", number, option, description);
        String coloredLine = YELLOW + "  " + number + ". " + RESET + BOLD + option + RESET +
                CYAN + " " + description + RESET;
        System.out.println(centerText(coloredLine, CONSOLE_WIDTH));
    }

    private static void printSuccess(String message) {
        String successLine = GREEN + BOLD + "✅ " + message + RESET;
        System.out.println(centerText(successLine, CONSOLE_WIDTH));
    }

    private static void printError(String message) {
        String errorLine = RED + BOLD + "❌ " + message + RESET;
        System.out.println(centerText(errorLine, CONSOLE_WIDTH));
    }

    private static void printWarning(String message) {
        String warningLine = YELLOW + BOLD + "⚠️  " + message + RESET;
        System.out.println(centerText(warningLine, CONSOLE_WIDTH));
    }

    private static void printInfo(String message) {
        String infoLine = CYAN + BOLD + "ℹ️  " + message + RESET;
        System.out.println(centerText(infoLine, CONSOLE_WIDTH));
    }

    private static void printDivider() {
        String divider = PURPLE + "─".repeat(60) + RESET;
        System.out.println(centerText(divider, CONSOLE_WIDTH));
    }

    private static void printPrompt(String prompt) {
        String promptLine = BOLD + "➤ " + prompt + ": " + RESET;
        System.out.print(centerText(promptLine, CONSOLE_WIDTH - 10)); // Leave space for input
    }

    private static String centerText(String text, int width) {
        // Remove ANSI codes for length calculation
        String plainText = text.replaceAll("\033\\[[0-9;]*m", "");
        if (plainText.length() >= width) return text;

        int padding = (width - plainText.length()) / 2;
        return " ".repeat(padding) + text;
    }

    private static void pauseForUser() {
        String pauseMessage = YELLOW + "\nPress Enter to continue..." + RESET;
        System.out.println(centerText(pauseMessage, CONSOLE_WIDTH));
        try {
            System.in.read();
        } catch (Exception ignored) {}
    }

    private static int getIntInput(String prompt) {
        printPrompt(prompt);
        while (!scanner.hasNextInt()) {
            printError("Please enter a valid number!");
            scanner.next();
            printPrompt(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private static float getFloatInput(String prompt) {
        printPrompt(prompt);
        while (!scanner.hasNextFloat()) {
            printError("Please enter a valid number!");
            scanner.next();
            printPrompt(prompt);
        }
        float input = scanner.nextFloat();
        scanner.nextLine();
        return input;
    }

    private static String getStringInput(String prompt) {
        printPrompt(prompt);
        return scanner.nextLine().trim();
    }

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

            clearConsole();
            printInfo("Logging you in automatically...");
            Thread.sleep(1000);

            if (userController.login(email, password)) {
                return true;
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
                return true;
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
        try {
            List<ProductResponDto> products = productController.getAllProducts();

            if (products.isEmpty()) {
                printWarning("No products found in inventory.");
            } else {
                printInfo("Products in Shop " + products.size());
                printDivider();
                products.forEach(product -> {
                    String productLine = CYAN + "🏷️  " + product + RESET;
                    System.out.println(centerText(productLine, CONSOLE_WIDTH));
                });
            }
        } catch (Exception e) {
            printError("Failed to retrieve products: " + e.getMessage());
        }
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
            String productLine = GREEN + "📦 " + product + RESET;
            System.out.println(centerText(productLine, CONSOLE_WIDTH));

        } catch (Exception e) {
            printError("Failed to add product: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void handleSearchProducts() {
        clearConsole();
        printSectionHeader("Search Products", "🔍");
        String searchTerm = getStringInput("Enter search term");
        String result = productController.getProductByName(searchTerm).toString();
        String resultLine = CYAN + result + RESET;
        System.out.println(centerText(resultLine, CONSOLE_WIDTH));
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
            handleViewAllProducts();
            int choice = getIntInput("Select an option");
            switch (choice) {
                case 1 -> handleAddNewProduct();
                case 2 -> handleSearchProducts();
                case 3 -> {
                    printInfo("Logging out... See you next time! 👋");
                    Thread.sleep(1500);
                    return; // Return to auth menu
                }
                default -> {
                    printError("Invalid option! Please choose 1-3.");
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
                String userInfo = String.valueOf(userController.isUserLoggedIn());
                System.out.println(centerText(CYAN + "👤 " + userInfo + RESET, CONSOLE_WIDTH));
                System.out.println();
                productMenu();
                continue;
            }

            authMenu();
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