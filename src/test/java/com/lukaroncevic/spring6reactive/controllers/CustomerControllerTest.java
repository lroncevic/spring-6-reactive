package com.lukaroncevic.spring6reactive.controllers;

import com.lukaroncevic.spring6reactive.domain.Customer;
import com.lukaroncevic.spring6reactive.model.BeerDTO;
import com.lukaroncevic.spring6reactive.model.CustomerDTO;
import com.lukaroncevic.spring6reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(999)
    void deleteCustomer() {
        webTestClient.delete().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(3)
    void updateCustomer() {
        webTestClient.put().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void createCustomer() {
        webTestClient.post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getTestCustomer()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/3");
    }

    @Test
    @Order(1)
    void getById() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(2)
    void listCustomers() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

    Customer getTestCustomer(){
        return Customer.builder()
                .customerName("Test Customer")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }
}