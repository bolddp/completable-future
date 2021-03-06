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
    
    private Executor executor = new ThreadPoolExecutor(5, 5, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10000));

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
        int callCount = 100;
        long startTick = System.currentTimeMillis();
        
        CountDownLatch latch = new CountDownLatch(callCount); 

        for (int a = 0;a<callCount;a++) {
            final int index = a;
            executor.execute(() -> {
                LOG.info("Call #{} at {} ms", index, System.currentTimeMillis() - startTick);
                final Customer[] customers = webClient.getAllCustomers();
                LOG.info("Call #{} succeeded at {} ms... Customer #{}", index, System.currentTimeMillis() - startTick, customers[0].getId());
                latch.countDown();
            });
        }
        
        latch.await(callCount * 1500L, TimeUnit.MILLISECONDS);
    }
}
