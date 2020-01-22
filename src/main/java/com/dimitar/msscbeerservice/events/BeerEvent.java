package com.dimitar.msscbeerservice.events;

import com.dimitar.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -5309315589632565264L;

    private final BeerDto beerDto;
}
