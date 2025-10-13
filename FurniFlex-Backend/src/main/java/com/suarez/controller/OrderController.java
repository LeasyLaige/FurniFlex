package com.suarez.controller;

import com.suarez.model.Order;
import com.suarez.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("api/order")
    public ResponseEntity<?> listOrders() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Order[] orders = orderService.getAllOrders();
            response = ResponseEntity.ok().headers(headers).body(orders);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("api/order/{id}")
    public ResponseEntity<?> get(@PathVariable final int id) {
        logger.info("Input order id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Order order = orderService.getOrderById(id);
            response = ResponseEntity.ok(order);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PostMapping("api/order")
    public ResponseEntity<?> add(@RequestBody Order order) {
        logger.info("Input >> " + order.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Order newOrder = orderService.createOrder(order);
            logger.info("created order >> " + newOrder.toString());
            response = ResponseEntity.ok().headers(headers).body(newOrder);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PutMapping("api/order/{id}")
    public ResponseEntity<?> update(@PathVariable final int id, @RequestBody Order order) {
        logger.info("Input id >> {} : {}", id, order);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Order updatedOrder = orderService.updateOrder(order);
            logger.info("updated order >> " + updatedOrder.toString());
            response = ResponseEntity.ok().headers(headers).body(updatedOrder);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("api/order/{id}")
    public ResponseEntity<?> delete(@PathVariable final int id) {
        logger.info("Input order id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            orderService.deleteOrder(id);
            response = ResponseEntity.ok().headers(headers).body("Order deleted successfully");
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }
}
