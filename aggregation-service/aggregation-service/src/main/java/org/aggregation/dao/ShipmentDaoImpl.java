package org.aggregation.dao;

import lombok.extern.slf4j.Slf4j;
import org.aggregation.webbackend.WebBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ShipmentDaoImpl implements ShipmentDao{

    @Autowired
    private WebBackend webBackend;

    public Map<String, List<String>> getShipments(List<String> orders) {
        Map<String, List<String>> shipmentResponse = new HashMap<>();
        if (orders == null || orders.isEmpty()) return shipmentResponse;

        CountDownLatch countDownLatch = new CountDownLatch(orders.size());
        ExecutorService executor = Executors.newFixedThreadPool(orders.size());
        for (String order : orders) {
            executor.execute(() -> {
                List<String> status = webBackend.getShipmentStatus(order);
                if (status != null) {
                    shipmentResponse.put(order, status);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("error found in getShipments {}", e.getCause());
        } finally {
            executor.shutdown();
        }

        return shipmentResponse;
    }
}
