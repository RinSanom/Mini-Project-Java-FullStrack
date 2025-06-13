# E-Commerce Mini Project Java

<img src="https://www.oracle.com/a/tech/img/cb88-java-logo-001.jpg" alt="Java Logo" width="100" height="100"/>

A console-based e-commerce application built with Java that provides a complete shopping experience with user authentication, product management, shopping cart functionality, and order processing.

## Project Status

⚠️ **IMPORTANT: This project is currently under development and is not 100% complete.**

While the core functionality is implemented, there are still some features and improvements that are being worked on:

- **Error Handling**: Some edge cases may not be properly handled yet
- **Input Validation**: Additional validation for user inputs is planned
- **UI Improvements**: Enhancing the console-based user interface
- **Testing**: Comprehensive testing is still in progress
- **Documentation**: Some code documentation needs to be completed

Feel free to use and contribute to this project, but be aware that you might encounter some bugs or incomplete features.

## Features

- **User Authentication**
  - Register new user accounts
  - Login with email and password
  - Secure password hashing
  - Session management

- **Product Management**
  - Add new products with name, price, and quantity
  - Search for products by name
  - View all available products

- **Shopping Cart**
  - Add products to cart
  - View cart with product details and subtotals
  - Calculate total price

- **Order Processing**
  - Create orders from cart items
  - Order confirmation

## Technologies Used

- Java
- MVC Architecture (Model-View-Controller)
- Repository Pattern
- DTO (Data Transfer Objects)
- Console-based UI with ANSI colors
- PostgreSQL database for data persistence
- Properties file for configuration

## Dependencies

The project requires the following external JAR files:

- **PostgreSQL JDBC Driver** (version 42.7.x)
  - Required for database connectivity
  - Download from: [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download/)

- **jBCrypt** (version 0.4 or higher)
  - Used for password hashing
  - Download from: [jBCrypt](https://www.mindrot.org/projects/jBCrypt/)

- **Text Table Formatter** (version 1.1.2)
  - Used for formatting console output tables
  - Download from: [Text-Table-Formatter](https://github.com/iNamik/text-table-formatter/releases)

### Adding Dependencies to Your Project

1. Download the JAR files from the links above
2. In your IDE (IntelliJ IDEA, Eclipse, etc.), add the JAR files to your project:
   - **IntelliJ IDEA**: File > Project Structure > Libraries > + > Java > Select the JAR files
   - **Eclipse**: Right-click on the project > Build Path > Configure Build Path > Libraries > Add External JARs

## Project Structure

```
src/
├── controller/         # Controllers for handling business logic
│   ├── OrderController.java
│   ├── ProductController.java
│   └── UserController.java
├── mapper/             # Mappers for converting between entities and DTOs
│   ├── ProductMapper.java
│   └── UserMapper.java
├── model/              # Data models and business logic
│   ├── dto/            # Data Transfer Objects
│   ├── entities/       # Entity classes
│   ├── repository/     # Data access layer
│   └── service/        # Service layer
├── utiles/             # Utility classes
│   ├── DatabaseConfig.java
│   ├── FileUtil.java
│   └── PasswordHasher.java
├── view/               # User interface
│   ├── TableListAllPro.java
│   └── UI.java
└── Main.java           # Application entry point
```

## Setup and Installation

1. Clone the repository:
   ```
   git clone https://github.com/RinSanom/Mini-Project-Java-FullStrack.git
   ```

2. Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.)

3. Make sure you have Java Development Kit (JDK) installed (version 11 or higher recommended)

4. Configure the database connection:
   - The application uses a PostgreSQL database
   - Database connection details are stored in the `app.properties` file
   - Update the following properties if needed:
     ```
     db_url=jdbc:postgresql://dpg-d11rk6c9c44c73flakdg-a.oregon-postgres.render.com:5432/e_commers_db
     db_username=e_commers_db_user
     db_password=qzSO6esc2ah24W9m2CAhQoH2MbqDYDPs
     ```

5. Build the project

6. Run the `Main.java` file to start the application

## Usage

1. **Starting the Application**
   - Run the `Main.java` file to launch the application
   - You'll be presented with the authentication menu

2. **User Registration**
   - Select "Register" from the menu
   - Enter your username, email, and password
   - Upon successful registration, you'll be automatically logged in

3. **User Login**
   - Select "Login" from the menu
   - Enter your email and password

4. **Product Management**
   - To add a new product, select "Add New Product" and enter the details
   - To search for a product, select "Search Products" and enter the product name

5. **Shopping**
   - View all available products displayed in a table
   - Add products to your cart by selecting "Add Product To Cart" and entering the product UUID and quantity
   - View your cart by selecting "Show Cart"

6. **Placing Orders**
   - From the cart view, select "Make Order" to place an order
   - Order confirmation will be displayed with order details

7. **Logging Out**
   - Select "Logout" to end your session and return to the login screen

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

