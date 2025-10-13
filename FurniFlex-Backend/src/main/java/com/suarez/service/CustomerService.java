package com.suarez.service;

import com.suarez.model.Customer;

public interface CustomerService {
    Customer getCustomerById(int id);
    Customer[] getAllCustomers();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(int id);
}
