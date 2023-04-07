package com.lukaroncevic.spring6reactive.mappers;

import com.lukaroncevic.spring6reactive.domain.Beer;
import com.lukaroncevic.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMaper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
