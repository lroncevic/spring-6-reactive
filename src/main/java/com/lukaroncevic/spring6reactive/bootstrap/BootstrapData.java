package com.lukaroncevic.spring6reactive.bootstrap;

import com.lukaroncevic.spring6reactive.domain.Beer;
import com.lukaroncevic.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.plaf.IconUIResource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();

        beerRepository.count().subscribe(count -> {
            System.out.println("Count is: " + count);
        });
    }

        private void loadBeerData() {
            beerRepository.count().subscribe(count -> {
                if (count == 0) {
                    Beer beer1 = Beer.builder()
                            .beerName("Galaxy Cat")
                            .beerStyle("Pale Ale")
                            .upc("123456")
                            .price(new BigDecimal("12.99"))
                            .quantityOnHand(122)
                            .createdDate(LocalDateTime.now())
                            .lastModifiedDate(LocalDateTime.now())
                            .build();

                    Beer beer2 = Beer.builder()
                            .beerName("Crank")
                            .beerStyle("Pale Ale")
                            .upc("12345622")
                            .price(new BigDecimal("11.99"))
                            .quantityOnHand(392)
                            .createdDate(LocalDateTime.now())
                            .lastModifiedDate(LocalDateTime.now())
                            .build();

                    Beer beer3 = Beer.builder()
                            .beerName("Sunshine City")
                            .beerStyle("IPA")
                            .upc("123499")
                            .price(new BigDecimal("13.99"))
                            .quantityOnHand(392)
                            .createdDate(LocalDateTime.now())
                            .lastModifiedDate(LocalDateTime.now())
                            .build();

                    beerRepository.save(beer1).subscribe();
                    beerRepository.save(beer2).subscribe();
                    beerRepository.save(beer3).subscribe();
                }
            });
        }
}


