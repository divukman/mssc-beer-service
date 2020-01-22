package com.dimitar.msscbeerservice.services;

import com.dimitar.msscbeerservice.config.JmsConfig;
import com.dimitar.msscbeerservice.domain.Beer;
import com.dimitar.msscbeerservice.events.BrewBeerEvent;
import com.dimitar.msscbeerservice.repositories.BeerRepository;
import com.dimitar.msscbeerservice.services.inventory.BeerInventoryService;
import com.dimitar.msscbeerservice.web.mappers.BeerMapper;
import com.dimitar.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) //every 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());

            log.debug("Min Onhand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQOH);
            if (beer.getMinOnHand() >= invQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent( beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
