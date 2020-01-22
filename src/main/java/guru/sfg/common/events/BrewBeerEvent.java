package guru.sfg.common.events;

import guru.sfg.common.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
