package com.refresh.spring.repo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.refresh.spring.model.Customer;
import com.refresh.spring.model.Phone;
import lombok.Data;
import lombok.Value;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="CUSTOMER")
public class CustomerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY,mappedBy="customer")
    @JsonIgnore
    private AddressDTO address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PhoneDTO> contacts= new HashSet<>();
    @OneToMany(mappedBy="customer",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderDTO> orders= new HashSet<>();

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerDTO builder(Customer customer) throws IOException {

        AddressDTO addressDTO = convert(customer.getAddress(),AddressDTO.class);
        Set<PhoneDTO> contacts = customer.getContacts().stream().map(t -> convert(t,PhoneDTO.class)).collect(Collectors.toSet());
        contacts.stream().forEach(t -> {this.addPhone(t);});
        customer.getOrders().stream().map(t -> convert(t,OrderDTO.class)).forEach(t -> {if(t != null) this.addOrder(t); });
        this.setFirstName(customer.getFirstName());
        this.setLastName(customer.getLastName());
        this.setAddress(addressDTO);
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

    public void addAddress(AddressDTO address){
        this.address = address;
        address.setCustomer(this);
    }
    public void removeAddress(){
        this.address = null;
        address.setCustomer(null);
    }

    public void addPhone(PhoneDTO nPhone){
        contacts.add(nPhone);
        nPhone.setCustomer(this);
    }

    public void removePhone(PhoneDTO rPhone){
        this.getContacts().remove(rPhone);
        rPhone.setCustomer(null);
    }

    public void addOrder(OrderDTO nOrder){
        orders.add(nOrder);
        nOrder.setCustomer(this);
    }

    public void removeOrder(OrderDTO rOrder) {
        orders.remove(rOrder);
        rOrder.setCustomer(this);
    }


}
