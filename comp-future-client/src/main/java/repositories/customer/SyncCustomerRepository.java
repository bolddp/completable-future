package repositories.customer;

import java.util.Arrays;

import client.SyncWebClient;
import entity.Customer;

public class SyncCustomerRepository {

  private SyncWebClient webClient;
  
  public Customer getById(int customerId) {
    return Arrays.stream(webClient.getAllCustomers())
        .filter(x -> x.getId() == customerId).findFirst().orElse(null);
  }

  public Customer insertOrUpdate(int customerId, Customer updatedCustomer) {
    return updatedCustomer;
  }

}
