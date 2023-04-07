package com.lukaroncevic.spring6reactive.controllers;

import com.lukaroncevic.spring6reactive.model.CustomerDTO;
import com.lukaroncevic.spring6reactive.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v2/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    private final CustomerService customerService;

    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable("customerId") Integer customerId){
        return customerService.deleteCustomer(customerId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingCustomer(@PathVariable("customerId") Integer customerId,
                                                     @RequestBody CustomerDTO customerDTO){

        return customerService.patchedCustomer(customerId, customerDTO)
                .map(savedDto -> ResponseEntity.ok().build());
        }

    @PutMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> updateExistingCustomer(@PathVariable("customerId") Integer customerId,
                                                      @RequestBody CustomerDTO customerDTO){

        return customerService.updateCustomer(customerId, customerDTO)
                .map(savedDto -> ResponseEntity.noContent().build());
    }

    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO){
        return customerService.saveNewCustomer(customerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/" + CUSTOMER_PATH
                                        + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> findCustomerById(@PathVariable("customerId") Integer customerId){
        return customerService.findById(customerId);
    }

    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> listCustomers(){
        return customerService.listCustomers();
    }
}
