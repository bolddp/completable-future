package services.customer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import entity.Address;
import entity.Customer;
import entity.Scope;
import entity.Token;
import repositories.customer.FutureCustomerRepository;
import services.security.FutureSecurityService;

public class FutureFinishedCustomerServiceTest {

  private FutureFinishedCustomerService sut;

  @Mock
  private FutureSecurityService securityService;
  @Mock
  private FutureCustomerRepository customerRepository;
  @Mock
  private Scope scope;

  @Captor
  private ArgumentCaptor<Customer> updatedCustomer;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    sut = new FutureFinishedCustomerService(securityService, customerRepository);
  }

  @Test
  public void shouldUpdateCustomerAddress() throws InterruptedException, ExecutionException {
    
    int CUSTOMER_ID = 1;
    Address address = new Address("name", "street", "zipcode", "city");
    Token accessToken = new Token();

    when(securityService.authenticate(eq(accessToken))).thenReturn(CompletableFuture.completedFuture(scope));

    sut.updateCustomerAddress(CUSTOMER_ID , address, accessToken).get();
   
    verify(securityService).authenticate(eq(accessToken));
    verify(customerRepository.insertOrUpdate(eq(CUSTOMER_ID), updatedCustomer.capture()));
    
    Assert.assertEquals(address, updatedCustomer.getValue().getAddress());
  }
}
