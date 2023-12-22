package com.neosoft.springcache.controller;

import com.neosoft.springcache.repository.CustomerRepository;
import com.neosoft.springcache.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.neosoft.springcache.model.Customer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        log.info("creating customer: {}", customer.getName());
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity
                .created(URI.create("localhost:8080/api/v1/customer/" + savedCustomer.getId()))
                .body(savedCustomer);
    }

    @GetMapping("/{customerId}")
//    @Cacheable(value = "customers", key = "#customerId", unless = "#result.body.orders > 100")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId) {
        log.info("finding customer with id {}", customerId);
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable Integer customerId,
                                                       @RequestBody Customer customer){
        log.info("updating customer info of {}", customerId);
        return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        log.info("get all customers");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

}
