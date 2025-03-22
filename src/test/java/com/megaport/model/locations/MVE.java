package com.megaport.model.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MVE {
    private String product;
    private String size;
    private String vendor;
    private int count;
    private boolean enabled;
}
