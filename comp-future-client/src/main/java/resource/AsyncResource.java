package resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import entity.Address;
import entity.Token;
import mapper.CustomerMapper;
import services.customer.FutureCustomerService;
import services.customer.FutureFinishedCustomerService;

@Path("/async")
public class AsyncResource {

  private FutureCustomerService customerService;
  private CustomerMapper customerMapper;

  @POST
  public void postCustomerAddress(@PathParam("customerId") int customerId, Address address,
      @HeaderParam("Authorization") Token accessToken, @Suspended AsyncResponse response) {
    customerService.updateCustomerAddress(customerId, address, accessToken)
        .thenAcceptAsync(updatedCustomer -> customerMapper.toDto(updatedCustomer))
        .thenAcceptAsync(customerDto -> response.resume(customerDto)).exceptionally(e -> {
          response.resume(e);
          return null;
        });
  }
}
