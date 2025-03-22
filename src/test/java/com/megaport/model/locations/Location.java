package com.megaport.model.locations;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String id;
    private String name;
    private String campus;
    private String metro;
    private String country;
    private String siteCode;
    private String networkRegion;
    private Address address;
    private DC dc;
    private String market;
    @JsonProperty("vRouterAvailable")
    private boolean vRouterAvailable;
    private long liveDate;
    private String status;
    private double longitude;
    private double latitude;
    private Products products;

    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", campus='" + campus + '\'' +
                ", metro='" + metro + '\'' +
                ", country='" + country + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", networkRegion='" + networkRegion + '\'' +
                ", address=" + address +
                ", dc=" + dc +
                ", market='" + market + '\'' +
                ", vRouterAvailable=" + vRouterAvailable +
                ", liveDate=" + liveDate +
                ", status='" + status + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", products=" + products +
                '}';
    }

    private static final String[] popularMetros = {
        "Sydney", 
        "Singapore", 
        "London", 
        "New York", 
        "Los Angeles", 
        "Tokyo", 
        "Amsterdam"
    };
    
    @DataProvider(name = "metroProvider")
    public static Object[][] metroProvider() {
        return Arrays.stream(popularMetros)
                .map(metro -> new Object[] { metro })
                .toArray(Object[][]::new);
    }
    
    @DataProvider(name = "combinedProvider")
    public static Object[][] combinedVendorStatusMetroProvider() {
        String[] selectedVendors = {"Cisco", "Fortinet", "Palo Alto"};
        String[] selectedStatuses = {"Active", "Deployment"};
        String[] selectedMetros = {"Sydney", "Singapore", "London"};
        
        return Arrays.stream(selectedVendors)
                .flatMap(vendor -> 
                    Arrays.stream(selectedStatuses)
                        .flatMap(status -> 
                            Arrays.stream(selectedMetros)
                                .map(metro -> new Object[] { vendor, status, metro })
                        )
                )
                .toArray(Object[][]::new);
    }
}