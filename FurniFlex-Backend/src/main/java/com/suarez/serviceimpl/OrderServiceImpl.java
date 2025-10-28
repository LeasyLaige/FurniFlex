package com.suarez.serviceimpl;

import com.suarez.entity.OrderData;
import com.suarez.entity.OrderItemData;
import com.suarez.model.Order;
import com.suarez.model.OrderItem;
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
            // Shipping fields
            order.setRecipientName(optional.get().getRecipientName());
            order.setAddressLine1(optional.get().getAddressLine1());
            order.setAddressLine2(optional.get().getAddressLine2());
            order.setCity(optional.get().getCity());
            order.setState(optional.get().getState());
            order.setPostalCode(optional.get().getPostalCode());
            order.setCountry(optional.get().getCountry());
            order.setPhone(optional.get().getPhone());
            order.setShippingMethod(optional.get().getShippingMethod());
            order.setShippingCost(optional.get().getShippingCost());
            // Map items
            List<OrderItem> items = new ArrayList<>();
            if (optional.get().getItems() != null) {
                for (OrderItemData oid : optional.get().getItems()) {
                    OrderItem mi = new OrderItem();
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
            order.setRecipientName(orderData.getRecipientName());
            order.setAddressLine1(orderData.getAddressLine1());
            order.setAddressLine2(orderData.getAddressLine2());
            order.setCity(orderData.getCity());
            order.setState(orderData.getState());
            order.setPostalCode(orderData.getPostalCode());
            order.setCountry(orderData.getCountry());
            order.setPhone(orderData.getPhone());
            order.setShippingMethod(orderData.getShippingMethod());
            order.setShippingCost(orderData.getShippingCost());
            List<OrderItem> items = new ArrayList<>();
            if (orderData.getItems() != null) {
                for (OrderItemData oid : orderData.getItems()) {
                    OrderItem mi = new OrderItem();
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
    // Shipping fields
    orderData.setRecipientName(order.getRecipientName());
    orderData.setAddressLine1(order.getAddressLine1());
    orderData.setAddressLine2(order.getAddressLine2());
    orderData.setCity(order.getCity());
    orderData.setState(order.getState());
    orderData.setPostalCode(order.getPostalCode());
    orderData.setCountry(order.getCountry());
    orderData.setPhone(order.getPhone());
    orderData.setShippingMethod(order.getShippingMethod());
    orderData.setShippingCost(order.getShippingCost());
        // Map items from model
        List<OrderItemData> items = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem mi : order.getItems()) {
                OrderItemData oid = new OrderItemData();
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
    newOrder.setRecipientName(orderData.getRecipientName());
    newOrder.setAddressLine1(orderData.getAddressLine1());
    newOrder.setAddressLine2(orderData.getAddressLine2());
    newOrder.setCity(orderData.getCity());
    newOrder.setState(orderData.getState());
    newOrder.setPostalCode(orderData.getPostalCode());
    newOrder.setCountry(orderData.getCountry());
    newOrder.setPhone(orderData.getPhone());
    newOrder.setShippingMethod(orderData.getShippingMethod());
    newOrder.setShippingCost(orderData.getShippingCost());
        List<OrderItem> newItems = new ArrayList<>();
        if (orderData.getItems() != null) {
            for (OrderItemData oid : orderData.getItems()) {
                OrderItem mi = new OrderItem();
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
            existingOrderData.setRecipientName(order.getRecipientName());
            existingOrderData.setAddressLine1(order.getAddressLine1());
            existingOrderData.setAddressLine2(order.getAddressLine2());
            existingOrderData.setCity(order.getCity());
            existingOrderData.setState(order.getState());
            existingOrderData.setPostalCode(order.getPostalCode());
            existingOrderData.setCountry(order.getCountry());
            existingOrderData.setPhone(order.getPhone());
            existingOrderData.setShippingMethod(order.getShippingMethod());
            existingOrderData.setShippingCost(order.getShippingCost());
            // Replace items
            List<OrderItemData> newItems = new ArrayList<>();
            if (order.getItems() != null) {
                for (OrderItem mi : order.getItems()) {
                    OrderItemData oid = new OrderItemData();
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
            updatedOrder.setRecipientName(existingOrderData.getRecipientName());
            updatedOrder.setAddressLine1(existingOrderData.getAddressLine1());
            updatedOrder.setAddressLine2(existingOrderData.getAddressLine2());
            updatedOrder.setCity(existingOrderData.getCity());
            updatedOrder.setState(existingOrderData.getState());
            updatedOrder.setPostalCode(existingOrderData.getPostalCode());
            updatedOrder.setCountry(existingOrderData.getCountry());
            updatedOrder.setPhone(existingOrderData.getPhone());
            updatedOrder.setShippingMethod(existingOrderData.getShippingMethod());
            updatedOrder.setShippingCost(existingOrderData.getShippingCost());
            List<OrderItem> updItems = new ArrayList<>();
            if (existingOrderData.getItems() != null) {
                for (OrderItemData oid : existingOrderData.getItems()) {
                    OrderItem mi = new OrderItem();
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
