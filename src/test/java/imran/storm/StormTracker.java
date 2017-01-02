package imran.storm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StormTracker {

    private static final StormTracker instance = new StormTracker();
    private Map<String, CountDownLatch> latches = new HashMap<>();
    private AtomicInteger messageCount = new AtomicInteger();

    public static StormTracker getInstance(){
        return instance;
    }

    public void startTracking(String id){
        if (latches.get(id) == null) {
            latches.put(id,  new CountDownLatch(1));
            return;
        }

        throw new IllegalStateException("Latch has not been reset: " + id);
    }

    public void resetTracking(){
        latches.clear();;
        messageCount = new AtomicInteger();
    }

    public boolean waitForSignal(String id, Long timeout, TimeUnit unit) throws InterruptedException {
        CountDownLatch latch = latches.get(id);
        boolean result = latch.await(timeout, unit);
        if(!result){
            latch.countDown();
        }
        return result;
    }

    public void sendProcessedSignal(String id) {
        CountDownLatch latch = latches.get(id);
        if(latch == null){
            startTracking(id);
            latch = latches.get(id);
        }
        latch.countDown();
        messageCount.incrementAndGet();
    }

    public boolean waitUntilNMessagesProcessed(Integer numberOfAcks, Integer maxTimeoutMilliseconds) {
        int retries = 0;

        while(true){
            try {
                Thread.sleep(50);
                retries++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(messageCount.get() < numberOfAcks || retries >= maxTimeoutMilliseconds/50){
                break;
            }
        }

        return messageCount.get() > numberOfAcks;
    }


}
