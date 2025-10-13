package com.suarez.serviceimpl;

import com.suarez.entity.CustomerData;
import com.suarez.model.Customer;
import com.suarez.repository.CustomerDataRepository;
import com.suarez.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerDataRepository customerDataRepository;

    @Override
    public Customer getCustomerById(int id) {
        logger.info("Getting customer with id: " + id);
        Customer customer = null;
        Optional<CustomerData> customerData = customerDataRepository.findById(id);
        if (customerData.isPresent()) {
            customer = new Customer();
            customer.setId(customerData.get().getId());
            customer.setName(customerData.get().getName());
            customer.setEmail(customerData.get().getEmail());
            customer.setPhone(customerData.get().getPhone());
            customer.setCreated(customerData.get().getCreated());
            customer.setLastUpdated(customerData.get().getLastUpdated());
        } else {
            logger.warn("Customer with id: " + id + " not found");
        }

        return customer;
    }

    @Override
    public Customer[] getAllCustomers() {
        logger.info("Getting all customers");
        List<CustomerData> customerDataList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();

        customerDataRepository.findAll().forEach(customerDataList::add);
        Iterator<CustomerData> iterator = customerDataList.iterator();

        while (iterator.hasNext()) {
            CustomerData customerData = iterator.next();
            Customer customer = new Customer();
            customer.setId(customerData.getId());
            customer.setName(customerData.getName());
            customer.setEmail(customerData.getEmail());
            customer.setPhone(customerData.getPhone());
            customer.setCreated(customerData.getCreated());
            customer.setLastUpdated(customerData.getLastUpdated());
            customerList.add(customer);
        }

        Customer[] customerArray = new Customer[customerList.size()];
        for (int i = 0; i < customerList.size(); i++) {
            customerArray[i] = customerList.get(i);
        }

        logger.info("Found " + customerList.size() + " customers");
        return customerArray;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Creating customer with name: " + customer.getName());
        CustomerData customerData = new CustomerData();
        customerData.setName(customer.getName());
        customerData.setEmail(customer.getEmail());
        customerData.setPhone(customer.getPhone());
        customerData = customerDataRepository.save(customerData);

        logger.info("Customer created with id: " + customerData.getId());
        Customer newCustomer = new Customer();
        newCustomer.setId(customerData.getId());
        newCustomer.setName(customerData.getName());
        newCustomer.setEmail(customerData.getEmail());
        newCustomer.setPhone(customerData.getPhone());

        return newCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer updatedCustomer = null;
        int id = customer.getId();
        Optional<CustomerData> customerData = customerDataRepository.findById(id);

        if (customerData.isPresent()) {
            CustomerData originalCustomerData = new CustomerData();
            originalCustomerData.setId(customer.getId());
            originalCustomerData.setName(customer.getName());
            originalCustomerData.setEmail(customer.getEmail());
            originalCustomerData.setPhone(customer.getPhone());

            CustomerData customerDataRepo = customerDataRepository.save(originalCustomerData);

            updatedCustomer = new Customer();
            updatedCustomer.setId(customerDataRepo.getId());
            updatedCustomer.setName(customerDataRepo.getName());
            updatedCustomer.setEmail(customerDataRepo.getEmail());
            updatedCustomer.setPhone(customerDataRepo.getPhone());
        } else {
            logger.warn("Customer with id: " + id + " not found");
        }

        return updatedCustomer;
    }

    @Override
    public void deleteCustomer(int id) {
        Customer customer = null;
        logger.info("Deleting customer with id: " + id);
        Optional<CustomerData> customerData = customerDataRepository.findById(id);

        if (customerData.isPresent()) {
            CustomerData customerDataToDelete = customerData.get();
            customerDataRepository.delete(customerDataToDelete);
            logger.info("Successfully deleted customer with id: " + id);
        } else {
            logger.warn("Unable to delete customer with id: " + id + " not found");
        }
    }
}
