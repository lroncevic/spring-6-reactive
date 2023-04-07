package com.lukaroncevic.spring6reactive.services;

import com.lukaroncevic.spring6reactive.model.CustomerDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.FileChannel;

public interface CustomerService {

    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> findById(Integer customerId);

    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchedCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<Void> deleteCustomer(Integer customerId);
}
