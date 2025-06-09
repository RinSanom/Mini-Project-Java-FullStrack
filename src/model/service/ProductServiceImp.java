package model.service;

import mapper.ProductMapper;
import model.entities.CartItem;
import model.entities.ProductModel;
import model.dto.ProductCreateDto;
import model.dto.ProductResponDto;
import model.repository.ProductRepository;
import model.repository.ProductRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final List<CartItem> cartList = new ArrayList<>();

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
    public CartItem addToCart(String uuid , Integer quantity ) {
        ProductModel product = productRepository.fineProductByUuid(uuid);
        if(quantity == 0){
            System.out.println("[!] Your Quantity must be greater than 0!");
        }
        else if ((product != null) && (product.getQty() >= quantity)) {
            product.setQty(product.getQty()-quantity);
            CartItem cartItem = new CartItem(ProductMapper.mapFromProductToProductResponDto(product)
                    ,quantity);
            cartList.add(cartItem);
            return cartItem;
        }else {
        System.out.println("[!] Your Quantity is over stock!");
    }
        return null;
    }

    // Additional helper method for retrieving cart items
    @Override
    public List<CartItem> getAllCartProducts() {

        return cartList;
    }
}