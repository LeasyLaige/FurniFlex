package com.suarez.serviceimpl;

import com.suarez.entity.OrderData;
import com.suarez.model.Order;
import com.suarez.repository.OrderDataRepository;
import com.suarez.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderDataRepository orderDataRepository;

    @Override
    public Order getOrderById(int id) {
        logger.info(" Getting order with id: " + id);
        Order order = null;
        Optional<OrderData> optional = orderDataRepository.findById(id);

        if(optional.isPresent()) {
            logger.info(" Is present >>");
            order = new Order();

            order.setId(optional.get().getId());
            order.setCustomer(optional.get().getCustomer());
            order.setProduct(optional.get().getProduct());
            order.setQuantity(optional.get().getQuantity());
            order.setCreated(optional.get().getCreated());
            order.setLastUpdated(optional.get().getLastUpdated());
            order.setStatus(optional.get().getStatus());
            logger.info(" Success >> found id: " + id);
        } else {
            logger.info(" Failed >> unable to locate id: " + id);
        }

        return order;
    }

    @Override
    public Order[] getAllOrders() {
        List<OrderData> orderDataList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();

        orderDataRepository.findAll().forEach(orderDataList::add);
        Iterator<OrderData> iterator = orderDataList.iterator();

        while (iterator.hasNext()) {
            OrderData orderData = iterator.next();
            Order order = new Order();

            order.setId(orderData.getId());
            order.setCustomer(orderData.getCustomer());
            order.setProduct(orderData.getProduct());
            order.setQuantity(orderData.getQuantity());
            order.setLastUpdated(orderData.getLastUpdated());
            order.setCreated(orderData.getCreated());
            order.setStatus(orderData.getStatus());
            orderList.add(order);
        }

        logger.info(" Found " + orderList.size() + " orders");

        Order[] orderArray = new Order[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            orderArray[i] = orderList.get(i);
        }

        return orderArray;
    }

    @Override
    public Order createOrder(Order order) {
        logger.info("Creating order with customer: " + order.getCustomer() + " and product: " + order.getProduct());
        OrderData orderData = new OrderData();
        orderData.setCreated(order.getCreated());
        orderData.setCustomer(order.getCustomer()); // To be implemented
        orderData.setProduct(order.getProduct()); // To be implemented
        orderData.setQuantity(order.getQuantity());
        orderData.setStatus(order.getStatus());
        orderData = orderDataRepository.save(orderData);

        logger.info("Order created with id: " + orderData.getId());
        Order newOrder = new Order();
        newOrder.setId(orderData.getId());
        newOrder.setCreated(orderData.getCreated());
        newOrder.setCustomer(orderData.getCustomer());
        newOrder.setProduct(orderData.getProduct());
        newOrder.setQuantity(orderData.getQuantity());
        newOrder.setStatus(orderData.getStatus());

        return newOrder;
    }

    @Override
    public Order updateOrder(Order order) {
        Order updatedOrder = null;
        int id = order.getId();
        Optional<OrderData> orderData = orderDataRepository.findById(id);

        if(orderData.isPresent()) {
            OrderData existingOrderData = orderData.get();
            existingOrderData.setCreated(order.getCreated());
            existingOrderData.setCustomer(order.getCustomer()); // To be implemented
            existingOrderData.setProduct(order.getProduct()); // To be implemented
            existingOrderData.setQuantity(order.getQuantity());
            existingOrderData.setStatus(order.getStatus());
            existingOrderData = orderDataRepository.save(existingOrderData);

            updatedOrder = new Order();
            updatedOrder.setId(existingOrderData.getId());
            updatedOrder.setCreated(existingOrderData.getCreated());
            updatedOrder.setCustomer(existingOrderData.getCustomer());
            updatedOrder.setProduct(existingOrderData.getProduct());
            updatedOrder.setQuantity(existingOrderData.getQuantity());
            updatedOrder.setStatus(existingOrderData.getStatus());
        } else {
            logger.warn("Order with id: " + id + " not found");
        }

        return updatedOrder;
    }

    @Override
    public void deleteOrder(int id) {
        Order order = null;
        logger.info("Deleting order with id: " + id);
        Optional<OrderData> orderData = orderDataRepository.findById(id);

        if(orderData.isPresent()) {
            OrderData orderDataToDelete = orderData.get();
            orderDataRepository.delete(orderDataToDelete);
            logger.info("Successfully deleted order with id: " + id);
        } else {
            logger.warn("Unable to delete order with id: " + id + " not found");
        }
    }
}
