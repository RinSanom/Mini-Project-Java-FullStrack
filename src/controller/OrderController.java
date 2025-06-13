package controller;

import model.entities.OrderProductModel;
import model.service.OrderServiceImp;

public class OrderController {
    private static final OrderServiceImp orderServiceImp = new OrderServiceImp();

    public OrderProductModel crateOrder() {
        return orderServiceImp.placeOrder();
    }
}
