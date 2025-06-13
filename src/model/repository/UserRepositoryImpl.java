package model.repository;

import model.entities.UserModel;
import utiles.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository{
    @Override
    public boolean registerUser(UserModel userModel) {
        String sql = """
                INSERT INTO users (user_name , email , password , is_deleted , u_uuid) values (?,?,?,?,?)
                """;
        try(Connection connection = DatabaseConfig.getDataConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , userModel.getUserName());
            preparedStatement.setString(2 , userModel.getEmail());
            preparedStatement.setString(3 , userModel.getPassword());
            preparedStatement.setBoolean(4 , false);
            preparedStatement.setString(5 , userModel.getUUID());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println(" User repor Resgister Error  " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<UserModel> login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DatabaseConfig.getDataConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new UserModel(
                        resultSet.getInt("id"),
                        resultSet.getString("u_uuid"),
                        resultSet.getString("user_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_deleted")
                ));
            }
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean isUsernameTaken(String userName) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try ( Connection connection = DatabaseConfig.getDataConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , userName);
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            System.out.println("Check Email Error" + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<UserModel> getUserByUUID(String uuid) {
        String sql = "SELECT id, user_name, email FROM users WHERE u_uuid = ?";
        try (Connection conn = DatabaseConfig.getDataConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("id"));
                System.out.println("User id: " + user.getUserId());
                return Optional.of(user);
            }
        } catch (Exception e) {
            System.out.println("Check UUID Error: " + e.getMessage());
        }
        return Optional.empty();
    }

}
