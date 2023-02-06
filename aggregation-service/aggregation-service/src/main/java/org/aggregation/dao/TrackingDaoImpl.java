package org.aggregation.dao;

import lombok.extern.slf4j.Slf4j;
import org.aggregation.webbackend.WebBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class TrackingDaoImpl implements TrackingDao{
    @Autowired
    private WebBackend webBackend;

    public @NonNull
    Map<String, String> getTracking(final List<String> orders) {
        Map<String, String> response = new HashMap<>();
        if (orders == null || orders.isEmpty()) return response;

        CountDownLatch countDownLatch = new CountDownLatch(orders.size());
        ExecutorService executor = Executors.newFixedThreadPool(orders.size());
        for (String order : orders) {
            executor.execute(() -> {
                String status = webBackend.getTrackStatus(order);
                if (status != null) {
                    response.put(order, status);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("error found in getTracking {}", e.getCause());
        } finally {
            executor.shutdown();
        }
        return response;
    }
}
