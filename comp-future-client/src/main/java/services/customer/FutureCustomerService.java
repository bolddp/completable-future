package services.customer;

import java.util.concurrent.CompletableFuture;

import entity.Address;
import entity.Customer;
import entity.Token;
import repositories.customer.FutureCustomerRepository;
import services.security.FutureSecurityService;

public class FutureCustomerService {

  private FutureSecurityService securityService;
  private FutureCustomerRepository customerRepository;

  public CompletableFuture<Customer> updateCustomerAddress(String customerId, Address address, Token accessToken) {
    return securityService.authenticate(accessToken).thenComposeAsync(scope -> {
      return customerRepository.getById(customerId).thenComposeAsync(customer -> {
        if (customer == null) {
          throw new RuntimeException("Customer not found");
        }
        return securityService.authorize(customer, "Customer.Insert", scope)
            .thenComposeAsync(rsp -> {
              final Customer updatedCustomer = attachAddress(customer, address);
              return customerRepository.insertOrUpdate(customerId, updatedCustomer);
            });
      });
    });
  }

  private Customer attachAddress(Customer customer, Address address) {
    customer.setAddress(address);
    return customer;
  }
}
