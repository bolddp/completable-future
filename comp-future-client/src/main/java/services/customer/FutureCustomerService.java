package services.customer;

import java.util.concurrent.CompletableFuture;

import entity.Address;
import entity.Customer;
import entity.Scope;
import entity.Token;
import repositories.customer.FutureCustomerRepository;
import repositories.customer.SyncCustomerRepository;
import services.security.FutureSecurityService;
import services.security.SyncSecurityService;

public class FutureCustomerService {

  private FutureSecurityService securityService;
  private FutureCustomerRepository customerRepository;

  public FutureCustomerService(FutureSecurityService securityService, FutureCustomerRepository customerRepository) {
    this.securityService = securityService;
    this.customerRepository = customerRepository;
  }

  private Customer attachAddress(Customer customer, Address address) {
    return customer;
  }

  public CompletableFuture<Customer> updateCustomerAddress(int customerId, Address address, Token accessToken) {
    return securityService.authenticate(accessToken)
        .thenComposeAsync(scope -> {
          return customerRepository.getById(customerId)
              .thenComposeAsync(customer -> {
                if (customer == null) {
                  throw new RuntimeException("Customer not found");
                }
                return securityService.authorize(customer, "Customer.Insert", scope)
                    .thenComposeAsync(rsp -> {
                      Customer updatedCustomer = attachAddress(customer, address);
                      return customerRepository.insertOrUpdate(customerId, updatedCustomer);
                    });
              });
        });
  }
}
