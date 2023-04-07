package com.lukaroncevic.spring6reactive.services;

import com.lukaroncevic.spring6reactive.mappers.CustomerMapper;
import com.lukaroncevic.spring6reactive.model.CustomerDTO;
import com.lukaroncevic.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> findById(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDto);

    }

    @Override
    public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(existingCustomer -> {
                    existingCustomer.setCustomerName(customerDTO.getCustomerName());
                    return existingCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> patchedCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(existingCustomer -> {
                    if (StringUtils.hasText(customerDTO.getCustomerName())) {
                        existingCustomer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return existingCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<Void> deleteCustomer(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
