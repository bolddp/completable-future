package repositories.customer;

import entity.Customer;
import services.customer.CustomerListener;

public interface CallbackCustomerRepository {

    void getById(String customerId, CustomerListener listener);

    void insertOrUpdate(Customer customer, CustomerListener listener);
}
