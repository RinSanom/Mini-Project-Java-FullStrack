package model.service;

import model.dto.ProductResponDto;
import model.dto.UserResponDto;
import model.entities.CartItem;
import model.entities.OrderProductModel;
import model.entities.ProductModel;
import model.entities.UserModel;
import model.repository.OrderProductRepositoryImp;
import utiles.FileUtil;

import java.util.Collections;
import java.util.List;

public class OrderServiceImp implements OrderService {
    private static final OrderProductRepositoryImp orderProductImp = new OrderProductRepositoryImp();
    private static final UserServiceImp userServiceImp = new UserServiceImp();
    private static final ProductServiceImp productServiceImp = new ProductServiceImp();

    @Override
    public OrderProductModel placeOrder() {
        List<CartItem> cartItems = productServiceImp.getAllCartProducts();
        List<UserResponDto> users = userServiceImp.getLoggedInUser()
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot place order.");
        }
        
        Integer userID = 0;
        for (UserResponDto user : users) {
            userID = user.userId();
        }
        
        if (userServiceImp.isUserLoggedIn()) {
            OrderProductModel lastOrder = null;
            
            // Process each cart item
            for (CartItem cartItem : cartItems) {
                ProductResponDto p = cartItem.getProductResponDto();
                ProductModel pModel = productServiceImp.getProductByUuid(p.pUuid());
                
                // Check if enough stock is available
                if (pModel.getQty() < cartItem.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + p.pName() + 
                                             ". Available: " + pModel.getQty() + ", Requested: " + cartItem.getQuantity());
                }
                
                // Create order for this product
                OrderProductModel orderProductModel = new OrderProductModel(userID, pModel.getId());
                lastOrder = orderProductImp.save(orderProductModel);
                
                // Update product stock in database
                productServiceImp.updateProductStock(p.pUuid(), cartItem.getQuantity());
            }
            
            // Clear the cart after successful order
            productServiceImp.clearCart();
            
            return lastOrder;
        }
        
        throw new RuntimeException("User is not logged in. Cannot place order.");
    }
}
