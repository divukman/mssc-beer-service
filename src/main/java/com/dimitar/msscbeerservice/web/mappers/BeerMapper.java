package com.dimitar.msscbeerservice.web.mappers;

import com.dimitar.msscbeerservice.domain.Beer;
import com.dimitar.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = { DateMapper.class })
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerTodoToBeer(BeerDto beerDto);
}
