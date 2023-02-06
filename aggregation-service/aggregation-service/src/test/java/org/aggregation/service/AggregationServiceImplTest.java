package org.aggregation.service;

import org.aggregation.aggregationservice.AggregationServiceApplication;
import org.aggregation.model.AggregationResponse;
import org.aggregation.service.AggregationServiceImpl;
import org.aggregation.dao.PricingDaoImpl;
import org.aggregation.dao.ShipmentDaoImpl;
import org.aggregation.dao.TrackingDaoImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = AggregationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AggregationServiceImplTest {

    @Mock
    private ShipmentDaoImpl mockShipmentDaoImpl;
    @Mock
    private TrackingDaoImpl mockTrackingDaoImpl;
    @Mock
    private PricingDaoImpl mockPricingTask;
    @InjectMocks
    private AggregationServiceImpl aggregationService;

    @Test
    public void test_getAggregatedProductDetail() {
        List<String> shipmentOrderNumbers = new ArrayList<>();
        shipmentOrderNumbers.add("987654321");
        List<String> trackOrderNumbers = new ArrayList<>();
        trackOrderNumbers.add("987654321");
        List<String> pricingCountryCodes = new ArrayList<>();
        pricingCountryCodes.add("NL");

        Map<String, List<String>> shipments = new HashMap<>();
        shipments.put("987654321", Arrays.asList("BOX", "BOX", "PALLET"));
        Map<String, String> track = new HashMap<>();
        track.put("123456789", "COLLECTING");
        Map<String, String> prices = new HashMap<>();
        prices.put("NL", "14.84");

        doReturn(shipments).when(mockShipmentDaoImpl).getShipments(any());
        doReturn(track).when(mockTrackingDaoImpl).getTracking(anyList());
        doReturn(prices).when(mockPricingTask).getPricing(anyList());

        AggregationResponse aggregatedProductDetail = aggregationService.getAggregatedDetails(
                shipmentOrderNumbers,
                trackOrderNumbers,
                pricingCountryCodes
        );

        assertEquals(shipments.get("987654321"), aggregatedProductDetail.getShipments().get("987654321"));
        assertEquals(track.get("987654321"), aggregatedProductDetail.getTrack().get("987654321"));
        assertEquals(prices.get("NL"), aggregatedProductDetail.getPrices().get("NL"));
    }
}
