package controller;

import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.service.ProductServiceImp;

import java.util.List;

public class ProductController {
    private ProductServiceImp productServiceImp = new ProductServiceImp();

    public List<ProductResponDto> getAllProducts() {
        return productServiceImp.getAllProducts();
    }

    public ProductResponDto getProductByName(String name) {
        return productServiceImp.getProductByName(name);
    }

    public ProductResponDto insertNewProduct(ProductCreateDto productModel) {
        return productServiceImp.insertNewProduct(productModel);
    }

    // New method for adding product to cart
    public ProductResponDto addProductToCart(String UUID) {
        return productServiceImp.addToCart(UUID);
    }

    // New method for showing cart products
    public List<ProductResponDto> showCart() {
        return productServiceImp.getCartProducts();
    }
}
