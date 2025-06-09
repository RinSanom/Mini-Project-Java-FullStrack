package model.repository;

import model.antities.ProductModel;

import utiles.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<ProductModel> getAll() {
        String sql = "SELECT * FROM products";
        List<ProductModel> productModels = new ArrayList<>();

        try (Connection con = DatabaseConfig.getDataConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                ProductModel productModel = new ProductModel();
                productModel.setId(rs.getInt("id"));
                productModel.setPName(rs.getString("p_name"));
                productModel.setPrice(rs.getFloat("price"));
                productModel.setQty(rs.getInt("qty"));
                productModel.setDeleted(rs.getBoolean("is_deleted"));
                productModel.setPUuid(rs.getString("p_uuid"));

                productModels.add(productModel);
            }
        } catch (Exception e) {
            System.out.println("Error during get all products: " + e.getMessage());
        }

        return productModels;
    }


    @Override
    public ProductModel save(ProductModel productModel) {
        String sql = """
       INSERT INTO products (p_name, price, qty, is_deleted, p_uuid) VALUES (?, ?, ?, ?, ?)
""";
        try (Connection con = DatabaseConfig.getDataConnection()){
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, productModel.getPName());
            pre.setFloat(2, productModel.getPrice());
            pre.setInt(3, productModel.getQty());
            pre.setBoolean(4, productModel.isDeleted());
            pre.setString(5, productModel.getPUuid());
            int rowEffected = pre.executeUpdate();
            return rowEffected > 0 ? productModel : null ;
        }catch (Exception e){
            System.out.println("Error during save product" + e.getMessage());
        }
        return productModel;
    }

    @Override
    public ProductModel fineProductByName(String pName) {
        String sql = "SELECT * FROM products WHERE p_name = ?";

        try( Connection con = DatabaseConfig.getDataConnection()){
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, pName);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                ProductModel productModel = new ProductModel();
                productModel.setId(rs.getInt("id"));
                productModel.setPName(rs.getString("p_name"));
                productModel.setPrice(rs.getFloat("price"));
                productModel.setQty(rs.getInt("qty"));
                productModel.setDeleted(rs.getBoolean("is_deleted"));
                productModel.setPUuid(rs.getString("p_uuid"));
                return productModel;
            }

        } catch (Exception e) {
            System.out.printf("Error during get product by name: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ProductModel fineProductByCategory(String CategoryName) {
        return null;
    }

    @Override
    public ProductModel fineProductByUuid(String uuid) {
        String sql = """
                SELECT * FROM products WHERE p_uuid = ?
                """;
        try(Connection con = DatabaseConfig.getDataConnection()){
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, uuid);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                ProductModel productModel = new ProductModel();
                productModel.setId(resultSet.getInt("id"));
                productModel.setPName(resultSet.getString("p_name"));
                productModel.setPrice(resultSet.getFloat("price"));
                productModel.setQty(resultSet.getInt("qty"));
                productModel.setDeleted(resultSet.getBoolean("is_deleted"));
                productModel.setPUuid(resultSet.getString("p_uuid"));
                return productModel;
            }
        }catch (Exception exception){
            System.out.println("Error during get product by uuid: " + exception.getMessage());
        }
        return null;
    }
}
