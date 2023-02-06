package org.aggregation.controller;

import org.aggregation.model.AggregationResponse;
import org.aggregation.service.AggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregationController {

    @Autowired
    private AggregationService aggregationService;

    @GetMapping("/aggregation")
    public ResponseEntity<AggregationResponse> getProductPrice(
            @RequestParam(required = false) List<String> shipmentsOrderNumbers,
            @RequestParam(required = false) List<String> trackOrderNumbers,
            @RequestParam(required = false) List<String> pricingCountryCodes
    ) {
        AggregationResponse aggregationResponse = aggregationService.getAggregatedDetails(shipmentsOrderNumbers, trackOrderNumbers, pricingCountryCodes);

        return new ResponseEntity<>(aggregationResponse, HttpStatus.OK);
    }

}
