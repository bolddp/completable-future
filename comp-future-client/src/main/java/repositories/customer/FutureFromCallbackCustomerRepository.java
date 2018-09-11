package repositories.customer;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import client.AsyncCallbackWebClient;
import entity.Customer;

public class FutureFromCallbackCustomerRepository {

    private AsyncCallbackWebClient callbackWebClient;

    public CompletableFuture<Customer> getById(final int customerId) {
        final CompletableFuture<Customer> cf = new CompletableFuture<>();
        callbackWebClient.getAllCustomers(customers -> {
            final Customer customer = Arrays.stream(customers).filter(x -> x.getId() == customerId).findFirst().orElse(null);
            cf.complete(customer);
        }, error -> {
            cf.completeExceptionally(error);
        });
        return cf;
    }
}
