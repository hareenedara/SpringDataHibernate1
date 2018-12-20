package com.refresh.spring.controller;

import com.refresh.spring.model.Customer;
import com.refresh.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping(value="/customer",produces = "application/json")
public class CustomerController {


    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/lastname/{lname}", consumes = "application/json")
    public Set<Customer> customersByLastName(@PathVariable("lname") String lName) {
        return customerService.customerByLastName(lName);
    }
    @PostMapping(consumes = "application/json")
    public Customer newCustomer(@RequestBody Customer customer) throws IOException {
        return customerService.newCustomer(customer);
    }
}
