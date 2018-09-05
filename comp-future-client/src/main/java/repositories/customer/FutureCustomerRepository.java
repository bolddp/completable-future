package repositories.customer;

import java.util.concurrent.CompletableFuture;

import entity.Customer;

public class FutureCustomerRepository {

    public CompletableFuture<Customer> getById(String customerId) {
        return CompletableFuture.completedFuture(new Customer(1, "One"));
    }

    public CompletableFuture<Customer> insertOrUpdate(String customerId, Customer updatedCustomer) {
        return CompletableFuture.completedFuture(updatedCustomer);
    }

}
