public interface CustomerListener {
  void onResult(Customer customer, Throwable e);
}

public class CallbackCustomerService {

  public void updateCustomerAddress(String customerId, Address address,
      Token accessToken, CustomerListener listener) {
    // Authenticate
    securityService.authenticate(accessToken, (scope, e) -> {
      if (e != null) {
        listener.onResult(null, e);
      } else {
        // Get customer
        customerRepository.getById(customerId, (customer, e) -> {
          if (e != null) {
            listener.onResult(null, e);
          } else {
            if (customer == null) {
              listener.onResult(null, new ClientRequestException("Customer not found"));
            } else {
              // Authorize
              authorize(customer, Action.CustomerInsert, scope, e -> {
                if (e != null) {
                  listener.onResult(null, e);
                } else {
                  Customer updatedCustomer = attachAddress(customer, address);
                  customerRepository.insertOrUpdate(customerId, updatedCustomer, e -> {
                    if (e != null) {
                      listener.onResult(null, e);
                    } else {
                      listener.onResult(updatedCustomer, null);
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