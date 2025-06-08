package model.repository;

import model.antities.UserModel;

import java.util.Optional;

public interface UserRepository {
    boolean registerUser(UserModel userModel);
    Optional<UserModel> login(String userName, String password);
    boolean isUsernameTaken (String userName);
}
