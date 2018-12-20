package com.refresh.spring.service;

import com.refresh.spring.model.Customer;
import com.refresh.spring.repo.dto.CustomerDTO;
import com.refresh.spring.repo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo repo;

    public Set<Customer> customerByLastName(String lastName){
        return repo.getByLastName(lastName,Customer.class);
    }

    public Customer newCustomer(Customer customer) throws IOException {

        CustomerDTO dto = new CustomerDTO().builder(customer);
        CustomerDTO resultDto = repo.save(dto);
        return new Customer().builder(dto);
    }
}
