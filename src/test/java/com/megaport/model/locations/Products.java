package com.megaport.model.locations;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
@Setter
public class Products {
    private boolean mcr;
    private int mcrVersion;
    private List<Integer> megaport;
    private List<MVE> mve;
    
    @Override
    public String toString() {
        return "Products{" +
               "mve=" + mve +
               '}';
    }
}
