package repositories.customer;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import client.AsyncWebClient;
import entity.Customer;

public class FutureCustomerRepository {

  private AsyncWebClient client;
  
  public CompletableFuture<Customer> getById(int customerId) {
    return client.getAllCustomers()
        .thenApplyAsync(customers -> Arrays.stream(customers).filter(x -> x.getId() == customerId).findFirst().orElse(null));
  }

  public CompletableFuture<Customer> insertOrUpdate(int customerId, Customer updatedCustomer) {
    return CompletableFuture.completedFuture(updatedCustomer);
  }

}
