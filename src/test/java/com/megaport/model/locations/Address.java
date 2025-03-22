package com.megaport.model.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Address {
    private String street;
    private String city;
    private String suburb;
    private String country;
    private String postcode;
}
