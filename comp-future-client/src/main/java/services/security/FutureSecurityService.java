package services.security;

import java.util.concurrent.CompletableFuture;

import entity.Customer;
import entity.Scope;
import entity.Token;

public class FutureSecurityService {

    public CompletableFuture<Scope> authenticate(Token accessToken) {
        // TODO Auto-generated method stub
        return CompletableFuture.completedFuture(new Scope());
    }

    public CompletableFuture<Void> authorize(Customer customer, String action, Scope scope) {
      return CompletableFuture.completedFuture(null);
    }

}
