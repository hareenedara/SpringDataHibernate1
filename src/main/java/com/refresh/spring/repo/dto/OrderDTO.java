package com.refresh.spring.repo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.refresh.spring.model.Customer;
import com.refresh.spring.model.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name="CUSTOMER_ORDER",uniqueConstraints = {@UniqueConstraint(columnNames = {"productName"})})
@JsonIgnoreProperties(value = {"customer"})
public class OrderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String productName;
    private int quantity;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CustomerDTO customer;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "order")
    @JsonIgnore
    private ProductDTO product;


}
