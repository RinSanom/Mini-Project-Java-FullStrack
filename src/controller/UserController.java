package controller;

import model.dto.UserResponDto;
import model.service.UserServiceImp;

import java.util.Optional;

public class UserController {
    private static final UserServiceImp userService = new UserServiceImp();

    public void register(String username, String password, String email) {
        boolean success = userService.register(username, password, email);
        if (success) {
            System.out.println("✅ Registration successful!");
        } else {
            System.out.println("❌ Registration failed. Username might be taken.");
        }
    }

    public boolean login(String email, String password) {
        if (userService.isUserLoggedIn()) {
//            System.out.println("⚠️ You are already logged in.");
            return true;
        }

        Optional<UserResponDto> loggedIn = userService.login(email, password);
        if (loggedIn.isPresent()) {
            System.out.println("✅ Login successful. Welcome, " + loggedIn.get().email() + "!");
            return true;
        } else {
            System.out.println("❌ Login failed. Please check your credentials.");
            return false;
        }
    }


    public boolean isUserLoggedIn() {
        return userService.isUserLoggedIn();
    }

    public void showLoggedInUser() {
        userService.getLoggedInUser().ifPresentOrElse(
                user -> System.out.println("👋 Logged in as: " + user.email()),
                () -> System.out.println("")
        );
    }

    public void logout() {
        boolean result = userService.logout();
        if (result) {
            System.out.println("✅ Logout successful.");
        } else {
            System.out.println("❌ Logout failed.");
        }
    }
}
