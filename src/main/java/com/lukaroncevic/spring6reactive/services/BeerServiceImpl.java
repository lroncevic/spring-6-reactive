package com.lukaroncevic.spring6reactive.services;

import com.lukaroncevic.spring6reactive.mappers.BeerMaper;
import com.lukaroncevic.spring6reactive.model.BeerDTO;
import com.lukaroncevic.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @Override
    public Mono<BeerDTO> getBeerById(Integer beerId) {
        return beerRepository.findById(beerId)
                .map(beerMaper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMaper.beerDtoToBeer(beerDTO))
                .map(beerMaper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(foundBeer -> {
                    foundBeer.setBeerName(beerDTO.getBeerName());
                    foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    foundBeer.setPrice(beerDTO.getPrice());
                    foundBeer.setUpc(beerDTO.getUpc());
                    foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMaper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(existingBeer -> {

                    if (StringUtils.hasText(beerDTO.getBeerName())) {
                        existingBeer.setBeerName(beerDTO.getBeerName());
                    }

                    if (StringUtils.hasText(beerDTO.getBeerStyle())) {
                        existingBeer.setBeerStyle(beerDTO.getBeerStyle());
                    }

                    if (StringUtils.hasText(beerDTO.getUpc())) {
                        existingBeer.setUpc(beerDTO.getUpc());
                    }
                    if (beerDTO.getQuantityOnHand() != null) {
                        existingBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    }
                    if (beerDTO.getPrice() != null) {
                        existingBeer.setPrice(beerDTO.getPrice());
                    }
                    return existingBeer;
                }).flatMap(beerRepository::save)
                .map(beerMaper::beerToBeerDto);
    }
}
