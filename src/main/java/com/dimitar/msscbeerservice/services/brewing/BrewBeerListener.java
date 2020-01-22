package com.dimitar.msscbeerservice.services.brewing;

import com.dimitar.msscbeerservice.config.JmsConfig;
import com.dimitar.msscbeerservice.domain.Beer;
import com.dimitar.msscbeerservice.events.BrewBeerEvent;
import com.dimitar.msscbeerservice.events.InventoryEvent;
import com.dimitar.msscbeerservice.repositories.BeerRepository;
import com.dimitar.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(final BrewBeerEvent brewBeerEvent) {
        final BeerDto beerDto = brewBeerEvent.getBeerDto();

        final Beer beer = beerRepository.getOne(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew()); //hack with imaginary brewing process

        final InventoryEvent inventoryEvent = new InventoryEvent(beerDto);
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, inventoryEvent);
    }
}