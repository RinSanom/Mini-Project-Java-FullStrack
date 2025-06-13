package model.service;

import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.entities.CartItem;

import java.util.List;

public interface ProductService {
    List<ProductResponDto> getAllProducts();
    ProductResponDto insertNewProduct(ProductCreateDto productModel);
    ProductResponDto getProductByName(String productName);
    ProductResponDto getProductByCategory(String productCategory);
    CartItem addToCart(String uuid, Integer quantity);
//    List<CartItem> getAllCartProducts();
}
