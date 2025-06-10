package model.service;

import mapper.UserMapper;
import model.antities.UserModel;
import model.dto.UserResponDto;
import model.repository.UserRepository;
import model.repository.UserRepositoryImpl;
import utiles.FileUtil;
import utiles.PasswordHasher;

import java.util.Optional;
import java.util.UUID;

public class UserServiceImp implements UserService{
    private static final String LOGIN_FILE = "SessionUser.txt";
    private static final UserRepository userRepository = new UserRepositoryImpl();
    @Override
    public boolean register(String username, String password , String email) {
        if(userRepository.isUsernameTaken(email)){
            System.out.println("Email is taken");
            return false;
        }
        UserModel userModel = new UserModel(null , UUID.randomUUID().toString() , username , email , PasswordHasher.hashPassword(password) , false);
        FileUtil.writeToFile(LOGIN_FILE, userModel.getUUID());
        return userRepository.registerUser(userModel);
    }

    @Override
    public Optional<UserResponDto> login(String email, String password) {
        Optional<UserModel> userOpt = userRepository.login(email, password);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            if (PasswordHasher.checkPassword(password, user.getPassword())) {
                FileUtil.writeToFile(LOGIN_FILE, user.getUUID());
                return Optional.of(new UserResponDto(user.getUUID(), user.getUserName()));
            } else {
                System.out.println("❌ Incorrect password");
            }
        } else {
            System.out.println("❌ User not found");
        }
        return Optional.empty();
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public Optional<UserResponDto> getLoggedInUser() {
        String uuid = FileUtil.readFromFile(LOGIN_FILE);
        if (uuid != null){
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean isUserLoggedIn() {
        String uuid = FileUtil.readFromFile(LOGIN_FILE);
        return uuid != null && !uuid.isEmpty();
    }

    @Override
    public UserResponDto getUserByUuid(String uuid) {
        return UserMapper.mapFromUserModelToUserResponDto(userRepository.fineUserByUuid(uuid));
    }
}
