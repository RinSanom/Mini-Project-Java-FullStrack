package model.service;

import model.antities.ProductModel;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.repository.ProductRepository;

import java.util.List;

public interface ProductService {
    List<ProductResponDto> getAllProducts();
    ProductResponDto insertNewProduct(ProductCreateDto productModel);
    ProductResponDto getProductByName(String productName);
    ProductResponDto getProductByCategory(String productCategory);

    List<ProductRepository> getAllProductsInCart();
    ProductRepository addProductToCart(String productUuid,Integer quantity);
}
