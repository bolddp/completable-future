package services.security;

import entity.Scope;

@FunctionalInterface
public interface ScopeListener {

    void onResult(Scope scope, Throwable e);
}
