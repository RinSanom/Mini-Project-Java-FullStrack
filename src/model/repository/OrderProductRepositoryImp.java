package model.repository;

import model.entities.OrderProductModel;
import utiles.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderProductRepositoryImp implements OrderProducts{
    @Override
    public OrderProductModel save(OrderProductModel orderProducts) {
        String sql = "INSERT INTO orders_products (user_id, p_id) VALUES (?, ?)";
        try(Connection connection = DatabaseConfig.getDataConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , orderProducts.getUserId());
            preparedStatement.setInt(2 , orderProducts.getProductId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error during save order product: " + e.getMessage());
        }
        return orderProducts;
    }
}
