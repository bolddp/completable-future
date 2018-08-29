package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    private static ObjectMapper mapperInstance;

    public static ObjectMapper mapper() {
        return mapperInstance;
    }
    
    static {
        mapperInstance = new ObjectMapper();
    }
}
