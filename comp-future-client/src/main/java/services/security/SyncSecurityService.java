package services.security;

import entity.Customer;
import entity.Scope;
import entity.Token;

public class SyncSecurityService {

    public Scope authenticate(Token accessToken) {
        return new Scope();
    }

    public void authorize(Customer customer, String string, Scope scope) {
        // TODO Auto-generated method stub
    }

}
