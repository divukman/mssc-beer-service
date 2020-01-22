package com.dimitar.msscbeerservice.events;

import com.dimitar.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -5309315589632565264L;

    private BeerDto beerDto;
}
