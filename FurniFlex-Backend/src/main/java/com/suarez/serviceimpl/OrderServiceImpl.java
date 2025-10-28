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
            // Map items
            java.util.List<com.suarez.model.OrderItem> items = new java.util.ArrayList<>();
            if (optional.get().getItems() != null) {
                for (com.suarez.entity.OrderItemData oid : optional.get().getItems()) {
                    com.suarez.model.OrderItem mi = new com.suarez.model.OrderItem();
                    mi.setProduct(oid.getProduct());
                    mi.setQuantity(oid.getQuantity());
                    items.add(mi);
                }
            }
            order.setItems(items);
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
            java.util.List<com.suarez.model.OrderItem> items = new java.util.ArrayList<>();
            if (orderData.getItems() != null) {
                for (com.suarez.entity.OrderItemData oid : orderData.getItems()) {
                    com.suarez.model.OrderItem mi = new com.suarez.model.OrderItem();
                    mi.setProduct(oid.getProduct());
                    mi.setQuantity(oid.getQuantity());
                    items.add(mi);
                }
            }
            order.setItems(items);
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
        logger.info("Creating order with customer: " + order.getCustomer());
        OrderData orderData = new OrderData();
        orderData.setCreated(order.getCreated());
        orderData.setCustomer(order.getCustomer());
        // Map items from model
        java.util.List<com.suarez.entity.OrderItemData> items = new java.util.ArrayList<>();
        if (order.getItems() != null) {
            for (com.suarez.model.OrderItem mi : order.getItems()) {
                com.suarez.entity.OrderItemData oid = new com.suarez.entity.OrderItemData();
                oid.setOrder(orderData);
                oid.setProduct(mi.getProduct());
                oid.setQuantity(mi.getQuantity());
                items.add(oid);
            }
        }
        orderData.setItems(items);
        orderData.setStatus(order.getStatus());
        orderData = orderDataRepository.save(orderData);

        logger.info("Order created with id: " + orderData.getId());
        Order newOrder = new Order();
        newOrder.setId(orderData.getId());
        newOrder.setCreated(orderData.getCreated());
        newOrder.setCustomer(orderData.getCustomer());
        java.util.List<com.suarez.model.OrderItem> newItems = new java.util.ArrayList<>();
        if (orderData.getItems() != null) {
            for (com.suarez.entity.OrderItemData oid : orderData.getItems()) {
                com.suarez.model.OrderItem mi = new com.suarez.model.OrderItem();
                mi.setProduct(oid.getProduct());
                mi.setQuantity(oid.getQuantity());
                newItems.add(mi);
            }
        }
        newOrder.setItems(newItems);
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
            existingOrderData.setCustomer(order.getCustomer());
            // Replace items
            java.util.List<com.suarez.entity.OrderItemData> newItems = new java.util.ArrayList<>();
            if (order.getItems() != null) {
                for (com.suarez.model.OrderItem mi : order.getItems()) {
                    com.suarez.entity.OrderItemData oid = new com.suarez.entity.OrderItemData();
                    oid.setOrder(existingOrderData);
                    oid.setProduct(mi.getProduct());
                    oid.setQuantity(mi.getQuantity());
                    newItems.add(oid);
                }
            }
            existingOrderData.getItems().clear();
            existingOrderData.getItems().addAll(newItems);
            existingOrderData.setStatus(order.getStatus());
            existingOrderData = orderDataRepository.save(existingOrderData);

            updatedOrder = new Order();
            updatedOrder.setId(existingOrderData.getId());
            updatedOrder.setCreated(existingOrderData.getCreated());
            updatedOrder.setCustomer(existingOrderData.getCustomer());
            java.util.List<com.suarez.model.OrderItem> updItems = new java.util.ArrayList<>();
            if (existingOrderData.getItems() != null) {
                for (com.suarez.entity.OrderItemData oid : existingOrderData.getItems()) {
                    com.suarez.model.OrderItem mi = new com.suarez.model.OrderItem();
                    mi.setProduct(oid.getProduct());
                    mi.setQuantity(oid.getQuantity());
                    updItems.add(mi);
                }
            }
            updatedOrder.setItems(updItems);
            updatedOrder.setStatus(existingOrderData.getStatus());
        } else {
            logger.warn("Order with id: " + id + " not found");
        }

        return updatedOrder;
    }

    @Override
    public void deleteOrder(int id) {
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
