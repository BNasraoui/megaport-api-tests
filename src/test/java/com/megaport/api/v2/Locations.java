package com.megaport.api.v2;

import com.megaport.model.locations.Location;
import com.megaport.model.locations.LocationResponse;
import com.megaport.model.locations.LocationStatus;
import com.megaport.model.locations.Vendor;
import com.megaport.model.locations.MVE;
import com.megaport.util.Config;
import com.megaport.util.Reporting;

import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.*;
import java.util.List;
import java.util.ArrayList;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Locations API")
public class Locations {
    
    private static final String BASE_URL = Config.getProperty("api.base.url");
    private static final String locationsEndpoint = BASE_URL + "/locations";

    @Test
    @Story("Get All Locations")
    @Description("Retrieves all public locations without any filters")
    @Severity(SeverityLevel.BLOCKER)
    public void GetAllPublicLocations() {
        LocationResponse response = given()
            .filter(new AllureRestAssured())
            .get(locationsEndpoint)
            .then()
            .statusCode(200)
            .extract()
            .as(LocationResponse.class);
        
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("List all public locations");
        assertThat(response.getTerms()).isEqualTo("This data is subject to the Acceptable Use Policy https://www.megaport.com/legal/acceptable-use-policy");
        
        // Check data is a non-empty list of Location objects
        assertThat(response.getData())
            .isNotNull()
            .isNotEmpty()
            .allSatisfy(location -> {
                assertThat(location).isInstanceOf(Location.class);
                assertThat(location.getId()).isNotNull();
                assertThat(location.getName()).isNotNull();
            });
    }

    @Test(dataProviderClass = Location.class, dataProvider = "combinedProvider")
    @Description("Filter locations by combination of vendor, status, and metro")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Combined Filtering")
    public void GetLocationsByCombinationFilter(String vendor, String status, String metro) {
        LocationResponse response = given()
            .filter(new AllureRestAssured())
            .queryParam("mveVendor", vendor)
            .queryParam("locationStatuses", status)
            .queryParam("metro", metro)
            .get(locationsEndpoint)
            .then()
            .statusCode(200)
            .extract()
            .as(LocationResponse.class);
    
        assertThat(response).isNotNull();
        
        Allure.parameter("Vendor", vendor);
        Allure.parameter("Status", status);
        Allure.parameter("Metro", metro);
        
        validateLocationCombination(response.getData(), vendor, status, metro);
    }

    @Test(dataProviderClass = LocationStatus.class, dataProvider = "statusProvider")
    @Description("Retrieve locations filtered by location status")
    @Severity(SeverityLevel.NORMAL)
    @Story("Status Filtering")
    public void GetLocationsByStatus(String status) {
        LocationResponse response = given()
            .filter(new AllureRestAssured())
            .queryParam("locationStatuses", status)
            .get(locationsEndpoint)
            .then()
            .statusCode(200)
            .extract()
            .as(LocationResponse.class);
        
        assertThat(response).isNotNull();
        
        // Add status to the allure report as a parameter
        Allure.parameter("Status", status);
        
        validateLocationStatus(response.getData(), status);
    }

    @Test(dataProviderClass = Location.class, dataProvider = "metroProvider")
    @Description("Filter Location by metro")
    @Severity(SeverityLevel.NORMAL)
    @Story("Metro Filtering")
    public void GetLocationsByMetro(String metro) {
        LocationResponse response = given()
            .filter(new AllureRestAssured())
            .queryParam("metro", metro)
            .get(locationsEndpoint)
            .then()
            .statusCode(200)
            .extract()
            .as(LocationResponse.class);
        
        assertThat(response).isNotNull();
        
        validateLocationMetro(response.getData(), metro);
    }

