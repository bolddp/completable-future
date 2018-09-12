package services.customer;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import entity.Address;
import entity.Customer;
import entity.Scope;
import entity.Token;
import repositories.customer.SyncCustomerRepository;
import services.security.SyncSecurityService;

public class SyncCustomerServiceTest {

  private SyncCustomerService sut;

  @Mock
  private SyncSecurityService securityService;
  @Mock
  private SyncCustomerRepository customerRepository;
  @Mock
  private Scope scope;
  @Mock
  private Customer updatedCustomer;

  @Captor
  private ArgumentCaptor<Customer> updatedCustomerCaptor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    sut = new SyncCustomerService(securityService, customerRepository);
  }

  @Test
  public void shouldUpdateCustomerAddress() {
    
    int CUSTOMER_ID = 1;
    Address address = new Address("name", "street", "zipcode", "city");
    Token accessToken = new Token();

    when(securityService.authenticate(eq(accessToken))).thenReturn(scope);
    when(customerRepository.getById(eq(CUSTOMER_ID))).thenReturn(updatedCustomer);
    when(customerRepository.insertOrUpdate(eq(CUSTOMER_ID), eq(updatedCustomer))).thenReturn(updatedCustomer);
    // when(securityService.authorize(eq(updatedCustomer), any(String.class), eq(scope))).thenReturn(null);
    
    when(updatedCustomer.getName()).thenReturn("name");

    sut.updateCustomerAddress(CUSTOMER_ID , address, accessToken);
   
    verify(securityService).authenticate(eq(accessToken));
    verify(customerRepository).insertOrUpdate(eq(CUSTOMER_ID), updatedCustomerCaptor.capture());
    
    Assert.assertEquals(address.getName(), updatedCustomerCaptor.getValue().getName());
  }

}
