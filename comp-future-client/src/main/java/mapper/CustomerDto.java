package mapper;

import entity.Address;

public class CustomerDto {
  private int id;
  private String name;
  private Address address;

  public CustomerDto(int id, String name, Address address) {
    this.id = id;
    this.name = name;
    this.address = address;
  }
}
