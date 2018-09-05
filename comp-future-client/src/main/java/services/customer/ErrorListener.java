package services.customer;

@FunctionalInterface
public interface ErrorListener {

    void onResult(Throwable e);
}
