package com.dimitar.msscbeerservice.events;

import com.dimitar.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
