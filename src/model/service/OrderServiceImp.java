package model.service;

import model.entities.OrderProductModel;
import model.repository.OrderProductRepositoryImp;

public class OrderServiceImp implements OrderService {
    private static final OrderProductRepositoryImp orderProductImp = new OrderProductRepositoryImp();
    @Override
    public OrderProductModel placeOrder(OrderProductModel orderProductModel) {
        return orderProductImp.save(orderProductModel);
    }
}
