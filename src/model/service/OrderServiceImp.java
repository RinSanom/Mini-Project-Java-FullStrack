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
        Integer userID = 0;
        for (UserResponDto user : users) {
            userID = user.userId();
        }
        if (userServiceImp.isUserLoggedIn()) {
            for (CartItem cartItem : cartItems) {
                 ProductResponDto p = cartItem.getProductResponDto();
                 ProductModel pModel = productServiceImp.getProductByUuid(p.pUuid());
                 OrderProductModel orderProductModel = new OrderProductModel(userID,pModel.getId());
                 return orderProductImp.save(orderProductModel);
            }
        }
        return null;
    }
}
