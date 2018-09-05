package services.customer;

import entity.Customer;

@FunctionalInterface
public interface CustomerListener {

    void onResult(Customer customer, Throwable e);
}
