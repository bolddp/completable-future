package client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

import entity.Customer;

public class ExceptionallyTest {

    @Test
    public void shouldCatchInExceptionally() {
        getCustomer()
                .thenAcceptAsync(customer -> {
                    assertThat(customer.getId()).isEqualTo(1);
                })
                .exceptionally(e -> {
                    throw new IllegalArgumentException("Failed");
                });
    }

    private CompletableFuture<Customer> getCustomer() {
        return CompletableFuture.completedFuture(new Customer(1, "One"));
    }
}
