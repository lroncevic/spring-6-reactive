package com.lukaroncevic.spring6reactive.services;

import com.lukaroncevic.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;

public interface BeerService {

    Flux<BeerDTO> listBeers();
}
