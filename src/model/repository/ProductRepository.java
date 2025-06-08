package model.repository;

import model.antities.ProductModel;
import model.dto.ProductResponDto;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository {
    List<ProductModel> getAll();
    ProductModel save(ProductModel productModel);

    ProductModel fineProductByName(String pName);
    ProductModel fineProductByCategory(String CategoryName);


}
