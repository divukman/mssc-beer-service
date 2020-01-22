package com.dimitar.msscbeerservice.web.mappers;

import com.dimitar.msscbeerservice.domain.Beer;
import guru.sfg.common.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = { DateMapper.class })
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
