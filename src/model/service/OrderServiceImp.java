package model.service;

import model.entities.OrderProductModel;
import model.repository.OrderProductRepositoryImp;
import utiles.FileUtil;

public class OrderServiceImp implements OrderService {
    private static final OrderProductRepositoryImp orderProductImp = new OrderProductRepositoryImp();
    private static final UserServiceImp userServiceImp = new UserServiceImp();
    private static final ProductServiceImp productServiceImp = new ProductServiceImp();

    private static final String LOGIN_FILE = "SessionUser.txt";
    @Override
    public OrderProductModel placeOrder(OrderProductModel orderProductModel) {

        return null;
    }
}
