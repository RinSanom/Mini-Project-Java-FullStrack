package controller;

import model.service.OrderServiceImp;

public class OrderController {
    private static final OrderServiceImp orderServiceImp = new OrderServiceImp();

    public OrderServiceImp crateOrder( String pUUID , Integer userID ) {
        return orderServiceImp;
    }
}
