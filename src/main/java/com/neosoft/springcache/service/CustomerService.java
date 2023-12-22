package com.neosoft.springcache.service;

import com.neosoft.springcache.model.Customer;
import com.neosoft.springcache.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Cacheable(value = "customers", key = "#customerId")
    public Customer findCustomerById(Integer customerId){
        return customerRepository.findById(customerId).orElseThrow();
    }

    @CachePut(value = "customers", key = "#customerId")
    public Customer updateCustomer(Integer customerId, Customer customer) {
        customerRepository.updateCustomer(customer.getName(), customer.getOrders(), customerId);
        return customerRepository.findById(customerId).orElseThrow();
    }


    @CacheEvict(value = "customers", allEntries = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
