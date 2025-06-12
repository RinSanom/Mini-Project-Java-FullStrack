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

        // ✅ Update product stock in DB first (in memory here, assume later save to DB)
        product.setQty(product.getQty() - quantity);

        // ✅ Check if product is already in the cart → if yes → update quantity
        for (CartItem item : cartList) {
            if (item.getProductResponDto().pUuid().equals(uuid)) {
                item.setQuantity(item.getQuantity() + quantity);

                // 🔔 ❗❗ Now update ProductResponDto to reflect NEW stock qty
                ProductResponDto updatedProductDto = new ProductResponDto(
                        product.getPName(),
                        product.getPrice(),
                        product.getQty(),   // ✅ updated stock
                        product.getPUuid()
                );
                item.setProductResponDto(updatedProductDto);

                System.out.println("✅ Updated existing cart item: " + item);
                return item;
            }
        }

        ProductResponDto newProductDto = new ProductResponDto(
                product.getPName(),
                product.getPrice(),
                product.getQty(),
                product.getPUuid()
        );

        CartItem newItem = new CartItem(newProductDto, quantity);
        cartList.add(newItem);
//        System.out.println("✅ New item added to cart: " + newItem);
        return newItem;
    }
    // Additional helper method for retrieving cart items
    @Override
    public List<CartItem> getAllCartProducts() {
//        System.out.println("Helo from Product cart");
//        System.out.println("Cart List: " + cartList + "\n\n");
        return cartList;
    }
}