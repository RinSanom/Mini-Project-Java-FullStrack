package model.service;

import mapper.ProductMapper;
import model.entities.ProductModel;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.repository.ProductRepository;
import model.repository.ProductRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final List<ProductResponDto> cart = new ArrayList<>();

    @Override
    public List<ProductResponDto> getAllProducts() {
        List<ProductResponDto> productResponDtoList = new ArrayList<>();
        productRepository.getAll().forEach(p -> {
            productResponDtoList.add(new ProductResponDto(
                    p.getPName(),
                    p.getPrice(),
                    p.getQty(),
                    p.isDeleted(),
                    p.getPUuid()
            ));
        });
        return productResponDtoList;
    }

    @Override
    public ProductResponDto insertNewProduct(ProductCreateDto productModel) {
        ProductModel productModel1 = ProductMapper.mapFromProductCreateDtoToProduct(productModel);
        return ProductMapper.mapFromProductToProductResponDto(productRepository.save(productModel1));
    }

    @Override
    public ProductResponDto getProductByName(String productName) {
        return ProductMapper.mapFromProductToProductResponDto(productRepository.fineProductByName(productName));
    }

    @Override
    public ProductResponDto getProductByCategory(String productCategory) {
        return null;
    }

    // Implemented method addToCart
    @Override
    public ProductResponDto addToCart(String UUID) {
        ProductModel product = productRepository.findByUUID(UUID);
        if (product != null) {
            ProductResponDto dto = ProductMapper.mapFromProductToProductResponDto(product);
            cart.add(dto); // Add without any limit or check
            return dto;
        }
        return null;
    }

    // Additional helper method for retrieving cart items
    public List<ProductResponDto> getCartProducts() {
        return cart;
    }
}