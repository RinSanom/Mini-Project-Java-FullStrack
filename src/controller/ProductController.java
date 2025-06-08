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
    public ProductResponDto getProductByName(String name){
        return productServiceImp.getProductByName(name);
    }
    public ProductResponDto insertNewProduct(ProductCreateDto productModel) {
        return productServiceImp.insertNewProduct(productModel);


    }
}
