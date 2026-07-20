package com.javabank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javabank.entity.Customer;
import com.javabank.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.javabank.exception.CustomerNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private static final Logger logger =
            LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Customer createCustomer(Customer customer) {
    	logger.info("Creating new customer.");
    	
    	Customer savedCustomer = customerRepository.save(customer);
    	logger.info("Customer created successfully with ID: {}",
    	        savedCustomer.getCustomerId());
    	
        return savedCustomer;
    }

    @Override
    public List<Customer> getAllCustomers() {
    	 logger.info("Fetching all customers.");
    	 
    	 List<Customer> customers = customerRepository.findAll();
    	 
    	 logger.info("Total customers found: {}", customers.size());
    	 
        return customers;
    }

    @Override
    public Customer getCustomerById(Long customerId) {
    	
    	logger.info("Fetching customer with ID: {}", customerId);
    	
        return customerRepository.findById(customerId)
        		.orElseThrow(() -> {
        			logger.error("Customer not found with ID: {}", customerId);
        			return new CustomerNotFoundException("Customer Not Found");
        });
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer customer) {

        logger.info("Updating customer with ID: {}", customerId);

        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", customerId);
                    return new CustomerNotFoundException("Customer Not Found");
                });

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setMobile(customer.getMobile());
        existingCustomer.setAddress(customer.getAddress());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        logger.info("Customer updated successfully. ID: {}", customerId);

        return updatedCustomer;
    }
    
    @Override
    public void deleteCustomer(Long customerId) {

        logger.info("Deleting customer with ID: {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", customerId);
                    return new CustomerNotFoundException("Customer Not Found");
                });

        customerRepository.delete(customer);

        logger.info("Customer deleted successfully. ID: {}", customerId);
    }
}