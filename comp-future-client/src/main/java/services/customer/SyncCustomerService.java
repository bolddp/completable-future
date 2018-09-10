package services.customer;

import entity.Address;
import entity.Customer;
import entity.Scope;
import entity.Token;
import repositories.customer.SyncCustomerRepository;
import services.security.SyncSecurityService;

public class SyncCustomerService {

  private SyncSecurityService securityService;
  private SyncCustomerRepository customerRepository;

  public SyncCustomerService(SyncSecurityService securityService, SyncCustomerRepository customerRepository) {
    this.securityService = securityService;
    this.customerRepository = customerRepository;
  }

  private Customer attachAddress(Customer customer, Address address) {
    return customer;
  }

  public Customer updateCustomerAddress(int customerId, Address address, Token accessToken) {
    Scope scope = securityService.authenticate(accessToken);
    Customer customer = customerRepository.getById(customerId);
    if (customer == null) {
      throw new RuntimeException("Customer not found");
    }
    securityService.authorize(customer, "Customer.Insert", scope);
    Customer updatedCustomer = attachAddress(customer, address);
    customerRepository.insertOrUpdate(customerId, updatedCustomer);

    return updatedCustomer;
  }
}
