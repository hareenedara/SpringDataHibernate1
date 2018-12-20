package com.refresh.spring.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refresh.spring.repo.dto.AddressDTO;
import com.refresh.spring.repo.dto.CustomerDTO;
import com.refresh.spring.repo.dto.OrderDTO;
import com.refresh.spring.repo.dto.PhoneDTO;
import lombok.Data;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Customer {

    private String firstName;
    private String lastName;
    private Address address;
    private Set<Phone> contacts=new HashSet<>();
    private Set<Order> orders= new HashSet<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Customer builder(CustomerDTO customerDTO) throws IOException {

        this.setFirstName(customerDTO.getFirstName());
        this.setLastName(customerDTO.getLastName());
        this.setAddress(convert(customerDTO.getAddress(),Address.class));
        this.setContacts(customerDTO.getContacts().stream().map(t -> convert(t,Phone.class)).collect(Collectors.toSet()));
        this.setOrders(customerDTO.getOrders().stream().map(t -> convert(t,Order.class)).collect(Collectors.toSet()));
        return this;
    }
    public static <T,S> T convert(S obj, Class<T> type ) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(obj), type);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
