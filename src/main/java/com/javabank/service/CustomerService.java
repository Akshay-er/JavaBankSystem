package com.javabank.service;

import java.util.List;

import com.javabank.entity.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long customerId);

    Customer updateCustomer(Long customerId, Customer customer);

    void deleteCustomer(Long customerId);
}