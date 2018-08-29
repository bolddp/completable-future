package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
    
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
