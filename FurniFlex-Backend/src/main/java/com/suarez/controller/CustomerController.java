package com.suarez.controller;

import com.suarez.model.Customer;
import com.suarez.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("api/customer")
    public ResponseEntity<?> listCustomers() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Customer[] customers = customerService.getAllCustomers();
            response = ResponseEntity.ok().headers(headers).body(customers);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("api/customer/{id}")
    public ResponseEntity<?> get(@PathVariable final int id) {
        logger.info("Input customer id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Customer customer = customerService.getCustomerById(id);
            response = ResponseEntity.ok(customer);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PostMapping("api/customer")
    public ResponseEntity<?> add(@RequestBody Customer customer) {
        logger.info("Input >> " + customer.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Customer newCustomer = customerService.createCustomer(customer);
            logger.info("created customer >> " + newCustomer.toString());
            response = ResponseEntity.ok().headers(headers).body(newCustomer);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @PutMapping("api/customer/{id}")
    public ResponseEntity<?> update(@PathVariable final int id, @RequestBody Customer customer) {
        logger.info("Input id >> " + id + " : " + customer.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            customer.setId(id);
            Customer updatedCustomer = customerService.updateCustomer(customer);
            logger.info("updated customer >> " + updatedCustomer.toString());
            response = ResponseEntity.ok().headers(headers).body(updatedCustomer);
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("api/customer/{id}")
    public ResponseEntity<?> delete(@PathVariable final int id) {
        logger.info("Input customer id >> " + id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            customerService.deleteCustomer(id);
            response = ResponseEntity.ok().headers(headers).body("Customer with id " + id + " deleted successfully.");
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        return response;
    }
}
