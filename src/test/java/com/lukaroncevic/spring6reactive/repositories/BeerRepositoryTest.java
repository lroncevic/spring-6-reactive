package com.lukaroncevic.spring6reactive.repositories;

import com.lukaroncevic.spring6reactive.config.DataBaseConfig;
import com.lukaroncevic.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import(DataBaseConfig.class)
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveNewBeer() {
        beerRepository.save(getTestBeer())
                .subscribe(beer -> {
                    System.out.println(beer.toString());
                });
    }

    Beer getTestBeer(){
       return Beer.builder()
               .beerName("Osječko")
               .beerStyle("IPA")
               .price(BigDecimal.TEN)
               .quantityOnHand(12)
               .upc("123456")
               .build();
    }
}