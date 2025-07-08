package model.repository;

import model.entities.ProductModel;

import java.util.List;

public interface ProductRepository {
    List<ProductModel> getAll();
    ProductModel save(ProductModel productModel);
    ProductModel fineProductByName(String pName);
    ProductModel fineProductByCategory(String CategoryName);
    ProductModel fineProductByUuid(String uuid);
    ProductModel updateProductStock(String uuid, Integer quantity);
}
