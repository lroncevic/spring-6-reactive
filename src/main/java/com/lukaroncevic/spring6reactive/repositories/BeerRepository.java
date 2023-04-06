package com.lukaroncevic.spring6reactive.repositories;

import com.lukaroncevic.spring6reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
