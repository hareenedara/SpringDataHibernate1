package com.refresh.spring.repo.repository;

import com.refresh.spring.repo.dto.CustomerDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CustomerRepo extends CrudRepository<CustomerDTO,Long> {

    <T> Set<T> getByLastName(String lastName, Class<T> tClass);
}
