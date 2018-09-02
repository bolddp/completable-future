public class SyncCustomerService {
  public Customer updateCustomerAddress(String customerId, Address address, Token accessToken) {
    UserScope scope = securityService.authenticate(accessToken);
    Customer customer = customerRepository.getById(customerId);
    if (customer == null) {
      throw new ClientRequestException(“Customer not found”);
    }
    Customer updatedCustomer = attachAddress(customer, address);
    customerRepository.insertOrUpdate(customerId, updatedCustomer);

    return updatedCustomer;
  }
}