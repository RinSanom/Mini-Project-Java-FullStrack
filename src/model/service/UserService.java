package model.service;

import model.dto.UserResponDto;

import java.util.Optional;

public interface UserService {
    boolean register(String username, String password , String email);
    Optional<UserResponDto> login(String username, String password);
    boolean logout();
    Optional<UserResponDto> getLoggedInUser();
    boolean isUserLoggedIn();
    UserResponDto getUserByUuid(String uuid);
}
