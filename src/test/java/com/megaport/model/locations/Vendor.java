package com.megaport.model.locations;

import java.util.Arrays;
import org.testng.annotations.DataProvider;

public enum Vendor {
    ARUBA("Aruba"),
    CISCO("Cisco"),
    FORTINET("Fortinet"),
    VERSA("Versa"),
    VMWARE("VMware"),
    PALO_ALTO("Palo Alto");
    
    private final String value;
    
    Vendor(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @DataProvider(name = "vendorProvider")
    public static Object[][] vendorProvider() {
        return Arrays.stream(Vendor.values())
                .map(vendor -> new Object[] { vendor.getValue() })
                .toArray(Object[][]::new);
    }
} 