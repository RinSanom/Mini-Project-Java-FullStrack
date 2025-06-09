package model.repository;

import model.entities.ProductModel;

import java.util.List;

public interface ProductRepository {
    List<ProductModel> getAll();
    ProductModel save(ProductModel productModel);

    ProductModel fineProductByName(String pName);
    ProductModel fineProductByCategory(String CategoryName);

    // Find and return a product by unique UUID
    ProductModel fineProductByUuid(String uuid);

}
