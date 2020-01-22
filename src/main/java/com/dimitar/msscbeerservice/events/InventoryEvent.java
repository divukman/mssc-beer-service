package com.dimitar.msscbeerservice.events;

import com.dimitar.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InventoryEvent extends BeerEvent {

    public InventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
