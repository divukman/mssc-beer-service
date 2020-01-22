package com.dimitar.msscbeerservice.events;

import com.dimitar.msscbeerservice.web.model.BeerDto;

public class InventoryEvent extends BeerEvent {

    public InventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
