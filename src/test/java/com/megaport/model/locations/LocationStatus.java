package com.megaport.model.locations;

import java.util.Arrays;
import org.testng.annotations.DataProvider;

public enum LocationStatus {
    EXTENDED("Extended"),
    DEPLOYMENT("Deployment"),
    ACTIVE("Active"),
    NEW("New"),
    RESTRICTED("Restricted"),
    EXPIRED("Expired");
    
    private final String value;
    
    LocationStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @DataProvider(name = "statusProvider")
    public static Object[][] statusProvider() {
        return Arrays.stream(LocationStatus.values())
                .map(status -> new Object[] { status.getValue() })
                .toArray(Object[][]::new);
    }
} 