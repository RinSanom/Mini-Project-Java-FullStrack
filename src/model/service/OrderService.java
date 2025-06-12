package model.service;

import model.entities.OrderProductModel;

public interface OrderService {
    OrderProductModel placeOrder( OrderProductModel orderProductModel );
}
