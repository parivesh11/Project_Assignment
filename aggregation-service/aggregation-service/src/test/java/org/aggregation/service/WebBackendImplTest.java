package org.aggregation.service;

import org.aggregation.webbackend.WebBackend;
import org.aggregation.webbackend.WebBackendImpl;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class WebBackendImplTest {
    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private WebBackend webBackendImpl =
            new WebBackendImpl(
                    restTemplate,
                    "url"
            );
    @Test
    public void test_getShipmentStatus() {
        List<String> shipment = new ArrayList<>();
        shipment.add("BOX");
        shipment.add("ENVELOPE");
        ResponseEntity<List> response = new ResponseEntity<>(shipment, HttpStatus.OK);
        doReturn(response).when(restTemplate).getForEntity(anyString(), any(), anyString());
        List<String> shipmentStatus = webBackendImpl.getShipmentStatus("987654321");
        assertTrue(shipmentStatus.contains(shipment.get(0)));
        assertTrue(shipmentStatus.contains(shipment.get(1)));
    }

    @Test
    public void test_getTrackStatus() {
        String status = '"'+"delivered"+'"';
        ResponseEntity<String> response = new ResponseEntity<>(status, HttpStatus.OK);
        doReturn(response).when(restTemplate).getForEntity(anyString(), any(), anyString());
        String statusFromBackend = webBackendImpl.getTrackStatus("987654321");
        assertEquals("delivered", statusFromBackend);
    }

    @Test
    public void test_getPricing() throws InterruptedException {
        String price = "12.44";
        ResponseEntity<String> response = new ResponseEntity<>(price, HttpStatus.OK);
        doReturn(response).when(restTemplate).getForEntity(anyString(), any(), anyString());
        String pricing = webBackendImpl.getPricing("NL");
        assertEquals(price, pricing);
    }

    @Test
    public void test_getShipmentStatus_timeout() {
        when(restTemplate.getForEntity(anyString(), any(), anyString())).thenThrow(AsyncRequestTimeoutException.class);
        List<String> response = webBackendImpl.getShipmentStatus("987654321");
        assertNull(response);
    }

    @Test
    public void test_getTrackStatus_timeout() {
        when(restTemplate.getForEntity(anyString(), any(), anyString())).thenThrow(AsyncRequestTimeoutException.class);
        String response = webBackendImpl.getTrackStatus("987654321");
        assertNull(response);
    }

    @Test
    public void test_getPricing_timeout() {
        when(restTemplate.getForEntity(anyString(), any(), anyString())).thenThrow(AsyncRequestTimeoutException.class);
        String response = webBackendImpl.getPricing("NL");
        assertNull(response);
    }
}
