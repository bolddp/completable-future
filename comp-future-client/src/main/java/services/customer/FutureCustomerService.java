package services.customer;
public class FutureCustomerService {
  public CompletableFuture<Customer> updateCustomerAddress(String customerId, Address address, Token accessToken) {
    return securityService.authenticate(accessToken)
      .thenComposeAsync(token -> {
        return customerRepository.getById(customerId)
          .thenComposeAsync(customer -> {
            if (customer == null) {
              throw new ClientRequestException("Customer not found");
            }
            Customer updatedCustomer = attachAddress(customer, address);
            return customerRepository.insertOrUpdate(customerId, updatedCustomer)
              .thenAcceptAsync(rsp -> {
                return updatedCustomer;
              });
          });
      });
    UserScope scope = securityService.authenticate(accessToken);
    Customer customer = customerRepository.getById(customerId);
    if (customer == null) {
      throw new ClientRequestException("Customer not found");
    }
    Customer updatedCustomer = attachAddress(customer, address);
    customerRepository.insertOrUpdate(customerId, updatedCustomer);

    return updatedCustomer;
  }
}