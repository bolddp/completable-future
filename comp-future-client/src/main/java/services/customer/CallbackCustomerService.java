package services.customer;

import javax.xml.transform.ErrorListener;

import entity.Address;
import entity.Customer;
import entity.Scope;
import entity.Token;
import repositories.customer.CallbackCustomerRepository;
import services.security.CallbackSecurityService;

public class CallbackCustomerService {

    private CallbackCustomerRepository customerRepository;
    private CallbackSecurityService securityService;

    public void updateCustomerAddress(final String customerId, final Address address,
            final Token accessToken, final CustomerListener listener) {
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
                            authorize(customer, "Customer.Insert", scope, e3 -> {
                                if (e3 != null) {
                                    listener.onResult(null, e3);
                                } else {
                                    Customer updatedCustomer = attachAddress(customer, address);
                                    customerRepository.insertOrUpdate(customerId, (updatedCustomer, e4) -> {
                                        if (e4 != null) {
                                            listener.onResult(null, e4);
                                        } else {
                                            listener.onResult(updatedCustomer, null);
                                        }
                                    });
                                }
                            };
                        }
                    }
                });
            }
        });
    }

    public void authorize(final Customer customer, final String action, final Scope scope,
            final ErrorListener callback) {
        // TBD
    }

}
