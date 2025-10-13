package com.suarez.service;

import com.suarez.model.Order;

public interface OrderService {
    Order createOrder(Order order);
    Order[] getAllOrders();
    Order getOrderById(int id);
    Order updateOrder(Order order);
    void deleteOrder(int id);
}
