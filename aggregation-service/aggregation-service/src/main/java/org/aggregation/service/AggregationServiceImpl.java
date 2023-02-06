package org.aggregation.service;

import org.aggregation.model.AggregationResponse;
import org.aggregation.dao.PricingDaoImpl;
import org.aggregation.dao.ShipmentDaoImpl;
import org.aggregation.dao.TrackingDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AggregationServiceImpl implements AggregationService{

    @Autowired
    private ShipmentDaoImpl shipmentDaoImpl;

    @Autowired
    private TrackingDaoImpl trackingDaoImpl;

    @Autowired
    private PricingDaoImpl pricingDaoImpl;

    @Override
    public AggregationResponse getAggregatedDetails(List<String> shipmentOrderNumbers, List<String> trackOrderNumbers, List<String> pricingCountryCodes) {

        AggregationResponse aggregationResponse = new AggregationResponse();

        //Call all three backend service, combine response and send back to controller

        CompletableFuture<Map<String, List<String>>> shipmentResponse = CompletableFuture.supplyAsync(() ->
                shipmentDaoImpl.getShipments(shipmentOrderNumbers)
        );

        CompletableFuture<Map<String, String>> trackingResponse = CompletableFuture.supplyAsync(() ->
                trackingDaoImpl.getTracking(trackOrderNumbers)
        );

        CompletableFuture<Map<String, String>> pricingResponse = CompletableFuture.supplyAsync(() ->
                pricingDaoImpl.getPricing(pricingCountryCodes)
        );

        return  CompletableFuture.allOf(shipmentResponse, trackingResponse, pricingResponse)
                        .thenApply(v -> {
                            aggregationResponse.setShipments(shipmentResponse.join());
                            aggregationResponse.setTrack(trackingResponse.join());
                            aggregationResponse.setPrices(pricingResponse.join());
                            return aggregationResponse;
                        }).join();
    }
}
