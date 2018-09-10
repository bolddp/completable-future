package services.customer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import entity.Address;
import entity.Customer;
import entity.Token;
import repositories.customer.FutureCustomerRepository;
import services.security.FutureSecurityService;

public class FutureFinishedCustomerService {

  private FutureSecurityService securityService;
  private FutureCustomerRepository customerRepository;

  public FutureFinishedCustomerService(FutureSecurityService securityService, FutureCustomerRepository customerRepository) {
    this.securityService = securityService;
    this.customerRepository = customerRepository;
  }

  public CompletableFuture<Customer> updateCustomerAddress(int customerId, Address address, Token accessToken) {
    return securityService.authenticate(accessToken).thenComposeAsync(scope -> {
      return customerRepository.getById(customerId).thenComposeAsync(customer -> {
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
