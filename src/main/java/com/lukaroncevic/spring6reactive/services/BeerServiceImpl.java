package com.lukaroncevic.spring6reactive.services;

import com.lukaroncevic.spring6reactive.mappers.BeerMaper;
import com.lukaroncevic.spring6reactive.model.BeerDTO;
import com.lukaroncevic.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMaper beerMaper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMaper::beerToBeerDto);
    }
}
