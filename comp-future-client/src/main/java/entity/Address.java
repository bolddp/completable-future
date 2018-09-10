package entity;

public class Address {

    private String name;
    private String street;
    private String zipcode;
    private String city;

    public Address() {
      // TODO Auto-generated constructor stub
    }
    
    public Address(String name, String street, String zipcode, String city) {
      this.name = name;
      this.street = street;
      this.zipcode = zipcode;
      this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

}
