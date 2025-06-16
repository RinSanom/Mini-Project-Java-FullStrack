package view;

import controller.OrderController;
import controller.ProductController;
import controller.UserController;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.entities.CartItem;
import model.entities.OrderProductModel;
import model.repository.Insert10MRecord;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

import static java.awt.Color.WHITE;
import static view.TableListAllPro.showTableListAllPro;

public class UI {
    private static final ProductController productController = new ProductController();
    public static final UserController userController = new UserController();
    private static OrderController orderController = new OrderController();
    private static final String LOGIN_FILE = "SessionUser.txt";
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
        System.out.println();
    }

    private static void printWelcomeBanner() {
//        String[] bannerLines = {
//                "╔══════════════════════════════════════════════════════════════╗",
//                "                    🚀 INVENTORY SYSTEM 🚀                     ",
//                "                     Welcome to Management                     ",
//                "╚══════════════════════════════════════════════════════════════╝"
//        };
//
//        for (String line : bannerLines) {
//            System.out.println(centerText(CYAN + BOLD + line + RESET, CONSOLE_WIDTH));
//        }
//        System.out.println();
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
        printSectionHeader("Create An Account ", "");
        printMenuOption(1, "Register", "Create a new account");
        printMenuOption(2, "Login", "   Access your account");
        printMenuOption(3, "Exit", "   Close the application");
        System.out.println();
        printDivider();
    }

    private static boolean handleRegistration() throws InterruptedException {
        clearConsole();
        printSectionHeader("User Registration", "📝");
        String username = getStringInput("Username: ");
        String email =  getStringInput(  "Email: ");
        String password = getStringInput("Password: ");

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

                case 4 -> {
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

    private static void renderProductMenu() {
        printSectionHeader("Product Shopping", "");
        printMenuOption(1, "Add New Product", "Create a new product entry");
        printMenuOption(2, "Search Products", "Find specific products     ");
        printMenuOption(3, "Add Product To Cart", "Add a product to cart  ");
        printMenuOption(4, "Show Cart", "View Your cart products          ");
        printMenuOption(5, "Insert 10M records", "Insert 10M records to DB");
        printMenuOption(6, "Logout", "Return to login screen              ");
        System.out.println();
        printDivider();
    }

    private static boolean handleViewAllProducts() {
        showTableListAllPro( productController.getAllProducts());
        return true;
    }

    private static void handleAddNewProduct() {
        clearConsole();
        printSectionHeader("Add New Product", "➕");

        try {
            String productName = getStringInput("Product Name");
            float productPrice = getFloatInput( "Product Price");
            int productQuantity = getIntInput(  "Product Quantity");

            ProductCreateDto productCreateDto = new ProductCreateDto(productName, productPrice, productQuantity);
            ProductResponDto product = productController.insertNewProduct(productCreateDto);

            printSuccess("Product added successfully! 🎉");
//            String productLine = GREEN + "📦 " + product + RESET;
//            System.out.println(centerText(productLine, CONSOLE_WIDTH));

        } catch (Exception e) {
            printError("Failed to add product: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void handleSearchProducts() {
        clearConsole();
        printSectionHeader("Search Products", "🔍");

        String searchTerm = getStringInput("Enter search term");
        ProductResponDto product = productController.getProductByName(searchTerm);

        if (product == null) {
            System.out.println(centerText(RED + "❗ Product not found." + RESET, CONSOLE_WIDTH));
            pauseForUser();
            return;
        }

        int CONSOLE_WIDTH = 150;
        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        String[] header = {
                BOLD + YELLOW + "Product Name" + RESET,
                BOLD + GREEN + "Price" + RESET,
                BOLD + BLUE + "Quantity" + RESET,
                BOLD + PURPLE + "UUID" + RESET
        };
        for (String column : header) {
            table.addCell(column, new CellStyle(CellStyle.HorizontalAlign.center));
        }

        table.addCell(CYAN + product.pName() + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
        table.addCell(GREEN + product.price().toString() + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
        table.addCell(BLUE + product.qty().toString() + " pcs" + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
        table.addCell(PURPLE + product.pUuid() + RESET, new CellStyle(CellStyle.HorizontalAlign.center));

        table.setColumnWidth(0, 20, 40);
        table.setColumnWidth(1, 10, 20);
        table.setColumnWidth(2, 10, 20);
        table.setColumnWidth(3, 40, 80);

        // Center each line of the table
        String[] tableLines = table.render().split("\n");
        for (String line : tableLines) {
            System.out.println(centerText(line, CONSOLE_WIDTH));
        }

        pauseForUser();
    }

    private static void handleAppProductToCart() {
        clearConsole();
        printSectionHeader("Add Product To Cart ", "");
        String uuid = getStringInput("Product UUID");
        int quantity = getIntInput("Quantity");
        try {
            productController.addProductToCart(uuid,quantity);
            printSuccess("Product Add To successful! 🎉");
        } catch (Exception e) {
            System.out.println("Add Error" + e.getMessage() );
        }
        pauseForUser();
    }

    private static void handleShowCart() {
        clearConsole();
        printSectionHeader("🛒 Your Shopping Cart", "🛍️");
        List<CartItem> cartItems = productController.showCart();

        if (cartItems.isEmpty()) {
            System.out.println(centerText(RED + "❗ Your cart is empty." + RESET, CONSOLE_WIDTH));
            pauseForUser();
            return;
        }

        int CONSOLE_WIDTH = 150;
        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        String[] header = {
                BOLD + YELLOW + "Product Name" + RESET,
                BOLD + GREEN + "Unit Price" + RESET,
                BOLD + BLUE + "Quantity" + RESET,
                BOLD + PURPLE + "Subtotal" + RESET
        };
        for (String column : header) {
            table.addCell(column, new CellStyle(CellStyle.HorizontalAlign.center));
        }

        double totalPrice = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            String rowColor = (i % 2 == 0) ? RED : CYAN;

            double subtotal = item.getProductResponDto().price() * item.getQuantity();
            totalPrice += subtotal;

            table.addCell(rowColor + item.getProductResponDto().pName() + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
            table.addCell(GREEN + item.getProductResponDto().price().toString() + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
            table.addCell(BLUE + item.getQuantity() + " pcs" + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
            table.addCell(PURPLE + String.format("$%.2f", subtotal) + RESET, new CellStyle(CellStyle.HorizontalAlign.center));
        }

        table.setColumnWidth(0, 20, 40);
        table.setColumnWidth(1, 10, 20);
        table.setColumnWidth(2, 10, 20);
        table.setColumnWidth(3, 20, 40);

        String[] tableLines = table.render().split("\n");
        for (String line : tableLines) {
            System.out.println(centerText(line, CONSOLE_WIDTH));
        }
        System.out.println(centerText(BOLD + GREEN + "💰 Total Amount: $" + String.format("%.2f", totalPrice) + RESET, CONSOLE_WIDTH));

        // ✅ Offer choices: 1) Make Order or 2) Go Back
        System.out.println();
        System.out.println(centerText("1. Make Order",   CONSOLE_WIDTH));
        System.out.println(centerText("0. Back to Menu", CONSOLE_WIDTH));

        int choice = getIntInput("Select an option");
        if (choice == 1) {
            try {
                handleMakeOrder();
            } catch (InterruptedException e) {
                System.out.println("❗ Order process interrupted.");
            }
        }
    }

    private static void handleLogout() throws InterruptedException {
        clearConsole();
        printSectionHeader("Logout", "🚪");
        userController.logout();
        printSuccess("You have been logged out successfully!");
        Thread.sleep(1500);
        clearConsole();
        printWelcomeBanner();
    }

    private static void handleMakeOrder() throws InterruptedException {
        clearConsole();
        printSectionHeader("📦 Make Your Order", "");
        try {
            printWarning("Product is Ordering........");
            OrderProductModel orderProductModel = orderController.crateOrder(  );
            System.out.println();
            System.out.println(centerText(BOLD + GREEN + "✅ Your order has been placed successfully!" + RESET, CONSOLE_WIDTH));
            System.out.println(centerText("Order Product ID: " + CYAN + orderProductModel.getProductId() + RESET, CONSOLE_WIDTH));
            System.out.println(centerText("User ID: " + YELLOW + orderProductModel.getUserId() + RESET, CONSOLE_WIDTH));
        } catch (Exception e) {
            System.out.println();
            System.out.println(centerText(RED + "❗ Failed to place order: " + e.getMessage() + RESET, CONSOLE_WIDTH));
        }
        pauseForUser();
    }

    private static void productMenu() throws InterruptedException {
        while (true) {
            clearConsole();
            renderProductMenu();
            printWarning( "Fetching products..." );
            boolean hasProducts = handleViewAllProducts();
            if (!hasProducts) {
                System.out.println("No products available at the moment.\n");
            }
            int choice = getIntInput("Select an option");
            switch (choice) {
                case 1 -> handleAddNewProduct();
                case 2 -> handleSearchProducts();
                case 3 -> handleAppProductToCart();
                case 4 -> handleShowCart();
                case 5 -> handleIsert10Mrecord();
                case 6 -> {
                    handleLogout();
                    return;
                }
                default -> {
                    printError("Invalid option! Please choose 1-5.");
                    Thread.sleep(1500);
                }
            }
        }
    }

    private static void handleIsert10Mrecord(){
        Insert10MRecord.insertTenMillionProducts();
    }

    public static void home() throws InterruptedException {
        while (true) {
            clearConsole();
            printWelcomeBanner();
            if (userController.isUserLoggedIn()) {
                String userInfo = String.valueOf(userController.isUserLoggedIn());
//                System.out.println(centerText(CYAN + "👤 " + userInfo + RESET, CONSOLE_WIDTH));
                System.out.println();
                productMenu();
                continue;
            }
            authMenu();
        }
    }

}
