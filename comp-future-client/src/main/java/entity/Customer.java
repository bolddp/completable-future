package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Address address;

    public Customer() {
        // Deserialization
    }

    public Customer(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
