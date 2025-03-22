package com.megaport.model.locations;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LocationResponse {
    private String message;
    private String terms;
    private List<Location> data;
}