    @Test(dataProviderClass = Vendor.class, dataProvider = "vendorProvider")
    @Description("Retrieves locations filtered by Vendor")
    @Severity(SeverityLevel.NORMAL)
    @Story("Vendor Filtering")
    public void GetLocationsByVendor(String vendor) {
        LocationResponse response = given()
            .filter(new AllureRestAssured())
            .queryParam("mveVendor", vendor)
            .get(locationsEndpoint)
            .then()
            .statusCode(200)
            .extract()
            .as(LocationResponse.class);
    
        assertThat(response).isNotNull();
        
        // Add vendor name to the allure report as a parameter
        Allure.parameter("Vendor", vendor);
        
        validateLocationVendor(response.getData(), vendor);
    }

    // I wasn't able to figure out what this actually does.
    // This could potentially be a bug because it doesn't seem to be filtering anything.
    // @Test()
    // @Description("Filter Location by Whether the Market is Enabled or Disabled")
    // @Severity(SeverityLevel.NORMAL)
    // @Story("Market Filtering")
    // public void GetLocationsByMarket() {
    //     LocationResponse response = given()
    //         .filter(new AllureRestAssured())
    //         .queryParam("marketEnabled", true)
    //         .get(locationsEndpoint)
    //         .then()
    //         .statusCode(200)
    //         .extract()
    //         .as(LocationResponse.class);
        
    //     assertThat(response).isNotNull();
        
    //     perform some assertion on the mystery parameters here...
    // }

    @Step("Validate all locations have status: {status}")
    private void validateLocationStatus(List<Location> locations, String status) {
        List<Location> invalidStatuses = new ArrayList<>();
        locations.forEach(location -> {
            if (!location.getStatus().equals(status)) {
                invalidStatuses.add(location);
            }
        });

        if (!invalidStatuses.isEmpty()) {
            String errorMsg = String.format(
                "Found %d locations with invalid status '%s'. See the Allure attachment for details.", 
                invalidStatuses.size(), status
            );
            Reporting.ReportFoundErrors("Locations with invalid status: " + status, errorMsg, invalidStatuses);
        }
    }

    @Step("Validate all locations have vendor: {vendor}")
    private void validateLocationVendor(List<Location> locations, String vendor) {
        List<Location> invalidVendors = new ArrayList<>();
        
        for (Location location : locations) {
            List<MVE> mveList = location.getProducts().getMve();
            for (MVE mve : mveList) {
                if (mve.getVendor() == null || !mve.getVendor().equals(vendor)) {
                    invalidVendors.add(location);
                    break;
                }
            }
        }

        if (!invalidVendors.isEmpty()) {
            String errorMsg = String.format(
                "Found %d locations without vendor '%s'. See the Allure attachment for details.", 
                invalidVendors.size(), vendor
            );
            Reporting.ReportFoundErrors("Locations without vendor: " + vendor, errorMsg, invalidVendors);
        }
    }

    @Step("Validate all locations have metro: {metro}")
    private void validateLocationMetro(List<Location> locations, String metro) {
        List<Location> invalidMetros = new ArrayList<>();
        locations.forEach(location -> {
            if (!location.getMetro().equals(metro)) {
                invalidMetros.add(location);
            }
        });

        if (!invalidMetros.isEmpty()) {
            String errorMsg = String.format(
                "Found %d locations without metro '%s'. See the Allure attachment for details.", 
                invalidMetros.size(), metro
            );
            Reporting.ReportFoundErrors("Locations without metro: " + metro, errorMsg, invalidMetros);
        }
    }
    
    @Step("Validate all locations match vendor: {vendor}, status: {status}, and metro: {metro}")
    private void validateLocationCombination(List<Location> locations, String vendor, String status, String metro) {
        // It's possible no locations match all criteria, which is valid
        if (locations.isEmpty()) {
            Allure.addAttachment(
                "No locations found", 
                "No locations found matching all criteria: vendor=" + vendor + ", status=" + status + ", metro=" + metro
            );
            return;
        }
        
        validateLocationStatus(locations, status);
        validateLocationVendor(locations, vendor);
        validateLocationMetro(locations, metro);
    }
}
