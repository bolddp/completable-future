package client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncCallbackWebClientTest {

    private static Logger LOG = LoggerFactory.getLogger(AsyncCallbackWebClientTest.class);

    private final Executor executor = new ThreadPoolExecutor(5, 5, 10L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10000));

    private AsyncCallbackWebClient webClient;

    @Before
    public void setup() {
        webClient = new AsyncCallbackWebClient();
    }

    @Test
    public void shouldGetAsManyCustomersAsPossible() throws InterruptedException {
        final int callCount = 100;
        final long startTick = System.currentTimeMillis();

        final CountDownLatch latch = new CountDownLatch(callCount);

        for (int a = 0; a < callCount; a++) {
            final int index = a;
            executor.execute(() -> {
                LOG.info("Call #{} at {} ms", index, System.currentTimeMillis() - startTick);
                webClient.getAllCustomers(customers -> {
                    LOG.info("Call #{} succeeded at {} ms... Customer #{}", index, System.currentTimeMillis() - startTick,
                            customers[0].getId());
                    latch.countDown();
                });
            });
        }

        latch.await(callCount * 1500L, TimeUnit.MILLISECONDS);
    }
}
