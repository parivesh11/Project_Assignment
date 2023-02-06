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
public class PricingDaoImpl implements PricingDao{

    @Autowired
    private WebBackend webBackend;

    public Map<String, String> getPricing(List<String> countryCodes) {
        Map<String, String> pricingResponse = new HashMap<>();
        if (countryCodes == null || countryCodes.isEmpty()) return pricingResponse;

        CountDownLatch countDownLatch = new CountDownLatch(countryCodes.size());
        ExecutorService executor = Executors.newFixedThreadPool(countryCodes.size());
        for (String code : countryCodes) {
            executor.execute(() -> {
                String price = webBackend.getPricing(code);
                if (price != null) {
                    pricingResponse.put(code, price);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("error found in getPricing {}", e.getCause());
        } finally {
            executor.shutdown();
        }

        return pricingResponse;
    }

}
