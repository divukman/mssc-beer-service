package com.dimitar.msscbeerservice.web.controller;

import com.dimitar.msscbeerservice.web.model.BeerDto;
import com.dimitar.msscbeerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 443)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(
                get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                        .param("iscold", "yes")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get.")),
                        requestParameters(
                                parameterWithName("iscold").description("Is beer cold query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of the beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date created"),
                                fieldWithPath("lastModifiedDate").description("Date Updated"),
                                fieldWithPath("beerName").description("Beer name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Beer price"),
                                fieldWithPath("quantityOnHand").description("Quantiti On Hand")
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {
        final BeerDto beerDto = BeerDto.builder()
                .price(BigDecimal.valueOf(10))
                .beerName("Karlovacko")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(10L)
                .build();
        final String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(
                post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson)
        )
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("Beer UPC").attributes(),
                                fields.withPath("price").description("Beer Price"),
                                fields.withPath("quantityOnHand").ignored()
                        )
                ))
        ;
    }

    @Test
    void updateBeerById() throws Exception {
        final BeerDto beerDto = BeerDto.builder()
                .price(BigDecimal.valueOf(10))
                .beerName("Karlovacko")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(10L)
                .build();
        final String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(
                put("/api/v1/beer/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson)
        )
                .andExpect(status().isNoContent());

    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(
                    key("constraints").value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". "))
            );
        }
    }
}