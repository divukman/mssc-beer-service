package guru.sfg.common.events;

import guru.sfg.common.BeerDto;
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
