package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.WebApplicationException;

import entity.Customer;
import utils.Json;

public class SyncWebClient {

    private static final Object BASE_URL = "http://localhost:8080";
    private static final String USER_AGENT = "DemoClient/1.0";

    private String buildFullUrl(String path) {
        return String.format("%s%s", BASE_URL, path);
    }
    
    private String get(String url) {
        try {
            URL obj = new URL(buildFullUrl(url));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return response.toString();
        } catch (IOException e) {
            throw new WebApplicationException(e, HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    private <T> T map(String json, Class<T> valueType) {
        try {
            return Json.mapper().readValue(json, valueType);
        } catch (IOException e) {
            throw new WebApplicationException(e, HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }
    
    public Customer[] getAllCustomers() {
        return map(get("/customers"), Customer[].class);
    }
}
