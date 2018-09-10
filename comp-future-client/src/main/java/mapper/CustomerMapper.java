package mapper;

import entity.Customer;

public class CustomerMapper {

  public CustomerDto toDto(Customer customer) {
    return new CustomerDto(customer.getId(), customer.getName(), customer.getAddress());
  }

}
