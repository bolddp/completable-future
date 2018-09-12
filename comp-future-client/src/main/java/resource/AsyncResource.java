package resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import entity.Address;
import entity.Customer;
import entity.Token;
import mapper.CustomerDto;
import mapper.CustomerMapper;
import services.customer.FutureCustomerService;

@Path("/async")
public class AsyncResource {

    private FutureCustomerService customerService;
    private CustomerMapper customerMapper;

    @POST
    public void postCustomerAddress(@PathParam("customerId") final int customerId, final Address address,
            @HeaderParam("Authorization") final Token accessToken, @Suspended final AsyncResponse response) {
        customerService.updateCustomerAddress(customerId, address, accessToken)
                .thenApplyAsync(updatedCustomer -> customerMapper.toDto(updatedCustomer))
                .thenAcceptAsync(customerDto -> response.resume(customerDto))
                .exceptionally(e -> {
                    response.resume(e);
                    return null;
                });
    }

    @POST
    public CustomerDto postCustomerAddressSync(@PathParam("customerId") final int customerId, final Address address,
            @HeaderParam("Authorization") final Token accessToken) {
        try {
            final Customer customer = customerService.updateCustomerAddress(customerId, address, accessToken).get();
            return customerMapper.toDto(customer);
        } catch (final Exception e) {
            throw new WebApplicationException(e);
        }
    }
}
