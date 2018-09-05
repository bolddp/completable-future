package services.customer;

import entity.Address;
import entity.Customer;
import entity.Scope;
import entity.Token;
import repositories.customer.CallbackCustomerRepository;
import services.security.CallbackSecurityService;

public class CallbackCustomerService {

  private CallbackCustomerRepository customerRepository;
  private CallbackSecurityService securityService;

  public Customer attachAddress(Customer customer, Address address) {
    customer.setAddress(address);
    return customer;
  }

  public void updateCustomerAddress(final String customerId, final Address address, final Token accessToken,
      final CustomerListener listener) {
    // Authenticate
    securityService.authenticate(accessToken, (scope, e) -> {
      if (e != null) {
        listener.onResult(null, e);
      } else {
        // Get customer
        customerRepository.getById(customerId, (customer, e2) -> {
          if (e2 != null) {
            listener.onResult(null, e2);
          } else {
            if (customer == null) {
              listener.onResult(null, new RuntimeException("Customer not found"));
            } else {
              // Authorize
              securityService.authorize(customer, "Customer.Insert", scope, (e3) -> {
                if (e3 != null) {
                  listener.onResult(null, e3);
                } else {
                  Customer updatedCustomer = attachAddress(customer, address);
                  customerRepository.insertOrUpdate(updatedCustomer, (updatedCustomer2, e4) -> {
                    if (e4 != null) {
                      listener.onResult(null, e4);
                    } else {
                      listener.onResult(updatedCustomer2, null);
                    }
                  });
                }
              });
            }
          }
        });
      }
    });
  }

}
