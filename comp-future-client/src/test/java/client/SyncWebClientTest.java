package client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Customer;

public class SyncWebClientTest {

    private static Logger LOG = LoggerFactory.getLogger(SyncWebClientTest.class);
    
    private Executor executor = new ThreadPoolExecutor(1, 10, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10000));

    private SyncWebClient webClient;

    @Before
    public void setup() {
        webClient = new SyncWebClient();
    }
    
    @Test
    public void shouldGetAllCustomers() {
        SyncWebClient sut = new SyncWebClient();
        Customer[] customers = sut.getAllCustomers();
    }
    
    @Test
    public void shouldGetAsManyCustomersAsPossible() throws InterruptedException {
        int callCount = 3;
        
        CountDownLatch latch = new CountDownLatch(callCount); 

        for (int a = 0;a<10;a++) {
            final int index = a;
            executor.execute(() -> {
                LOG.info("Call #{}", index);
                webClient.getAllCustomers();
                LOG.info("Call #{} succeeded", index);
                latch.countDown();
            });
        }
        
        latch.await(callCount * 1500L, TimeUnit.MILLISECONDS);
    }
}
