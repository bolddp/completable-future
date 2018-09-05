
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

import entity.Customer;

public class ExceptionallyTest {

    private CompletableFuture<Customer> getCustomer() {
        return CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Customer not found");
            // return new Customer(1, "One");
        });
    }

    @Test
    public void shouldCatchInExceptionally() {
        getCustomer()
                .thenAcceptAsync(customer -> {
                    assertThat(customer.getId()).isEqualTo(1);
                })
                .exceptionally(e -> {
                    throw new IllegalArgumentException("Failed!", e);
                });
    }
}
