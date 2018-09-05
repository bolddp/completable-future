package repositories.customer;

import entity.Customer;

public class SyncCustomerRepository {

    public Customer getById(String customerId) {
        return new Customer(1, "One");
    }

    public void insertOrUpdate(String customerId, Customer updatedCustomer) {
        // TBD
    }

}
