package guru.sfg.common.events;

import guru.sfg.common.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InventoryEvent extends BeerEvent {

    public InventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
