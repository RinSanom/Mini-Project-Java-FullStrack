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
    private static final ProductRepositoryImpl productServiceImp = new ProductRepositoryImpl();
    private static List<CartItem> cartList = new ArrayList<>();

    @Override
    public List<ProductResponDto> getAllProducts() {
        List<ProductResponDto> productResponDtoList = new ArrayList<>();
        productRepository.getAll().forEach(p -> {
            productResponDtoList.add(new ProductResponDto(
                    p.getPName(),
                    p.getPrice(),
                    p.getQty(),
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

    @Override
    public CartItem addToCart(String uuid, Integer quantity) {
        ProductModel product = productRepository.fineProductByUuid(uuid);
        if (product == null) {
            System.out.println("[!] Product not found!");
            return null;
        }
        if (quantity == null || quantity <= 0) {
            System.out.println("[!] Quantity must be greater than 0!");
            return null;
        }
        if (product.getQty() < quantity) {
            System.out.println("[!] Quantity exceeds available stock!");
            return null;
        }
        
        // Check if product is already in the cart → if yes → update quantity
        for (CartItem item : cartList) {
            if (item.getProductResponDto().pUuid().equals(uuid)) {
                int newQuantity = item.getQuantity() + quantity;
                // Check total quantity against available stock
                if (product.getQty() < newQuantity) {
                    System.out.println("[!] Total quantity in cart would exceed available stock!");
                    return null;
                }
                item.setQuantity(newQuantity);
                System.out.println("✅ Updated existing cart item: " + item);
                return item;
            }
        }
        
        // Create new cart item
        ProductResponDto newProductDto = new ProductResponDto(
                product.getPName(),
                product.getPrice(),
                product.getQty(),
                product.getPUuid()
        );
        CartItem newItem = new CartItem(newProductDto, quantity);
        cartList.add(newItem);
        return newItem;
    }

//    @Override
    public static List<CartItem> getAllCartProducts() {
        return cartList;
    }

    public ProductModel getProductByUuid(String uuid) {
        return productRepository.fineProductByUuid(uuid);
    }

    public void updateProductStock(String uuid, Integer quantity){
        productRepository.updateProductStock(uuid, quantity);
    }

    public void clearCart(){
        cartList.clear();
    }
}