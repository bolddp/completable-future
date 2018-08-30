package client;

import static org.asynchttpclient.Dsl.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import javax.ws.rs.WebApplicationException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;

import entity.Customer;
import utils.Json;

public class AsyncCallbackWebClient {

    private static final Object BASE_URL = "http://localhost:8080";
    private static final String USER_AGENT = "DemoClient/1.0";

    private static AsyncHttpClient asyncHttpClient;
    
    private String buildFullUrl(String path) {
        return String.format("%s%s", BASE_URL, path);
    }
    
    static {
        asyncHttpClient = asyncHttpClient();
    }
    
    private <T> T map(String json,  Class<T> valueType)  {
        try {
            return Json.mapper().readValue(json, valueType);
        } catch (IOException e) {
            throw new WebApplicationException(e, HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    private void get(String url, final Consumer<String> callback) {
        final ListenableFuture<Response> requestFuture = asyncHttpClient.prepareGet(url).execute();
        requestFuture.addListener(() -> {
            try {
                Response response = requestFuture.get();
                String responseJson = response.getResponseBody();
                // Return response in callback
                callback.accept(responseJson);
            } catch (InterruptedException | ExecutionException e){
                throw new WebApplicationException(e, HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        }, null); 
    }
    
    public void getAllCustomers(Consumer<Customer[]> callback) {
        get(buildFullUrl("/customers"), strJson -> {
            Customer[] customers = map(strJson, Customer[].class);
            callback.accept(customers);
        });
    }
}
